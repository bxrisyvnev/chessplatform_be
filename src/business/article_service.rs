use crate::domain::article::Article;
use crate::domain::page::Page;

pub trait ArticleService {
    fn get_article_by_id(&self, id: i32) -> Article;
    //fn get_get_article_by_page(&self, page: i32, size: i32) -> Page<Article>;
    fn create_article(&mut self, article: Article) -> Article;
    fn update_article(&mut self, article: Article, updated_id: i32) -> Article;
    fn delete_article(&mut self, article_id: i32);
    fn get_by_title(&self, title: &str) -> Article;
    //fn get_article_page_by_title(&self, title: String, page: i32, size: i32) -> Page<Article>;
}
