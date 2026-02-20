mod business;
mod controller;
mod domain;
mod mapper;
mod persistence;

use sea_orm::Database;

use crate::persistence::report_repository::ReportRepository;

use tokio::runtime::Runtime;

fn main() {
    // 1. create a runtime
    let runtime = Runtime::new().expect("Failed to create Tokio runtime");

    // 2. run async code inside it
    runtime.block_on(async {
        // Connect to the database

        let db = Database::connect("mysql://chess:chess@localhost:3306/chess_platform")
            .await
            .expect("DB connection failed");
        let report_repo =
            persistence::r#impl::report_repository_impl::ReportRepositoryImpl::new(db);

        // find by id
        match report_repo.find_by_id(1).await {
            Ok(Some(report)) => println!("Found: {:?}", report),
            Ok(None) => println!("Not found"),
            Err(e) => eprintln!("Error: {:?}", e),
        }

        let page: u64 = 1;
        let size: u64 = 10;
        // get paginated page
        let pageable = domain::pageable::Pageable { page, size };
        match report_repo.get_report_page(pageable).await {
            Ok(page) => println!("Total items: {}", size),
            Err(e) => eprintln!("Error: {:?}", e),
        }
    });
}
