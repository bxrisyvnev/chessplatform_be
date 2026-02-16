use crate::domain::{official_news::OfficialNews, page::Page};

pub trait OfficialNewsService {
    fn get_latest_five(&self) -> Vec<OfficialNews>;
    //fn create_offical_news(&mut self, dtos: Vec<OfficialNewsDTO>) -> Vec<OfficialNews>;
    fn get_official_news_page(&self, size: u32, page: u32) -> Page<OfficialNews>;
}
