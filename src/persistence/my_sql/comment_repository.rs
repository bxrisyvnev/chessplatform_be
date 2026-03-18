use dotenvy::dotenv;
use mysql::prelude::*;
use mysql::{params, Opts, Pool, PooledConn, Result};
use std::env;

use crate::domain::comment::Comment;
use crate::persistence::traits::CommentRepository;

pub struct MySqlCommentRepository {
    pool: Pool,
}

impl MySqlCommentRepository {
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

impl CommentRepository for MySqlCommentRepository {
    fn insert_comment(&self, comment: Comment) -> Result<u64> {
        let mut conn = self.get_conn()?;

        conn.exec_drop(
            r#"
                INSERT INTO comments (
                    text,
                    article_id,
                    user_id
                ) VALUES (
                    :text,
                    :article_id,
                    :user_id
                )
            "#,
            params! {
                 "text" => comment.text.clone(),
                 "article_id" => comment.article_id.clone(),
                 "user_id" => comment.user_id,
            },
        )?;

        Ok(conn.last_insert_id())
    }

    fn delete_comment(&self, comment: Comment) -> Result<()> {
        let mut conn = self.get_conn()?;
        let comment_id = comment.id as u64;

        conn.exec_drop(
            "DELETE FROM comments WHERE id = :id",
            params! {
                "id" => comment_id,
            },
        )?;

        Ok(())
    }

    fn find_comment_by_id(&self, comment_id: u32) -> Result<Option<Comment>> {
        let mut conn = self.get_conn()?;

        let comment_row: Option<(u32, String, u32, u32)> = conn.exec_first(
            r#"
                SELECT
                    id,
                    text,
                    user_id,
                    article_id
                FROM comments
                WHERE id = :id
            "#,
            params! {
                "id" => comment_id,
            },
        )?;

        let Some((id, text, user_id, article_id)) = comment_row else {
            return Ok(None);
        };

        Ok(Some(Comment {
            id,
            text,
            user_id,
            article_id,
        }))
    }

    fn find_comment_by_article_id(&self, article_id: u32) -> Result<Option<Comment>> {
        let mut conn = self.get_conn()?;

        let comment_row: Option<(u32, String, u32, u32)> = conn.exec_first(
            r#"
                SELECT
                    id,
                    text,
                    user_id,
                    article_id
                FROM comments
                WHERE article_id = :id
            "#,
            params! {
                "id" => article_id,
            },
        )?;

        let Some((id, text, user_id, article_id)) = comment_row else {
            return Ok(None);
        };

        Ok(Some(Comment {
            id,
            text,
            user_id,
            article_id,
        }))
    }

    fn find_comment_by_username(&self, username: &str) -> Result<Option<Comment>> {
        let mut conn = self.get_conn()?;

        let comment_row: Option<(u32, String, u32, u32)> = conn.exec_first(
            r#"
            SELECT
                c.id,
                c.text,
                c.user_id,
                c.article_id
            FROM
            comments c
            JOIN users u
            ON c.user_id = u.id
            WHERE
                u.username = ":username"
            "#,
            params! {
                "username" => username,
            },
        )?;

        let Some((id, text, user_id, article_id)) = comment_row else {
            return Ok(None);
        };

        Ok(Some(Comment {
            id,
            text,
            user_id,
            article_id,
        }))
    }
}
