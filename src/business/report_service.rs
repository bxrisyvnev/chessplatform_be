use crate::{
    domain::{page::Page, pageable::Pageable, report::Report},
    persistence::report_repository::ReportRepositoryTest,
};

pub struct ReportService {
    pub repo: ReportRepositoryTest,
}

impl ReportService {
    pub fn add_report(&mut self, report: Report) -> Report {
        self.repo.save(report)
    }

    pub fn delete_report(&mut self, report: Report) {
        self.repo.delete(report)
    }

    pub fn find_report_by_id(&self, id: u32) -> Option<Report> {
        self.repo.find_by_id(id)
    }

    pub fn get_report_page(&self, pageable: Pageable) -> Option<Page<Report>> {
        self.repo.get_report_page(pageable)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::pageable::Pageable;
    use crate::domain::report::Report;
    use crate::persistence::report_repository::ReportRepositoryTest;

    fn make_service() -> ReportService {
        ReportService {
            repo: ReportRepositoryTest { report_db: vec![] },
        }
    }

    fn make_report(id: u32) -> Report {
        Report {
            id,
            ..Default::default()
        }
    }

    #[test]
    fn add_report_should_save_and_return_report() {
        let mut service = make_service();
        let report = make_report(1);

        let saved = service.add_report(report.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(service.repo.report_db.len(), 1);
        assert_eq!(service.repo.report_db[0].id, 1);
    }

    #[test]
    fn delete_report_should_remove_report() {
        let mut service = make_service();
        let report1 = make_report(1);
        let report2 = make_report(2);

        service.add_report(report1.clone());
        service.add_report(report2.clone());

        service.delete_report(report1);

        assert_eq!(service.repo.report_db.len(), 1);
        assert_eq!(service.repo.report_db[0].id, 2);
    }

    #[test]
    fn find_report_by_id_should_return_report_when_found() {
        let mut service = make_service();
        service.add_report(make_report(10));

        let found = service.find_report_by_id(10);

        assert!(found.is_some());
        assert_eq!(found.unwrap().id, 10);
    }

    #[test]
    fn find_report_by_id_should_return_none_when_not_found() {
        let service = make_service();

        let found = service.find_report_by_id(999);

        assert!(found.is_none());
    }

    #[test]
    fn get_report_page_should_return_none_for_invalid_page() {
        let service = make_service();
        let pageable = Pageable { page: 0, size: 10 };

        let result = service.get_report_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_report_page_should_return_first_page() {
        let mut service = make_service();
        service.add_report(make_report(1));
        service.add_report(make_report(2));
        service.add_report(make_report(3));

        let pageable = Pageable { page: 1, size: 2 };

        let result = service.get_report_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_report_page_should_return_last_partial_page() {
        let mut service = make_service();
        service.add_report(make_report(1));
        service.add_report(make_report(2));
        service.add_report(make_report(3));

        let pageable = Pageable { page: 2, size: 2 };

        let result = service.get_report_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 1);
        assert_eq!(page.content[0].id, 3);
    }

    #[test]
    fn get_report_page_should_return_none_when_page_out_of_bounds() {
        let mut service = make_service();
        service.add_report(make_report(1));
        service.add_report(make_report(2));

        let pageable = Pageable { page: 2, size: 10 };

        let result = service.get_report_page(pageable);

        assert!(result.is_none());
    }
}
