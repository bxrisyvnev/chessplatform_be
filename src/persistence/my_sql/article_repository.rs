use dotenvy::dotenv;
use mysql::prelude::*;
use mysql::{params, params::Params, Opts, Pool, PooledConn, Result, Value};
use std::collections::HashMap;
use std::env;

use crate::domain::article::Article;
use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use crate::persistence::traits::ArticleRepository;

pub struct MySqlArticleRepository {
    pool: Pool,
}

impl MySqlArticleRepository {
    pub fn new() -> Self {
        dotenv().ok();

        let database_url = env::var("DATABASE_URL").expect("DATABASE_URL must be set");
        let opts = Opts::from_url(&database_url).unwrap();
        let pool = Pool::new(opts).unwrap();

        Self { pool }
    }

    fn get_conn(&self) -> Result<PooledConn> {
        self.pool.get_conn()
    }
}

impl ArticleRepository for MySqlArticleRepository {
    fn insert_article(&self, article: Article) -> Result<u64> {
        let mut conn = self.get_conn()?;

        conn.exec_drop(
            r#"
            INSERT INTO articles (
                article_title,
                author_name,
                content_text,
                image_url,
                author_id
            ) VALUES (
                :article_title,
                :author_name,
                :content_text,
                :image_url,
                :author_id
            )
            "#,
            params! {
                "article_title" => article.article_title.clone(),
                "author_name" => article.author_name.clone(),
                "content_text" => article.content_text.clone(),
                "image_url" => article.image_url.clone(),
                "author_id" => article.author_id,
            },
        )?;

        Ok(conn.last_insert_id())
    }

    fn delete_article(&self, article: Article) -> Result<()> {
        let mut conn = self.get_conn()?;
        let article_id = article.id as u64;

        conn.exec_drop(
            "DELETE FROM articles WHERE id = :id",
            params! {
                "id" => article_id,
            },
        )?;

        Ok(())
    }

    fn get_article_page(&self, pageable: &Pageable) -> Result<Option<Page<Article>>> {
        let mut conn = self.get_conn()?;
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return Ok(None);
        }

        let offset = (page - 1) * size;

        let article_rows: Vec<(u32, String, String, String, String, u32)> = conn.exec(
            r#"
            SELECT
                id,
                COALESCE(article_title, ''),
                COALESCE(author_name, ''),
                COALESCE(content_text, ''),
                COALESCE(image_url, ''),
                author_id
            FROM articles
            ORDER BY id
            LIMIT ? OFFSET ?
            "#,
            (size, offset),
        )?;

        if article_rows.is_empty() {
            return Ok(None);
        }

        let article_ids: Vec<u32> = article_rows.iter().map(|row| row.0).collect();

        let placeholders = std::iter::repeat_n("?", article_ids.len())
            .collect::<Vec<_>>()
            .join(", ");

        let comment_sql = format!(
            "SELECT id, article_id FROM comments WHERE article_id IN ({}) ORDER BY id",
            placeholders
        );

        let comment_params =
            Params::Positional(article_ids.iter().map(|id| Value::from(*id)).collect());

        let comment_rows: Vec<(u32, u32)> = conn.exec(comment_sql, comment_params)?;

        let mut comment_map: HashMap<u32, Vec<u32>> = HashMap::new();
        for (comment_id, article_id) in comment_rows {
            comment_map.entry(article_id).or_default().push(comment_id);
        }

        let articles: Vec<Article> = article_rows
            .into_iter()
            .map(
                |(id, article_title, author_name, content_text, image_url, author_id)| Article {
                    id,
                    article_title,
                    author_name,
                    content_text,
                    image_url,
                    author_id,
                    comments_ids: comment_map.remove(&id).unwrap_or_default(),
                },
            )
            .collect();

