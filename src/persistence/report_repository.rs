use crate::domain::{page::Page, pageable::Pageable, report::Report};

pub trait ReportRepository {
    fn find_by_id(&self, id: u32) -> Option<Report>;
    // fn get_report_page(&self, pageable: Pageable) -> Page<Report>;
    fn save(&mut self, report: Report) -> Report;
    fn delete(&mut self, report: Report);
}
