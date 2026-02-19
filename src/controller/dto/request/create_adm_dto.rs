pub struct CreateAdmDTO {
    update_id: u32,
    username: String,
    password: String,
    age: u8,
    display_name: String,
    nationality: String,

    monthly_salary: f32,
    contract_start_date: Date,
    contract_end_date: Date,
    address: String,
}

impl CreateAdmDTO {
    pub fn new(
        update_id: u32,
        username: String,
        password: String,
        age: u8,
        display_name: String,
        nationality: String,
        monthly_salary: f32,
        contract_start_date: Date,
        contract_end_date: Date,
        address: String,
    ) -> Self {
        Self {
            update_id,
            username,
            password,
            age,
            display_name,
            nationality,
            monthly_salary,
            contract_start_date,
            contract_end_date,
            address,
        }
    }
}
