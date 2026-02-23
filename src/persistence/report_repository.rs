use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use crate::domain::report::Report;

pub struct ReportRepository {
    pub report_db: Vec<Report>,
}

impl ReportRepository {
    pub fn save(&mut self, report: Report) -> Report {
        self.report_db.push(report.clone());
        report
    }

    pub fn delete(&mut self, report: Report) {
        self.report_db.retain(|rep| rep.id != report.id);
    }

    pub fn find_by_id(&self, id: u32) -> Option<Report> {
        self.report_db.iter().find(|rep| rep.id == id).cloned()
    }

    pub fn get_report_page(&self, pageable: Pageable) -> Option<Page<Report>> {
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return None;
        }

        let start = (page - 1) * size;
        if start >= self.report_db.len() {
            return None;
        }

        let end = (start + size).min(self.report_db.len());
        let items: Vec<Report> = self.report_db[start..end].to_vec();

        Some(Page::new(items, (end - start) as u32, &pageable))
    }
}
