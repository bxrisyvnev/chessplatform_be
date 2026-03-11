use dotenvy::dotenv;
use mysql::prelude::*;
use mysql::{params, Opts, Pool, PooledConn, Result};
use std::env;

use crate::domain::article::Article;

pub fn create_pool() -> Pool {
    dotenv().ok();

    let database_url = env::var("DATABASE_URL").expect("DATABASE_URL must be set");

    let opts = Opts::from_url(&database_url).unwrap();
    Pool::new(opts).unwrap()
}

pub fn get_conn(pool: &Pool) -> Result<PooledConn> {
    pool.get_conn()
}

pub fn insert_article(conn: &mut PooledConn, article: Article) -> Result<u64> {
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
