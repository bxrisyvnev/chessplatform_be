pub struct Pageable {
    pub page: u32,
    pub size: u32,
}

impl Pageable {
    fn offset(&self) -> u32 {
        self.page * self.size
    }
}
