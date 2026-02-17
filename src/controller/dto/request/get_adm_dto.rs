pub struct GetAdmDTO {
    id: u32,
}

impl GetAdmDTO {
    pub fn new(id: u32) -> Self {
        Self { id }
    }
}
