use mysql::Result;

use crate::{domain::comment::Comment, persistence::traits::CommentRepository};

pub struct CommentService<R: CommentRepository> {
    pub comm_repo: R,
}

impl<R: CommentRepository> CommentService<R> {
    pub fn new(comm_repo: R) -> Self {
        Self { comm_repo }
    }

    pub fn add_comment(&self, comment: Comment) -> Result<u64> {
        self.comm_repo.insert_comment(comment)
    }

    pub fn delete_comment(&self, comment: Comment) -> Result<()> {
        self.comm_repo.delete_comment(comment)
    }

    pub fn find_by_id(&self, comment_id: u32) -> Result<Option<Comment>> {
        self.comm_repo.find_comment_by_id(comment_id)
    }

    pub fn find_by_article_id(&self, article_id: u32) -> Result<Option<Comment>> {
        self.comm_repo.find_comment_by_article_id(article_id)
    }

    pub fn find_by_username(&self, username: &str) -> Result<Option<Comment>> {
        self.comm_repo.find_comment_by_username(username)
    }
}
