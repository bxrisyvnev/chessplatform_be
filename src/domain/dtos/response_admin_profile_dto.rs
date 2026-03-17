use crate::controller::response_comment_dto::CommentResponseDTO;

pub struct AdminProfileDTO {
    username: String,
    age: u8,
    display_name: String,
    nationality: String,
    comments: Vec<CommentResponseDTO>,
    average_comments_per_article: f32,

    monthly_salary: f32,
    contract_start_date: String,
    contract_end_date: String,
    address: String,
}

impl AdminProfileDTO {
    pub fn new(
        username: String,
        age: u8,
        display_name: String,
        nationality: String,
        comments: Vec<CommentResponseDTO>,
        average_comments_per_article: f32,
        monthly_salary: f32,
        contract_start_date: String,
        contract_end_date: String,
        address: String,
    ) -> Self {
        Self {
            username,
            age,
            display_name,
            nationality,
            comments,
            average_comments_per_article,
            monthly_salary,
            contract_start_date,
            contract_end_date,
            address,
        }
    }
}
