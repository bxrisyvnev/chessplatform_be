use crate::domain::report::Report;
use crate::persistence::report_repository::ReportRepository;

pub struct ReportRepositoryImpl {
    pub report_db: Vec<Report>,
}

impl ReportRepository for ReportRepositoryImpl {
    fn save(&mut self, report: Report) -> Report {
        self.report_db.push(report.clone());
        report
    }

    fn delete(&mut self, report: Report) {
        let mut i = 0;
        for rep in self.report_db.clone() {
            if rep.id == report.id {
                self.report_db.remove(i);
            }
            i += 1;
        }
    }

    fn find_by_id(&self, id: u32) -> Option<Report> {
        for rep in self.report_db.clone() {
            if rep.id == id {
                return Some(rep);
            }
        }
        None
    }
}
