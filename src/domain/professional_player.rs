#[derive(Debug)]
pub struct ProfessionalPlayer {
    id: i32,
    username: String,
    password: String,
    age: i8,
    display_name: String,
    nationality: String,

    player_elo: i32,
    win_rate: f32,
    no_of_games_played: i32,
    follower_count: i32,
}

impl ProfessionalPlayer {
    pub fn new(
        id: i32,
        username: String,
        password: String,
        age: i8,
        display_name: String,
        nationality: String,
        player_elo: i32,
        win_rate: f32,
        no_of_games_played: i32,
        follower_count: i32,
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
