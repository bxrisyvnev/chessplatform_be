use crate::domain::log_in_token::LogInToken;

pub trait AuthenticationService {
    fn login(&self, username: &str, password: &str) -> LogInToken;
    fn logout(&self, user_id: u32);
}
