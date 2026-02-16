use crate::domain::comment::Comment;

pub trait CommentService {
    fn create_comment(&mut self, comment: Comment) -> Comment;
    fn update_comment(&mut self, comment: Comment, update_id: u32) -> Comment;
    fn delete_comment(&mut self, comment_id: u32);
    fn get_comments_by_article_id(&self, article_id: u32) -> Vec<Comment>;
    fn get_comment_by_author_username(&self, username: &str) -> Vec<Comment>;
}
