use crate::domain::{official_news::OfficialNews, page::Page, pageable::Pageable};

pub trait OfficialNewsRepository {
    fn save(&mut self, official_news: Vec<OfficialNews>) -> Vec<OfficialNews>;
    fn get_official_news_page(&self, pageable: Pageable) -> Page<OfficialNews>;
}
