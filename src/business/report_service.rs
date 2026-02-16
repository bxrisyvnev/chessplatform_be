use crate::domain::{page::Page, report::Report};

pub trait ReportService {
    fn get_report_page(&self, page: u32, size: u32) -> Page<Report>;
    fn create_report(&mut self, report: Report) -> Report;
    fn delete_report(&mut self, report_id: u32);
}
