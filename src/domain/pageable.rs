pub struct Pageable {
    pub page: u64,
    pub size: u64,
}

impl Pageable {
    fn offset(&self) -> u64 {
        self.page * self.size
    }
}
