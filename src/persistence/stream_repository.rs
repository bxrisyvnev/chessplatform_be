use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use crate::domain::stream::Stream;

pub struct StreamRepositoryTest {
    pub stream_db: Vec<Stream>,
}

impl StreamRepositoryTest {
    pub fn save(&mut self, stream: Stream) -> Stream {
        self.stream_db.push(stream.clone());
        stream
    }

    pub fn delete(&mut self, stream: Stream) {
        self.stream_db.retain(|rep| rep.id != stream.id);
    }

    pub fn find_by_id(&self, id: u32) -> Option<Stream> {
        self.stream_db.iter().find(|rep| rep.id == id).cloned()
    }

    pub fn get_stream_page(&self, pageable: Pageable) -> Option<Page<Stream>> {
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return None;
        }

        let start = (page - 1) * size;
        if start >= self.stream_db.len() {
            return None;
        }

        let end = (start + size).min(self.stream_db.len());
        let items: Vec<Stream> = self.stream_db[start..end].to_vec();

        Some(Page::new(items, (end - start) as u32, &pageable))
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::pageable::Pageable;
    use crate::domain::stream::Stream;

    fn make_repo() -> StreamRepositoryTest {
        StreamRepositoryTest { stream_db: vec![] }
    }

    fn make_stream(id: u32) -> Stream {
        Stream {
            id,
            ..Default::default()
        }
    }

    #[test]
    fn save_should_store_stream_and_return_it() {
        let mut repo = make_repo();
        let stream = make_stream(1);

        let saved = repo.save(stream.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(repo.stream_db.len(), 1);
        assert_eq!(repo.stream_db[0].id, 1);
    }

    #[test]
    fn delete_should_remove_stream_by_id() {
        let mut repo = make_repo();
        let s1 = make_stream(1);
        let s2 = make_stream(2);

        repo.save(s1.clone());
        repo.save(s2.clone());

        repo.delete(s1);

        assert_eq!(repo.stream_db.len(), 1);
        assert_eq!(repo.stream_db[0].id, 2);
    }

    #[test]
    fn find_by_id_should_return_stream_when_found() {
        let mut repo = make_repo();
        repo.save(make_stream(10));

        let result = repo.find_by_id(10);

        assert!(result.is_some());
        assert_eq!(result.unwrap().id, 10);
    }

    #[test]
    fn find_by_id_should_return_none_when_not_found() {
        let repo = make_repo();

        let result = repo.find_by_id(999);

        assert!(result.is_none());
    }

    #[test]
    fn get_stream_page_should_return_none_when_page_is_zero() {
        let repo = make_repo();
        let pageable = Pageable { page: 0, size: 10 };

        let result = repo.get_stream_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_stream_page_should_return_none_when_size_is_zero() {
        let repo = make_repo();
        let pageable = Pageable { page: 1, size: 0 };

        let result = repo.get_stream_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_stream_page_should_return_none_when_page_out_of_bounds() {
        let mut repo = make_repo();
        repo.save(make_stream(1));
        repo.save(make_stream(2));

        let pageable = Pageable { page: 2, size: 10 };

        let result = repo.get_stream_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_stream_page_should_return_first_page() {
        let mut repo = make_repo();
        repo.save(make_stream(1));
        repo.save(make_stream(2));
        repo.save(make_stream(3));

        let pageable = Pageable { page: 1, size: 2 };

        let result = repo.get_stream_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_stream_page_should_return_last_partial_page() {
        let mut repo = make_repo();
        repo.save(make_stream(1));
        repo.save(make_stream(2));
        repo.save(make_stream(3));

        let pageable = Pageable { page: 2, size: 2 };

        let result = repo.get_stream_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 1);
        assert_eq!(page.content[0].id, 3);
    }
}
