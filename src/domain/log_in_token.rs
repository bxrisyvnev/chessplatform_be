pub struct LogInToken {
    access_token: String,
}

impl LogInToken {
    pub fn new(access_token: String) -> Self {
        Self { access_token }
    }
}
