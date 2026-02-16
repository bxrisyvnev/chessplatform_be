pub struct GetCommentDTO {
    comment_id: u32,
}

impl GetCommentDTO {
    pub fn new(comment_id: u32) -> Self {
        Self { comment_id }
    }
}
