use sea_orm::{prelude::async_trait, DbErr};

use crate::domain::{page::Page, pageable::Pageable, report::Report};

#[async_trait::async_trait]
pub trait ReportRepository {
    async fn find_by_id(&self, id: u32) -> Result<Option<Report>, DbErr>;
    async fn get_report_page(&self, pageable: Pageable) -> Result<Page<Report>, DbErr>;
    async fn save(&self, report: Report) -> Result<Report, DbErr>;
    async fn delete(&self, report: Report) -> Result<(), DbErr>;
}
