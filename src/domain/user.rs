use chrono::NaiveDate;

#[derive(Debug, Clone, Default)]
pub struct User {
    pub id: u32,
    pub username: String,
    pub password: String,
    pub age: u8,
    pub display_name: String,
    pub nationality: String,

    pub user_type: UserType,

    pub monthly_salary: Option<f32>,
    pub contract_start_date: Option<NaiveDate>,
    pub contract_end_date: Option<NaiveDate>,
    pub address: Option<String>,

    pub pro_player_elo: Option<u32>,
    pub win_rate: Option<f32>,
    pub no_of_pro_games_played: Option<u32>,
    pub follower_count: Option<u32>,

    pub spec_player_elo: Option<u32>,
    pub is_chat_banned: Option<bool>,
    pub is_game_banned: Option<bool>,
    pub no_of_spec_games_played: Option<u32>,
    pub has_pass: Option<bool>,
}

#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub enum UserType {
    Admin,
    #[default]
    SpectatorPlayer,
    ProfecionalPlayer,
}

impl User {
    pub fn new_admin(
        id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        monthly_salary: f32,
        contract_start_date: NaiveDate,
        contract_end_date: NaiveDate,
        address: String,
    ) -> Self {
        Self {
            id,
            username,
            password,
            age,
            display_name,
            nationality,
            monthly_salary: Some(monthly_salary),
            contract_start_date: Some(contract_start_date),
            contract_end_date: Some(contract_end_date),
            address: Some(address),
            user_type: UserType::Admin,

            spec_player_elo: None,
            is_chat_banned: None,
            is_game_banned: None,
            no_of_pro_games_played: None,
            has_pass: None,
            pro_player_elo: None,
            win_rate: None,
            no_of_spec_games_played: None,
            follower_count: None,
        }
    }

    pub fn new_pro(
        id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        pro_player_elo: u32,
        win_rate: f32,
        no_of_games_played: u32,
        follower_count: u32,
    ) -> Self {
        Self {
            id,
            username,
            password,
            age,
            display_name,
            nationality,
            pro_player_elo: Some(pro_player_elo),
            win_rate: Some(win_rate),
            no_of_pro_games_played: Some(no_of_games_played),
            follower_count: Some(follower_count),
            user_type: UserType::ProfecionalPlayer,

            spec_player_elo: None,
            is_chat_banned: None,
            is_game_banned: None,
            no_of_spec_games_played: None,
            has_pass: None,
            monthly_salary: None,
            contract_start_date: None,
            contract_end_date: None,
            address: None,
        }
    }

    pub fn new_spec(
        id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        spec_player_elo: u32,
        is_chat_banned: bool,
        is_game_banned: bool,
        no_of_spec_games_played: u32,
        has_pass: bool,
    ) -> Self {
        Self {
            id,
            username,
            password,
            age,
            display_name,
            nationality,
            spec_player_elo: Some(spec_player_elo),
            is_chat_banned: Some(is_chat_banned),
            is_game_banned: Some(is_game_banned),
            no_of_spec_games_played: Some(no_of_spec_games_played),
            has_pass: Some(has_pass),
            user_type: UserType::SpectatorPlayer,

            monthly_salary: None,
            contract_start_date: None,
            contract_end_date: None,
            address: None,
            pro_player_elo: None,
            win_rate: None,
            no_of_pro_games_played: None,
            follower_count: None,
        }
    }
}
