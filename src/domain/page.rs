use crate::domain::pageable::Pageable;

pub struct Page<T> {
    content: Vec<T>,
    total_elements: u64,
    total_pages: u64,
    page_number: u64,
    page_size: u64,
}

impl<T> Page<T> {
    pub fn new(content: Vec<T>, total_elements: u64, pageable: &Pageable) -> Self {
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
