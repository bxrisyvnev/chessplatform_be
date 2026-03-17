pub struct CreateProDTO {
    update_id: u32,
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

impl CreateProDTO {
    pub fn new(
        update_id: u32,
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
            update_id,
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
