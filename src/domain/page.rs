use crate::domain::pageable::Pageable;

#[derive(Debug)]
pub struct Page<T> {
    pub content: Vec<T>,
    pub total_elements: u32,
    pub total_pages: u32,
    pub page_number: u32,
    pub page_size: u32,
}

impl<T> Page<T> {
    pub fn new(content: Vec<T>, total_elements: u32, pageable: &Pageable) -> Self {
        let total_pages = (total_elements + pageable.size - 1) / pageable.size;
        Page {
            content,
            total_elements,
            total_pages,
            page_number: pageable.page,
            page_size: pageable.size,
        }
    }
}
