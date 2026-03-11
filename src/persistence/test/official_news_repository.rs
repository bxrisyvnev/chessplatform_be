use crate::domain::official_news::OfficialNews;
use crate::domain::page::Page;
use crate::domain::pageable::Pageable;

pub struct OfficialNewsRepositoryTest {
    pub offn_db: Vec<OfficialNews>,
}

impl OfficialNewsRepositoryTest {
    pub fn save(&mut self, offn: OfficialNews) -> OfficialNews {
        self.offn_db.push(offn.clone());
        offn
    }

    pub fn get_offn_page(&self, pageable: Pageable) -> Option<Page<OfficialNews>> {
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return None;
        }

        let start = (page - 1) * size;
        if start >= self.offn_db.len() {
            return None;
        }

        let end = (start + size).min(self.offn_db.len());
        let items: Vec<OfficialNews> = self.offn_db[start..end].to_vec();

        Some(Page::new(items, (end - start) as u32, &pageable))
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::official_news::OfficialNews;
    use crate::domain::pageable::Pageable;

    fn make_repo() -> OfficialNewsRepositoryTest {
        OfficialNewsRepositoryTest { offn_db: vec![] }
    }

    fn make_official_news(id: u32) -> OfficialNews {
        OfficialNews {
            id,
            ..Default::default()
        }
    }

    #[test]
    fn save_should_store_official_news_and_return_it() {
        let mut repo = make_repo();
        let offn = make_official_news(1);

        let saved = repo.save(offn.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(repo.offn_db.len(), 1);
        assert_eq!(repo.offn_db[0].id, 1);
    }

    #[test]
    fn get_offn_page_should_return_none_when_page_is_zero() {
        let repo = make_repo();
        let pageable = Pageable { page: 0, size: 10 };

        let result = repo.get_offn_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_offn_page_should_return_none_when_size_is_zero() {
        let repo = make_repo();
        let pageable = Pageable { page: 1, size: 0 };

        let result = repo.get_offn_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_offn_page_should_return_none_when_page_is_out_of_bounds() {
        let mut repo = make_repo();
        repo.save(make_official_news(1));
        repo.save(make_official_news(2));

        let pageable = Pageable { page: 2, size: 10 };

        let result = repo.get_offn_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_offn_page_should_return_first_page() {
        let mut repo = make_repo();
        repo.save(make_official_news(1));
        repo.save(make_official_news(2));
        repo.save(make_official_news(3));

        let pageable = Pageable { page: 1, size: 2 };

        let result = repo.get_offn_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_offn_page_should_return_last_partial_page() {
        let mut repo = make_repo();
        repo.save(make_official_news(1));
        repo.save(make_official_news(2));
        repo.save(make_official_news(3));

        let pageable = Pageable { page: 2, size: 2 };

        let result = repo.get_offn_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 1);
        assert_eq!(page.content[0].id, 3);
    }
}
