pub struct GetSpeDTO {
    id: u32,
}

impl GetSpeDTO {
    pub fn new(id: u32) -> Self {
        Self { id }
    }
}
