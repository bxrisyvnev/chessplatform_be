pub struct CreateCommentDTO {
    update_id: u32,
    text: String,
    user_id: u32,
    article_id: u32,
}

impl CreateCommentDTO {
    pub fn new(update_id: u32, text: String, user_id: u32, article_id: u32) -> Self {
        Self {
            update_id,
            text,
            user_id,
            article_id,
        }
    }
}
