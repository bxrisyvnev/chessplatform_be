use chrono::NaiveDate;

#[derive(Debug)]
pub struct Admin {
    id: i32,
    username: String,
    password: String,
    age: i8,
    display_name: String,
    nationality: String,

    monthly_salary: f32,
    contract_start_date: NaiveDate,
    contract_end_date: NaiveDate,
    adress: String,
}

impl Admin {
    pub fn new(
        id: i32,
        username: String,
        password: String,
        age: i8,
        display_name: String,
        nationality: String,
        monthly_salary: f32,
        contract_start_date: NaiveDate,
        contract_end_date: NaiveDate,
        adress: String,
    ) -> Self {
        Self {
            id,
            username,
            password,
            age,
            display_name,
            nationality,
            monthly_salary,
            contract_start_date,
            contract_end_date,
            adress,
        }
    }
}
