use crate::domain::admin::Admin;
use crate::domain::professional_player::ProfessionalPlayer;
use crate::domain::spectator_player::SpectatorPlayer;

pub struct UserRepositoryTest {
    pub admin_db: Vec<Admin>,
    pub pro_db: Vec<ProfessionalPlayer>,
    pub spec_db: Vec<SpectatorPlayer>,
}

impl UserRepositoryTest {
    pub fn save(&mut self, admin: Admin) -> Admin {
        self.admin_db.push(admin.clone());
        admin
    }

    pub fn delete(&mut self, admin: Admin) {
        self.admin_db.retain(|adm| adm.id != admin.id);
    }

    pub fn find_by_id(&self, id: u32) -> Option<Admin> {
        self.admin_db.iter().find(|adm| adm.id == id).cloned()
    }

    pub fn find_by_username(&self, username: &str) -> Option<Admin> {
        self.admin_db
            .iter()
            .find(|adm| adm.username == username)
            .cloned()
    }

    pub fn get_average_comments_by_user(&self, user_id: u32) {
        todo!();
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::admin::Admin;

    fn make_repo() -> UserRepositoryTest {
        UserRepositoryTest {
            admin_db: vec![],
            pro_db: vec![],
            spec_db: vec![],
        }
    }

    fn make_admin(id: u32, username: &str) -> Admin {
        Admin {
            id,
            username: username.to_string(),
            ..Default::default()
        }
    }

    #[test]
    fn save_should_store_admin_and_return_it() {
        let mut repo = make_repo();
        let admin = make_admin(1, "alice");

        let saved = repo.save(admin.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(saved.username, "alice");
        assert_eq!(repo.admin_db.len(), 1);
        assert_eq!(repo.admin_db[0].id, 1);
        assert_eq!(repo.admin_db[0].username, "alice");
    }

    #[test]
    fn delete_should_remove_admin_by_id() {
        let mut repo = make_repo();
        let admin1 = make_admin(1, "alice");
        let admin2 = make_admin(2, "bob");

        repo.save(admin1.clone());
        repo.save(admin2.clone());

        repo.delete(admin1);

        assert_eq!(repo.admin_db.len(), 1);
        assert_eq!(repo.admin_db[0].id, 2);
        assert_eq!(repo.admin_db[0].username, "bob");
    }

    #[test]
    fn find_by_id_should_return_admin_when_found() {
        let mut repo = make_repo();
        let admin = make_admin(10, "charlie");

        repo.save(admin);

        let found = repo.find_by_id(10);

        assert!(found.is_some());
        let found = found.unwrap();
        assert_eq!(found.id, 10);
        assert_eq!(found.username, "charlie");
    }

    #[test]
    fn find_by_id_should_return_none_when_not_found() {
        let repo = make_repo();

        let found = repo.find_by_id(999);

        assert!(found.is_none());
    }

    #[test]
    fn find_by_username_should_return_admin_when_found() {
        let mut repo = make_repo();
        repo.save(make_admin(1, "alice"));
        repo.save(make_admin(2, "bob"));

        let found = repo.find_by_username("bob");

        assert!(found.is_some());
        let found = found.unwrap();
        assert_eq!(found.id, 2);
        assert_eq!(found.username, "bob");
    }

    #[test]
    fn find_by_username_should_return_none_when_not_found() {
        let mut repo = make_repo();
        repo.save(make_admin(1, "alice"));

        let found = repo.find_by_username("not_here");

        assert!(found.is_none());
    }

    #[test]
    fn find_by_username_should_match_exact_username() {
        let mut repo = make_repo();
        repo.save(make_admin(1, "alice"));
        repo.save(make_admin(2, "alice1"));

        let found = repo.find_by_username("alice");

        assert!(found.is_some());
        let found = found.unwrap();
        assert_eq!(found.id, 1);
        assert_eq!(found.username, "alice");
    }
}
