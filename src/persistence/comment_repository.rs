use crate::domain::comment::Comment;

pub trait CommentRepository {
    fn find_by_id(&self, id: u32) -> Option<Comment>;
    fn delete(&mut self, comment: Comment);
    fn find_by_article_id(&self, article_id: u32) -> Vec<Comment>;
    fn find_by_username(&self, username: &str) -> Vec<Comment>;
}
