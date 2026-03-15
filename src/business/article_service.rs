use mysql::Result;

use crate::domain::{article::Article, page::Page, pageable::Pageable};
use crate::persistence::traits::ArticleRepository;

pub struct ArticleService<R: ArticleRepository> {
    pub art_repo: R,
}

impl<R: ArticleRepository> ArticleService<R> {
    pub fn new(art_repo: R) -> Self {
        Self { art_repo }
    }

    pub fn get_article_by_id(&self, id: u32) -> Result<Option<Article>> {
        self.art_repo.find_article_by_id(id)
    }

    pub fn get_article_page(&self, pageable: &Pageable) -> Result<Option<Page<Article>>> {
        self.art_repo.get_article_page(pageable)
    }

    pub fn create_article(&self, article: Article) -> Result<u64> {
        self.art_repo.insert_article(article)
    }

    pub fn update_article(&self, article: &Article, update_id: u32) -> Result<bool> {
        self.art_repo.update_article(article, update_id)
    }

    pub fn delete_article(&self, id: u32) -> Result<bool> {
        if let Some(article) = self.art_repo.find_article_by_id(id)? {
            self.art_repo.delete_article(article)?;
            Ok(true)
        } else {
            Ok(false)
        }
    }

    pub fn get_by_title(&self, title: &str) -> Result<Option<Article>> {
        self.art_repo.get_by_title(title)
    }

    pub fn get_article_page_by_title(
        &self,
        title: &str,
        pageable: &Pageable,
    ) -> Result<Option<Page<Article>>> {
        self.art_repo.get_articles_by_title_page(title, pageable)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::article::Article;
    use crate::domain::pageable::Pageable;
    use crate::persistence::test::article_repository::ArticleRepositoryTest;

    fn make_article(id: u32, title: &str) -> Article {
        Article {
            id,
            article_title: title.to_string(),
            ..Default::default()
        }
    }

    fn make_service() -> ArticleService<ArticleRepositoryTest> {
        ArticleService::new(ArticleRepositoryTest::new())
    }

    #[test]
    fn get_article_by_id_returns_article_when_found() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Rust"))
            .unwrap();

        let result = service.get_article_by_id(1).unwrap();

        assert!(result.is_some());
        assert_eq!(result.unwrap().article_title, "Rust");
    }

    #[test]
    fn get_article_by_id_returns_none_when_not_found() {
        let service = make_service();

        let result = service.get_article_by_id(999).unwrap();

        assert!(result.is_none());
    }

    #[test]
    fn get_article_page_returns_page() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "A"))
            .unwrap();
        service
            .art_repo
            .insert_article(make_article(2, "B"))
            .unwrap();
        service
            .art_repo
            .insert_article(make_article(3, "C"))
            .unwrap();

        let result = service
            .get_article_page(&Pageable { page: 1, size: 2 })
            .unwrap();

        assert!(result.is_some());
        let page = result.unwrap();
        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_article_page_returns_none_for_invalid_page() {
        let service = make_service();

        let result = service
            .get_article_page(&Pageable { page: 0, size: 10 })
            .unwrap();

        assert!(result.is_none());
    }

    #[test]
    fn create_article_returns_inserted_id() {
        let service = make_service();

        let id = service.create_article(make_article(1, "Rust")).unwrap();

        assert_eq!(id, 1);
    }

    #[test]
    fn update_article_returns_true_when_found() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Old"))
            .unwrap();

        let updated = make_article(1, "New");
        let result = service.update_article(&updated, 1).unwrap();

        assert!(result);
        let article = service.get_article_by_id(1).unwrap().unwrap();
        assert_eq!(article.article_title, "New");
    }

    #[test]
    fn update_article_returns_false_when_missing() {
        let service = make_service();

        let updated = make_article(1, "New");
        let result = service.update_article(&updated, 999).unwrap();

        assert!(!result);
    }

    #[test]
    fn delete_article_returns_true_when_found() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Delete"))
            .unwrap();

        let result = service.delete_article(1).unwrap();

        assert!(result);
        assert!(service.get_article_by_id(1).unwrap().is_none());
    }

    #[test]
    fn delete_article_returns_false_when_missing() {
        let service = make_service();

        let result = service.delete_article(999).unwrap();

        assert!(!result);
    }

    #[test]
    fn get_by_title_returns_article_when_found() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Rust"))
            .unwrap();
        service
            .art_repo
            .insert_article(make_article(2, "Java"))
            .unwrap();

        let result = service.get_by_title("Java").unwrap();

        assert!(result.is_some());
        assert_eq!(result.unwrap().id, 2);
    }

    #[test]
    fn get_by_title_returns_none_when_missing() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Rust"))
            .unwrap();

        let result = service.get_by_title("Python").unwrap();

        assert!(result.is_none());
    }

    #[test]
    fn get_article_page_by_title_returns_filtered_page() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Rust basics"))
            .unwrap();
        service
            .art_repo
            .insert_article(make_article(2, "Advanced Rust"))
            .unwrap();
        service
            .art_repo
            .insert_article(make_article(3, "Java basics"))
            .unwrap();

        let result = service
            .get_article_page_by_title("Rust", &Pageable { page: 1, size: 10 })
            .unwrap();

        assert!(result.is_some());
        let page = result.unwrap();
        assert_eq!(page.content.len(), 2);
        assert!(page
            .content
            .iter()
            .all(|a| a.article_title.contains("Rust")));
    }

    #[test]
    fn get_article_page_by_title_returns_none_when_no_match() {
        let service = make_service();
        service
            .art_repo
            .insert_article(make_article(1, "Rust basics"))
            .unwrap();

        let result = service
            .get_article_page_by_title("Python", &Pageable { page: 1, size: 10 })
            .unwrap();

        assert!(result.is_none());
    }
}
