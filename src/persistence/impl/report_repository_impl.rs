use sea_orm::{prelude::async_trait, DatabaseConnection, DbErr, EntityTrait, PaginatorTrait};

use crate::{
    domain::{
        page::{self, Page},
        pageable::Pageable,
        report::Report,
    },
    mapper::report_mapper::ReportMapper,
    persistence::{r#impl::entity::report_entity, report_repository::ReportRepository},
};

pub struct ReportRepositoryImpl {
    db: DatabaseConnection,
}

impl ReportRepositoryImpl {
    pub fn new(db: DatabaseConnection) -> Self {
        Self { db }
    }
}

#[async_trait::async_trait]
impl ReportRepository for ReportRepositoryImpl {
    async fn find_by_id(&self, id: u32) -> Result<Option<Report>, DbErr> {
        let entity = report_entity::Entity::find_by_id(id).one(&self.db).await?; // ✅ unwrap Result

        Ok(entity.map(ReportMapper::entity_to_domain))
    }

    async fn get_report_page(&self, pageable: Pageable) -> Result<Page<Report>, DbErr> {
        let paginator = report_entity::Entity::find().paginate(&self.db, pageable.size);

        let total = paginator.num_items().await?;

        let entities = paginator.fetch_page(pageable.page).await?; // ✅ fixed syntax

        let reports: Vec<Report> = entities
            .into_iter()
            .map(ReportMapper::entity_to_domain)
            .collect();

        let return_page = page::Page::new(reports, total, &pageable);

        Ok(return_page)
    }

    async fn save(&self, object: Report) -> Result<Report, DbErr> {
        let active_model = ReportMapper::object_to_active(&object);

        let saved = report_entity::Entity::insert(active_model)
            .exec_with_returning(&self.db)
            .await?;

        Ok(ReportMapper::entity_to_domain(saved))
    }

    async fn delete(&self, object: Report) -> Result<(), DbErr> {
        report_entity::Entity::delete_by_id(object.id)
            .exec(&self.db)
            .await?;
        Ok(())
    }
}
