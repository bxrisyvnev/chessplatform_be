#[derive(Debug)]
pub struct ProfessionalPlayer {
    id: u32,
    username: String,
    password: String,
    age: u8,
    display_name: String,
    nationality: String,

    player_elo: u32,
    win_rate: f32,
    no_of_games_played: u32,
    follower_count: u32,
}

impl ProfessionalPlayer {
    pub fn new(
        id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        player_elo: u32,
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
            player_elo,
            win_rate,
            no_of_games_played,
            follower_count,
        }
    }
}
