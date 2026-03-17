use crate::domain::dtos::response_comment_dto::CommentResponseDTO;

pub struct ProfessionalProfileDTO {
    username: String,
    age: u8,
    display_name: String,
    nationality: String,
    comments: Vec<CommentResponseDTO>,
    average_comments_per_article: f32,

    player_elo: u32,
    win_rate: f32,
    no_of_games_played: u32,
    follower_count: u32,
    chroma: String,
}

impl ProfessionalProfileDTO {
    pub fn new(
        username: String,
        age: u8,
        display_name: String,
        nationality: String,
        comments: Vec<CommentResponseDTO>,
        average_comments_per_article: f32,
        player_elo: u32,
        win_rate: f32,
        no_of_games_played: u32,
        follower_count: u32,
        chroma: String,
    ) -> Self {
        Self {
            username,
            age,
            display_name,
            nationality,
            comments,
            average_comments_per_article,
            player_elo,
            win_rate,
            no_of_games_played,
            follower_count,
            chroma,
        }
    }
}
