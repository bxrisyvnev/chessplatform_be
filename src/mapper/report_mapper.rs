use crate::domain::report::Report;
use crate::persistence::r#impl::entity::report_entity;

use sea_orm::ActiveValue::Set;

pub struct ReportMapper;

impl ReportMapper {
    pub fn entity_to_domain(entity: report_entity::Model) -> Report {
        let return_report: Report = Report::new(
            entity.id,
            entity.description,
            entity.r#type,
            entity.date_time,
            entity.user_id,
        );
        return_report
    }

    pub fn object_to_active(domain: &Report) -> report::ActiveModel {
        report::ActiveModel {
            id: domain.id.map(Set).unwrap_or_default(),
            description: Set(domain.description.clone()),
            r#type: Set(domain.r#type.clone()),
            date_time: Set(domain.date_time),
            user_id: Set(domain.user_id),
        }
    }
}
