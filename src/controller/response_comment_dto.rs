pub struct CommentResponseDTO {
    id: u32,
    text: String,
    user_id: u32,
    article_id: u32,
}

impl CommentResponseDTO {
    pub fn new(id: u32, text: String, user_id: u32, article_id: u32) -> Self {
        Self {
            id,
            text,
            user_id,
            article_id,
        }
    }
}
