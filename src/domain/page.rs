#[derive(Debug)]
pub struct Page<T> {
    pub items: Vec<T>,
    pub page: usize,
    pub size: usize,
    pub total: usize,
}
