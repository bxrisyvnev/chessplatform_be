pub struct Admin {
    pub id: u32,
    pub username: String,
    pub password: String,
    pub age: u8,
    pub display_name: String,
    pub nationality: String,

    pub monthly_salary: f32,
    pub contract_start_date: Date,
    pub contract_end_date: Date,
    pub adress: String,
}

impl Admin {
    pub fn new(
        id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        monthly_salary: f32,
        contract_start_date: Date,
        contract_end_date: Date,
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
