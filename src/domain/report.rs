use chrono::NaiveDate;

#[derive(Clone, Debug)]
pub struct Report {
    pub id: u32,
    pub description: String,
    pub r#type: String,
    pub date_time: NaiveDate,
    pub user_id: i32,
}

impl Report {
    pub fn new(
        id: u32,
        description: String,
        r#type: String,
        date_time: NaiveDate,
        user_id: i32,
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
