pub struct Pageable {
    pub page: usize,
    pub size: usize,
}

impl Pageable {
    fn offset(&self) -> usize {
        self.page * self.size
    }
}
