use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use crate::domain::report::Report;

pub struct ReportRepositoryTest {
    pub report_db: Vec<Report>,
}

impl ReportRepositoryTest {
    pub fn save(&mut self, report: Report) -> Report {
        self.report_db.push(report.clone());
        report
    }

    pub fn delete(&mut self, report: Report) {
        self.report_db.retain(|rep| rep.id != report.id);
    }

    pub fn find_by_id(&self, id: u32) -> Option<Report> {
        self.report_db.iter().find(|rep| rep.id == id).cloned()
    }

    pub fn get_report_page(&self, pageable: Pageable) -> Option<Page<Report>> {
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return None;
        }

        let start = (page - 1) * size;
        if start >= self.report_db.len() {
            return None;
        }

        let end = (start + size).min(self.report_db.len());
        let items: Vec<Report> = self.report_db[start..end].to_vec();

        Some(Page::new(items, (end - start) as u32, &pageable))
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::pageable::Pageable;
    use crate::domain::report::Report;

    fn make_repo() -> ReportRepositoryTest {
        ReportRepositoryTest { report_db: vec![] }
    }

    fn make_report(id: u32) -> Report {
        Report {
            id,
            ..Default::default()
        }
    }

    #[test]
    fn save_should_store_report_and_return_it() {
        let mut repo = make_repo();
        let report = make_report(1);

        let saved = repo.save(report.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(repo.report_db.len(), 1);
        assert_eq!(repo.report_db[0].id, 1);
    }

    #[test]
    fn delete_should_remove_report_by_id() {
        let mut repo = make_repo();
        let report1 = make_report(1);
        let report2 = make_report(2);

        repo.save(report1.clone());
        repo.save(report2.clone());

        repo.delete(report1);

        assert_eq!(repo.report_db.len(), 1);
        assert_eq!(repo.report_db[0].id, 2);
    }

    #[test]
    fn find_by_id_should_return_report_when_found() {
        let mut repo = make_repo();
        let report = make_report(10);

        repo.save(report);

        let found = repo.find_by_id(10);

        assert!(found.is_some());
        assert_eq!(found.unwrap().id, 10);
    }

    #[test]
    fn find_by_id_should_return_none_when_not_found() {
        let repo = make_repo();

        let found = repo.find_by_id(999);

        assert!(found.is_none());
    }

    #[test]
    fn get_report_page_should_return_none_when_page_is_zero() {
        let repo = make_repo();
        let pageable = Pageable { page: 0, size: 10 };

        let result = repo.get_report_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_report_page_should_return_none_when_size_is_zero() {
        let repo = make_repo();
        let pageable = Pageable { page: 1, size: 0 };

        let result = repo.get_report_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_report_page_should_return_none_when_start_is_out_of_bounds() {
        let mut repo = make_repo();
        repo.save(make_report(1));
        repo.save(make_report(2));

        let pageable = Pageable { page: 2, size: 10 };

        let result = repo.get_report_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_report_page_should_return_first_page() {
        let mut repo = make_repo();
        repo.save(make_report(1));
        repo.save(make_report(2));
        repo.save(make_report(3));

        let pageable = Pageable { page: 1, size: 2 };

        let result = repo.get_report_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_report_page_should_return_last_partial_page() {
        let mut repo = make_repo();
        repo.save(make_report(1));
        repo.save(make_report(2));
        repo.save(make_report(3));

        let pageable = Pageable { page: 2, size: 2 };

        let result = repo.get_report_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 1);
        assert_eq!(page.content[0].id, 3);
    }
}
