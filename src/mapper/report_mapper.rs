use crate::domain::{dtos::request_create_report_dto::CreateReportDTO, report::Report};

pub struct ReportMapper;

impl ReportMapper {
    pub fn request_to_object(&self, dto: CreateReportDTO) -> Report {
        return Report::new(dto.id);
    }
}
