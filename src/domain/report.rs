use chrono::NaiveDate;

use crate::domain::{page::Page, pageable::Pageable};

#[derive(Clone, Debug)]
pub struct Report {
    pub id: u32,
    pub description: String,
    pub r#type: String,
    pub date_time: NaiveDate,
    pub user_id: u32,
}

impl Report {
    pub fn new(
        id: u32,
        description: String,
        r#type: String,
        date_time: NaiveDate,
        user_id: u32,
    ) -> Self {
        Self {
            id,
            description,
            r#type,
            date_time,
            user_id,
        }
    }
}

pub trait ReportRepository {
    fn save(&mut self, report: Report) -> Report;
    fn delete(&mut self, report: Report);
    fn find_by_id(&self, id: u32) -> Option<Report>;
    fn get_report_page(&self, pageable: Pageable) -> Option<Page<Report>>;
}
