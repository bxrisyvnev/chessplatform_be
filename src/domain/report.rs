use chrono::NaiveDate;

#[derive(Debug)]
pub struct Report {
    id: i32,
    description: String,
    r#type: String,
    date_time: NaiveDate,
    user_id: i32,
}

impl Report {
    pub fn new(
        id: i32,
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
