use crate::domain::article::Article;
use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use mysql::Result;

pub trait ArticleRepository: Send + Sync {
    fn insert_article(&self, article: Article) -> Result<u64>;
    fn delete_article(&self, article: Article) -> Result<()>;
    fn get_article_page(&self, pageable: &Pageable) -> Result<Option<Page<Article>>>;
    fn find_article_by_id(&self, article_id: u32) -> Result<Option<Article>>;
    fn get_by_title(&self, title: &str) -> Result<Option<Article>>;
    fn update_article(&self, article: &Article, update_id: u32) -> Result<bool>;
    fn get_articles_by_title_page(
        &self,
        title_query: &str,
        pageable: &Pageable,
    ) -> Result<Option<Page<Article>>>;
}