        let count = articles.len() as u32;
        Ok(Some(Page::new(articles, count, pageable)))
    }

    fn find_article_by_id(&self, article_id: u32) -> Result<Option<Article>> {
        let mut conn = self.get_conn()?;

        let article_row: Option<(u32, String, String, String, String, u32)> = conn.exec_first(
            r#"
            SELECT
                id,
                COALESCE(article_title, ''),
                COALESCE(author_name, ''),
                COALESCE(content_text, ''),
                COALESCE(image_url, ''),
                author_id
            FROM articles
            WHERE id = :id
            "#,
            params! {
                "id" => article_id,
            },
        )?;

        let Some((id, article_title, author_name, content_text, image_url, author_id)) = article_row else {
            return Ok(None);
        };

        let comments_ids: Vec<u32> = conn.exec_map(
            "SELECT id FROM comments WHERE article_id = :id",
            params! {
                "id" => article_id,
            },
            |comment_id| comment_id,
        )?;

        Ok(Some(Article {
            id,
            article_title,
            author_name,
            content_text,
            image_url,
            author_id,
            comments_ids,
        }))
    }

    fn get_by_title(&self, title: &str) -> Result<Option<Article>> {
        let mut conn = self.get_conn()?;

        let row: Option<(u32, String, String, String, String, u32)> = conn.exec_first(
            r#"
            SELECT
                id,
                COALESCE(article_title, ''),
                COALESCE(author_name, ''),
                COALESCE(content_text, ''),
                COALESCE(image_url, ''),
                author_id
            FROM articles
            WHERE article_title = :title
            LIMIT 1
            "#,
            params! {
                "title" => title,
            },
        )?;

        let Some((id, article_title, author_name, content_text, image_url, author_id)) = row else {
            return Ok(None);
        };

        let comments_ids: Vec<u32> = conn.exec_map(
            r#"
            SELECT id
            FROM comments
            WHERE article_id = :article_id
            ORDER BY id
            "#,
            params! {
                "article_id" => id,
            },
            |comment_id| comment_id,
        )?;

        Ok(Some(Article {
            id,
            article_title,
            author_name,
            content_text,
            image_url,
            author_id,
            comments_ids,
        }))
    }

    fn update_article(&self, article: &Article, update_id: u32) -> Result<bool> {
        let mut conn = self.get_conn()?;

        conn.exec_drop(
            r#"
            UPDATE articles
            SET
                article_title = :article_title,
                author_name = :author_name,
                content_text = :content_text,
                image_url = :image_url,
                author_id = :author_id
            WHERE id = :id
            "#,
            params! {
                "id" => update_id,
                "article_title" => &article.article_title,
                "author_name" => &article.author_name,
                "content_text" => &article.content_text,
                "image_url" => &article.image_url,
                "author_id" => article.author_id,
            },
        )?;

        Ok(conn.affected_rows() > 0)
    }

    fn get_articles_by_title_page(
        &self,
        title_query: &str,
        pageable: &Pageable,
    ) -> Result<Option<Page<Article>>> {
        let mut conn = self.get_conn()?;
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return Ok(None);
        }

        let offset = (page - 1) * size;
        let like_pattern = format!("%{}%", title_query);

        let article_rows: Vec<(u32, String, String, String, String, u32)> = conn.exec(
            r#"
            SELECT
                id,
                COALESCE(article_title, ''),
                COALESCE(author_name, ''),
                COALESCE(content_text, ''),
                COALESCE(image_url, ''),
                author_id
            FROM articles
            WHERE COALESCE(article_title, '') LIKE :title
            ORDER BY id
            LIMIT :limit OFFSET :offset
            "#,
            params! {
                "title" => &like_pattern,
                "limit" => size,
                "offset" => offset,
            },
        )?;

        if article_rows.is_empty() {
            return Ok(None);
        }

        let article_ids: Vec<u32> = article_rows.iter().map(|row| row.0).collect();

        let placeholders = std::iter::repeat_n("?", article_ids.len())
            .collect::<Vec<_>>()
            .join(", ");

        let comment_sql = format!(
            "SELECT id, article_id FROM comments WHERE article_id IN ({}) ORDER BY id",
            placeholders
        );

        let comment_params =
            Params::Positional(article_ids.iter().map(|id| Value::from(*id)).collect());

        let comment_rows: Vec<(u32, u32)> = conn.exec(comment_sql, comment_params)?;

        let mut comment_map: HashMap<u32, Vec<u32>> = HashMap::new();
        for (comment_id, article_id) in comment_rows {
            comment_map.entry(article_id).or_default().push(comment_id);
        }

        let articles: Vec<Article> = article_rows
            .into_iter()
            .map(
                |(id, article_title, author_name, content_text, image_url, author_id)| Article {
                    id,
                    article_title,
                    author_name,
                    content_text,
                    image_url,
                    author_id,
                    comments_ids: comment_map.remove(&id).unwrap_or_default(),
                },
            )
            .collect();

        let count = articles.len() as u32;
        Ok(Some(Page::new(articles, count, pageable)))
    }
}
