use std::collections::HashMap;
use std::sync::Mutex;

use mysql::Result;

use crate::domain::user::User;
use crate::persistence::traits::UserRepository;

pub struct UserRepositoryTest {
    pub users: Mutex<Vec<User>>,
    pub next_id: Mutex<u32>,
    pub average_comments_by_user_id: Mutex<HashMap<u32, f32>>,
}

impl UserRepositoryTest {
    pub fn new() -> Self {
        Self {
            users: Mutex::new(vec![]),
            next_id: Mutex::new(1),
            average_comments_by_user_id: Mutex::new(HashMap::new()),
        }
    }

    pub fn with_users(users: Vec<User>) -> Self {
        let next_id = users.iter().map(|u| u.id).max().unwrap_or(0) + 1;

        Self {
            users: Mutex::new(users),
            next_id: Mutex::new(next_id),
            average_comments_by_user_id: Mutex::new(HashMap::new()),
        }
    }

    pub fn set_average_comments(&self, user_id: u32, avg: f32) {
        self.average_comments_by_user_id
            .lock()
            .unwrap()
            .insert(user_id, avg);
    }
}

impl UserRepository for UserRepositoryTest {
    fn insert_user(&self, mut user: User) -> Result<u64> {
        if user.id == 0 {
            let mut next_id = self.next_id.lock().unwrap();
            user.id = *next_id;
            *next_id += 1;
        }

        let id = user.id as u64;
        self.users.lock().unwrap().push(user);
        Ok(id)
    }

    fn delete_user(&self, user: User) -> Result<()> {
        self.users.lock().unwrap().retain(|u| u.id != user.id);
        self.average_comments_by_user_id
            .lock()
            .unwrap()
            .remove(&user.id);
        Ok(())
    }

    fn find_user_by_id(&self, user_id: u32) -> Result<Option<User>> {
        Ok(self
            .users
            .lock()
            .unwrap()
            .iter()
            .find(|u| u.id == user_id)
            .cloned())
    }

    fn find_user_by_username(&self, username: &str) -> Result<Option<User>> {
        Ok(self
            .users
            .lock()
            .unwrap()
            .iter()
            .find(|u| u.username == username)
            .cloned())
    }

    fn get_average_comments_by_user_id(&self, user_id: u32) -> Result<Option<f32>> {
        Ok(self
            .average_comments_by_user_id
            .lock()
            .unwrap()
            .get(&user_id)
            .copied())
    }
}
