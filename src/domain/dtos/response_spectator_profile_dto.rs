use crate::domain::dtos::response_comment_dto::CommentResponseDTO;

pub struct SpectatorProfileDTO {
    username: String,
    age: u8,
    display_name: String,
    nationality: String,
    comments: Vec<CommentResponseDTO>,
    average_comments_per_article: f32,

    player_elo: u32,
    is_chat_banned: bool,
    is_game_banned: bool,
    no_of_games_played: u32,
    has_pass: bool,
}

impl SpectatorProfileDTO {
    pub fn new(
        username: String,
        age: u8,
        display_name: String,
        nationality: String,
        comments: Vec<CommentResponseDTO>,
        average_comments_per_article: f32,
        player_elo: u32,
        is_chat_banned: bool,
        is_game_banned: bool,
        no_of_games_played: u32,
        has_pass: bool,
    ) -> Self {
        Self {
            username,
            age,
            display_name,
            nationality,
            comments,
            average_comments_per_article,
            player_elo,
            is_chat_banned,
            is_game_banned,
            no_of_games_played,
            has_pass,
        }
    }
}
