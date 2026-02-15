#[derive(Debug)]
pub struct Comment {
    id: i32,
    text: String,
    user_id: i32,
    article_id: i32,
}

impl Comment {
    pub fn new(id: i32, text: String, user_id: i32, article_id: i32) -> Self {
        Self {
            id,
            text,
            user_id,
            article_id,
        }
    }
}
