use mysql::Result;

use crate::{domain::user::User, persistence::traits::UserRepository};

pub struct UserService<R: UserRepository> {
    pub user_repo: R,
}

impl<R: UserRepository> UserService<R> {
    pub fn new(user_repo: R) -> Self {
        Self { user_repo }
    }

    pub fn add_user(&self, user: User) -> Result<u64> {
        self.user_repo.insert_user(user)
    }

    pub fn delete_user(&self, user: User) -> Result<()> {
        self.user_repo.delete_user(user)
    }

    pub fn get_by_id(&self, user_id: u32) -> Result<Option<User>> {
        self.user_repo.find_user_by_id(user_id)
    }

    pub fn get_by_username(&self, username: &str) -> Result<Option<User>> {
        self.user_repo.find_user_by_username(username)
    }

    pub fn get_avg_comments_by_user_id(&self, user_id: u32) -> Result<Option<f32>> {
        self.user_repo.get_average_comments_by_user_id(user_id)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::user::User;
    use crate::persistence::test::user_repository::UserRepositoryTest;

    fn make_user(id: u32, username: &str) -> User {
        User {
            id,
            username: username.to_string(),
            ..Default::default()
        }
    }

    fn make_service() -> UserService<UserRepositoryTest> {
        UserService::new(UserRepositoryTest::new())
    }

    #[test]
    fn new_should_store_repository() {
        let service = make_service();

        assert!(service.user_repo.users.lock().unwrap().is_empty());
        assert_eq!(*service.user_repo.next_id.lock().unwrap(), 1);
    }

    #[test]
    fn add_user_should_insert_and_return_id() {
        let service = make_service();

        let id = service.add_user(make_user(1, "boris")).unwrap();

        assert_eq!(id, 1);
        let users = service.user_repo.users.lock().unwrap();
        assert_eq!(users.len(), 1);
        assert_eq!(users[0].username, "boris");
    }

    #[test]
    fn add_user_should_auto_assign_id_when_zero() {
        let service = make_service();

        let id = service.add_user(make_user(0, "alice")).unwrap();

        assert_eq!(id, 1);
        let users = service.user_repo.users.lock().unwrap();
        assert_eq!(users.len(), 1);
        assert_eq!(users[0].id, 1);
        assert_eq!(users[0].username, "alice");
    }

    #[test]
    fn delete_user_should_remove_existing_user() {
        let service = make_service();
        let user = make_user(1, "boris");
        service.add_user(user.clone()).unwrap();

        service.delete_user(user).unwrap();

        let result = service.get_by_id(1).unwrap();
        assert!(result.is_none());
        assert!(service.user_repo.users.lock().unwrap().is_empty());
    }

    #[test]
    fn get_by_id_should_return_user_when_found() {
        let service = make_service();
        service.add_user(make_user(1, "boris")).unwrap();

        let result = service.get_by_id(1).unwrap();

        assert!(result.is_some());
        let user = result.unwrap();
        assert_eq!(user.id, 1);
        assert_eq!(user.username, "boris");
    }

    #[test]
    fn get_by_id_should_return_none_when_missing() {
        let service = make_service();

        let result = service.get_by_id(999).unwrap();

        assert!(result.is_none());
    }

    #[test]
    fn get_by_username_should_return_user_when_found() {
        let service = make_service();
        service.add_user(make_user(1, "boris")).unwrap();
        service.add_user(make_user(2, "alice")).unwrap();

        let result = service.get_by_username("alice").unwrap();

        assert!(result.is_some());
        let user = result.unwrap();
        assert_eq!(user.id, 2);
        assert_eq!(user.username, "alice");
    }

    #[test]
    fn get_by_username_should_return_none_when_missing() {
        let service = make_service();
        service.add_user(make_user(1, "boris")).unwrap();

        let result = service.get_by_username("charlie").unwrap();

        assert!(result.is_none());
    }

    #[test]
    fn get_avg_comments_by_user_id_should_return_value_when_present() {
        let service = make_service();
        service.user_repo.set_average_comments(7, 3.5);

        let result = service.get_avg_comments_by_user_id(7).unwrap();

        assert_eq!(result, Some(3.5));
    }

    #[test]
    fn get_avg_comments_by_user_id_should_return_none_when_missing() {
        let service = make_service();

        let result = service.get_avg_comments_by_user_id(7).unwrap();

        assert_eq!(result, None);
    }

    #[test]
    fn delete_user_should_also_remove_stored_average_comments() {
        let service = make_service();
        let user = make_user(5, "boris");
        service.add_user(user.clone()).unwrap();
        service.user_repo.set_average_comments(5, 2.25);

        service.delete_user(user).unwrap();

        let avg = service.get_avg_comments_by_user_id(5).unwrap();
        assert_eq!(avg, None);
    }
}
