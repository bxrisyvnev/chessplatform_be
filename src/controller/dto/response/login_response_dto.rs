pub struct LoginResponseDTO {
    access_token: String,
}

impl LoginResponseDTO {
    pub fn new(access_token: String) -> Self {
        Self { access_token }
    }
}
