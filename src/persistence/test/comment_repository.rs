use crate::domain::comment::Comment;

pub struct CommentRepositoryTest {
    pub comment_db: Vec<Comment>,
}

impl CommentRepositoryTest {
    pub fn save(&mut self, comment: Comment) -> Comment {
        self.comment_db.push(comment.clone());
        comment
    }

    pub fn delete(&mut self, comment: Comment) {
        self.comment_db.retain(|rep| rep.id != comment.id);
    }

    pub fn find_by_id(&self, id: u32) -> Option<Comment> {
        self.comment_db.iter().find(|rep| rep.id == id).cloned()
    }

    pub fn find_by_article_id() {
        todo!()
    }

    pub fn fing_comments_by_username() {
        todo!()
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::comment::Comment;

    fn make_repo() -> CommentRepositoryTest {
        CommentRepositoryTest { comment_db: vec![] }
    }

    fn make_comment(id: u32) -> Comment {
        Comment {
            id,
            ..Default::default()
        }
    }

    #[test]
    fn save_should_store_comment_and_return_it() {
        let mut repo = make_repo();
        let comment = make_comment(1);

        let saved = repo.save(comment.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(repo.comment_db.len(), 1);
        assert_eq!(repo.comment_db[0].id, 1);
    }

    #[test]
    fn delete_should_remove_comment_by_id() {
        let mut repo = make_repo();
        let c1 = make_comment(1);
        let c2 = make_comment(2);

        repo.save(c1.clone());
        repo.save(c2.clone());

        repo.delete(c1);

        assert_eq!(repo.comment_db.len(), 1);
        assert_eq!(repo.comment_db[0].id, 2);
    }

    #[test]
    fn find_by_id_should_return_comment_when_found() {
        let mut repo = make_repo();
        repo.save(make_comment(10));

        let result = repo.find_by_id(10);

        assert!(result.is_some());
        assert_eq!(result.unwrap().id, 10);
    }

    #[test]
    fn find_by_id_should_return_none_when_not_found() {
        let repo = make_repo();

        let result = repo.find_by_id(999);

        assert!(result.is_none());
    }

    #[test]
    #[should_panic]
    fn find_by_article_id_should_panic_until_implemented() {
        CommentRepositoryTest::find_by_article_id();
    }

    #[test]
    #[should_panic]
    fn fing_comments_by_username_should_panic_until_implemented() {
        CommentRepositoryTest::fing_comments_by_username();
    }
}
