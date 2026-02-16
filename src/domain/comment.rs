#[derive(Debug)]
pub struct Comment {
    id: u32,
    text: String,
    user_id: u32,
    article_id: u32,
}

impl Comment {
    pub fn new(id: u32, text: String, user_id: u32, article_id: u32) -> Self {
        Self {
            id,
            text,
            user_id,
            article_id,
        }
    }
}
