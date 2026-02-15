use crate::business::article_service::ArticleService;
use crate::domain::article::Article;
use crate::domain::page::Page;

// Vec should be replaced by ArticleRepository when its implimented
const ARTICLE_REPOSITORY: Vec<Article> = Vec::new();

pub struct ArticleServiceImpl;

impl ArticleService for ArticleServiceImpl {
    fn get_article_by_id(&self, id: i32) -> Article {
        let vec = Vec::new();
        let ret_art = Article::new(
            1,
            "ssd".to_string(),
            "adf".to_string(),
            2,
            "wer".to_string(),
            "were".to_string(),
            vec,
        );
        ret_art
    }

    fn create_article(&mut self, article: Article) -> Article {
        article
    }

    fn update_article(&mut self, article: Article, updated_id: i32) -> Article {
        article
    }

    fn delete_article(&mut self, article_id: i32) {}

    fn get_by_title(&self, title: &str) -> Article {
        let vec = Vec::new();
        let art = Article::new(
            1,
            "ssd".to_string(),
            "adf".to_string(),
            2,
            "wer".to_string(),
            "were".to_string(),
            vec,
        );
        art
    }
}
