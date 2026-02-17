pub struct ProfessionalResponseDTO {
    id: u32,
    age: u8,
    username: String,
    display_name: String,
    nationality: String,

    player_elo: u32,
    win_rate: f32,
    no_of_games_played: u32,
    follower_count: u32,
}

impl ProfessionalResponseDTO {
    pub fn new(
        id: u32,
        age: u8,
        username: String,
        display_name: String,
        nationality: String,
        player_elo: u32,
        win_rate: f32,
        no_of_games_played: u32,
        follower_count: u32,
    ) -> Self {
        Self {
            id,
            age,
            username,
            display_name,
            nationality,
            player_elo,
            win_rate,
            no_of_games_played,
            follower_count,
        }
    }
}
