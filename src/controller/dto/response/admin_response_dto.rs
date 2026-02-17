pub struct AdminResponseDTO {
    id: u32,
    age: u8,
    username: String,
    display_name: String,
    nationality: String,

    monthly_salary: f32,
    contract_start_date: String,
    contract_end_date: String,
    address: String,
}

impl AdminResponseDTO {
    pub fn new(
        id: u32,
        age: u8,
        username: String,
        display_name: String,
        nationality: String,
        monthly_salary: f32,
        contract_start_date: String,
        contract_end_date: String,
        address: String,
    ) -> Self {
        Self {
            id,
            age,
            username,
            display_name,
            nationality,
            monthly_salary,
            contract_start_date,
            contract_end_date,
            address,
        }
    }
}
