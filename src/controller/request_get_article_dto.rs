pub struct GetArticleDTO {
    article_id: u32,
}

impl GetArticleDTO {
    pub fn new(article_id: u32) -> Self {
        Self { article_id }
    }
}
