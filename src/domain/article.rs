#[derive(Debug)]
pub struct Article {
    id: i32,
    article_title: String,
    image_url: String,
    author_id: i32,
    author_name: String,
    content_text: String,
    comments_ids: Vec<i32>,
}

impl Article {
    pub fn new(
        id: i32,
        article_title: String,
        image_url: String,
        author_id: i32,
        author_name: String,
        content_text: String,
        comments_ids: Vec<i32>,
    ) -> Self {
        Self {
            id,
            article_title,
            image_url,
            author_id,
            author_name,
            content_text,
            comments_ids,
        }
    }
}
