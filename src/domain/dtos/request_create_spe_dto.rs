pub struct CreateSpeDTO {
    update_id: u32,
    username: String,
    password: String,
    age: u8,
    display_name: String,
    nationality: String,

    player_elo: u32,
    is_chat_banned: bool,
    is_game_banned: bool,
    no_of_games_played: u32,
    has_pass: bool,
}

impl CreateSpeDTO {
    pub fn new(
        update_id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        player_elo: u32,
        is_chat_banned: bool,
        is_game_banned: bool,
        no_of_games_played: u32,
        has_pass: bool,
    ) -> Self {
        Self {
            update_id,
            username,
            password,
            age,
            display_name,
            nationality,
            player_elo,
            is_chat_banned,
            is_game_banned,
            no_of_games_played,
            has_pass,
        }
    }
}
