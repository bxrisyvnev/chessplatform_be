use chrono::NaiveDate;

pub struct CreateReportDTO {
    pub update_id: u32,
    pub description: String,
    pub r#type: String,
    pub date_time: NaiveDate,
    pub user_id: u32,
}

impl CreateReportDTO {
    pub fn new(
        update_id: u32,
        description: String,
        r#type: String,
        date_time: NaiveDate,
        user_id: u32,
    ) -> Self {
        Self {
            update_id,
            description,
            r#type,
            date_time,
            user_id,
        }
    }
}
