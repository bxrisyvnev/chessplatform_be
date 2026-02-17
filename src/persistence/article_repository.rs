use crate::domain::{article::Article, page::Page, pageable::Pageable};

pub trait ArticleReporitory {
    fn find_by_id(&self, id: u32) -> Option<Article>;
    fn save(&mut self, article: Article) -> Article;
    fn delete(&mut self, article: Article);
    fn get_article_page(&self, pageable: Pageable) -> Page<Article>;
    fn update(&mut self, article: Article, update_id: u32) -> Article;
    fn find_by_title(&self, title: &str) -> Article;
    fn get_articles_by_title_page(&self, pageable: Pageable) -> Page<Article>;
}
