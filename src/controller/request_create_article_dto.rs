pub struct CreateArticleDTO {
    update_id: u32,
    article_title: String,
    image_url: String,
    author_id: u32,
    content_text: String,
    comments_ids: Vec<u32>,
}

impl CreateArticleDTO {
    pub fn new(
        update_id: u32,
        article_title: String,
        image_url: String,
        author_id: u32,
        content_text: String,
        comments_ids: Vec<u32>,
    ) -> Self {
        Self {
            update_id,
            article_title,
            image_url,
            author_id,
            content_text,
            comments_ids,
        }
    }
}
