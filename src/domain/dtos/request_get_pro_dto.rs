pub struct GetProDTO {
    id: u32,
}

impl GetProDTO {
    pub fn new(id: u32) -> Self {
        Self { id }
    }
}
