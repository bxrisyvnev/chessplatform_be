#[derive(Debug)]
pub struct SpectatorPlayer {
    id: i32,
    username: String,
    password: String,
    age: i8,
    display_name: String,
    nationality: String,

    player_elo: i32,
    is_chat_banned: bool,
    is_game_banned: bool,
    no_of_game_played: i32,
    has_pass: bool,
}

impl SpectatorPlayer {
    pub fn new(
        id: i32,
        username: String,
        password: String,
        age: i8,
        display_name: String,
        nationality: String,
        player_elo: i32,
        is_chat_banned: bool,
        is_game_banned: bool,
        no_of_game_played: i32,
        has_pass: bool,
    ) -> Self {
        Self {
            id,
            username,
            password,
            age,
            display_name,
            nationality,
            player_elo,
            is_chat_banned,
            is_game_banned,
            no_of_game_played,
            has_pass,
        }
    }
}
