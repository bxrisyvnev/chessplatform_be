use crate::{domain::comment::Comment, persistence::comment_repository::CommentRepositoryTest};

pub struct CommentService {
    comm_repo: CommentRepositoryTest,
}

impl CommentService {
    pub fn create_comment(&mut self, comment: Comment) -> Comment {
        self.comm_repo.save(comment)
    }

    pub fn update_comment(&mut self, comment: Comment, update_id: u32) {
        if let Some(_existing_commen) = self.comm_repo.find_by_id(update_id) {
            self.comm_repo.save(comment);
        }
    }

    pub fn delete_comment(&mut self, id: u32) {
        if let Some(comment) = self.comm_repo.find_by_id(id) {
            self.comm_repo.delete(comment);
        }
    }

    pub fn get_comment_by_article_id() {
        todo!()
    }

    pub fn get_comment_by_author_username() {
        todo!()
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::comment::Comment;
    use crate::persistence::comment_repository::CommentRepositoryTest;

    fn make_service() -> CommentService {
        CommentService {
            comm_repo: CommentRepositoryTest { comment_db: vec![] },
        }
    }

    fn make_comment(id: u32) -> Comment {
        Comment {
            id,
            ..Default::default()
        }
    }

    #[test]
    fn create_comment_should_save_and_return_comment() {
        let mut service = make_service();
        let comment = make_comment(1);

        let saved = service.create_comment(comment.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(service.comm_repo.comment_db.len(), 1);
        assert_eq!(service.comm_repo.comment_db[0].id, 1);
    }

    #[test]
    fn update_comment_should_add_new_comment_if_existing_found() {
        let mut service = make_service();

        // existing comment
        service.create_comment(make_comment(1));

        // update attempt
        let updated = make_comment(2);
        service.update_comment(updated.clone(), 1);

        // because update_comment uses save(), it pushes a new comment
        assert_eq!(service.comm_repo.comment_db.len(), 2);
        assert_eq!(service.comm_repo.comment_db[1].id, 2);
    }

    #[test]
    fn update_comment_should_do_nothing_if_comment_not_found() {
        let mut service = make_service();

        let updated = make_comment(2);
        service.update_comment(updated, 999);

        assert_eq!(service.comm_repo.comment_db.len(), 0);
    }

    #[test]
    fn delete_comment_should_remove_comment() {
        let mut service = make_service();
        service.create_comment(make_comment(1));
        service.create_comment(make_comment(2));

        service.delete_comment(1);

        assert_eq!(service.comm_repo.comment_db.len(), 1);
        assert_eq!(service.comm_repo.comment_db[0].id, 2);
    }

    #[test]
    fn delete_comment_should_do_nothing_if_comment_not_found() {
        let mut service = make_service();
        service.create_comment(make_comment(1));

        service.delete_comment(999);

        assert_eq!(service.comm_repo.comment_db.len(), 1);
    }

    #[test]
    #[should_panic]
    fn get_comment_by_article_id_should_panic_until_implemented() {
        CommentService::get_comment_by_article_id();
    }

    #[test]
    #[should_panic]
    fn get_comment_by_author_username_should_panic_until_implemented() {
        CommentService::get_comment_by_author_username();
    }
}
