use crate::domain::pageable::Pageable;

pub struct Page<T> {
    content: Vec<T>,
    total_elements: usize,
    total_pages: usize,
    page_number: usize,
    page_size: usize,
}

impl<T> Page<T> {
    pub fn new(content: Vec<T>, total_elements: usize, pageable: &Pageable) -> Self {
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
