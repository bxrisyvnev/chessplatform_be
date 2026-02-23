use chrono::NaiveDate;

use crate::domain::pageable::Pageable;
use crate::domain::report::Report;
use crate::persistence::report_repository::ReportRepository;

mod business;
mod controller;
mod domain;
mod mapper;
mod persistence;

fn main() {
    let mut repo = ReportRepository {
        report_db: Vec::new(),
    };

    let rep1 = Report::new(
        1,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep2 = Report::new(
        2,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep3 = Report::new(
        3,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep4 = Report::new(
        4,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep5 = Report::new(
        5,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep6 = Report::new(
        6,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep7 = Report::new(
        7,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep8 = Report::new(
        8,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep9 = Report::new(
        9,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep10 = Report::new(
        10,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );
    let rep11 = Report::new(
        11,
        "first".to_string(),
        "first".to_string(),
        NaiveDate::parse_from_str("2026-02-23", "%Y-%m-%d").unwrap(),
        1,
    );

    repo.save(rep1);
    repo.save(rep2);
    repo.save(rep3);
    repo.save(rep4);
    repo.save(rep5);
    repo.save(rep6);
    repo.save(rep7);
    repo.save(rep8);
    repo.save(rep9);
    repo.save(rep10);
    repo.save(rep11);

    let pg1 = Pageable { size: 3, page: 2 };

    println!("{:?}", repo.get_report_page(pg1));
}
