pub struct SpectatorResponseDTO {
    id: u32,
    age: u8,
    username: String,
    display_name: String,
    nationality: String,

    player_elo: u32,
    is_chat_banned: bool,
    is_game_banned: bool,
    no_of_games_played: u32,
    chroma: String,
    has_pass: bool,
}

impl SpectatorResponseDTO {
    pub fn new(
        id: u32,
        age: u8,
        username: String,
        display_name: String,
        nationality: String,
        player_elo: u32,
        is_chat_banned: bool,
        is_game_banned: bool,
        no_of_games_played: u32,
        chroma: String,
        has_pass: bool,
    ) -> Self {
        Self {
            id,
            age,
            username,
            display_name,
            nationality,
            player_elo,
            is_chat_banned,
            is_game_banned,
            no_of_games_played,
            chroma,
            has_pass,
        }
    }
}
