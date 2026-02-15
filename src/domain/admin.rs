use chrono::NaiveDate;

pub struct Admin {
    pub id: i32,
    pub username: String,
    pub password: String,
    pub age: i8,
    pub display_name: String,
    pub nationality: String,

    pub monthly_salary: f32,
    pub contract_start_date: NaiveDate,
    pub contract_end_date: NaiveDate,
    pub adress: String,
}

impl Admin {}
