pub struct LoginRequestDTO {
    username: String,
    password: String,
}

impl LoginRequestDTO {
    pub fn new(username: String, password: String) -> Self {
        Self { username, password }
    }
}
