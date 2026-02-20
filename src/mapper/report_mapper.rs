use crate::domain::report::Report;
use crate::persistence::r#impl::entity::report_entity;

use sea_orm::ActiveValue::Set;

pub struct ReportMapper;

impl ReportMapper {
    pub fn entity_to_domain(entity: report_entity::Model) -> Report {
        Report::new(
            entity.id,
            entity.description.unwrap_or_default(),
            entity.r#type.unwrap_or_default(),
            entity
                .date_time
                .map(|dt| dt.date())
                .unwrap_or_else(|| chrono::Utc::now().date_naive()),
            entity.user_id,
        )
    }

    pub fn object_to_active(domain: &Report) -> report_entity::ActiveModel {
        report_entity::ActiveModel {
            id: Set(domain.id),

            description: Set(Some(domain.description.clone())),
            r#type: Set(Some(domain.r#type.clone())),

            date_time: Set(Some(domain.date_time.and_hms_opt(0, 0, 0).unwrap())),

            user_id: Set(domain.user_id),
        }
    }
}
