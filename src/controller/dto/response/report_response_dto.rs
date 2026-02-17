use chrono::NaiveDate;

pub struct ReportResponseDTO {
    id: u32,
    description: String,
    r#type: String,
    date_time: NaiveDate,
    user_id: u32,
}

impl ReportResponseDTO {
    pub fn new(
        id: u32,
        description: String,
        r#type: String,
        date_time: NaiveDate,
        user_id: u32,
    ) -> Self {
        Self {
            id,
            description,
            r#type,
            date_time,
            user_id,
        }
    }
}
