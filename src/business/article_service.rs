use crate::{
    domain::{article::Article, page::Page, pageable::Pageable},
    persistence::{article_repository::ArticleRepositoryTest, user_repository::UserRepositoryTest},
};

pub struct ArticleService {
    pub art_repo: ArticleRepositoryTest,
    pub usr_repo: UserRepositoryTest,
}

impl ArticleService {
    pub fn get_article_by_id(&self, id: u32) -> Option<Article> {
        self.art_repo.find_by_id(id)
    }

    pub fn get_article_page(&self, pageable: Pageable) -> Option<Page<Article>> {
        self.art_repo.get_article_page(pageable)
    }

    pub fn create_article(&mut self, article: Article) -> Article {
        self.art_repo.save(article)
    }

    pub fn update_article(&mut self, article: Article, update_id: u32) {
        self.art_repo.update(article, update_id)
    }

    pub fn delete_article(&mut self, id: u32) {
        if let Some(article) = self.art_repo.find_by_id(id) {
            self.art_repo.delete(article);
        }
    }

    pub fn get_by_title(&self, title: &str) -> Option<Article> {
        self.art_repo.find_by_title(title)
    }

    pub fn get_article_page_by_title() {
        todo!()
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::{article::Article, pageable::Pageable};
    use crate::persistence::{
        article_repository::ArticleRepositoryTest, user_repository::UserRepositoryTest,
    };

    fn make_service() -> ArticleService {
        ArticleService {
            art_repo: ArticleRepositoryTest { article_db: vec![] },
            usr_repo: UserRepositoryTest {
                admin_db: vec![],
                pro_db: vec![],
                spec_db: vec![],
            },
        }
    }

    fn make_article(id: u32, title: &str) -> Article {
        Article {
            id,
            article_title: title.to_string(),
            ..Default::default()
        }
    }

    #[test]
    fn create_article_should_save_and_return_article() {
        let mut service = make_service();
        let article = make_article(1, "Rust");

        let saved = service.create_article(article.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(service.art_repo.article_db.len(), 1);
        assert_eq!(service.art_repo.article_db[0].article_title, "Rust");
    }

    #[test]
    fn get_article_by_id_should_return_article_when_found() {
        let mut service = make_service();
        service.create_article(make_article(1, "Test"));

        let result = service.get_article_by_id(1);

        assert!(result.is_some());
        assert_eq!(result.unwrap().article_title, "Test");
    }

    #[test]
    fn get_article_by_id_should_return_none_when_not_found() {
        let service = make_service();

        let result = service.get_article_by_id(999);

        assert!(result.is_none());
    }

    #[test]
    fn delete_article_should_remove_article() {
        let mut service = make_service();
        service.create_article(make_article(1, "A"));
        service.create_article(make_article(2, "B"));

        service.delete_article(1);

        assert_eq!(service.art_repo.article_db.len(), 1);
        assert_eq!(service.art_repo.article_db[0].id, 2);
    }

    #[test]
    fn delete_article_should_do_nothing_if_article_not_found() {
        let mut service = make_service();
        service.create_article(make_article(1, "A"));

        service.delete_article(999);

        assert_eq!(service.art_repo.article_db.len(), 1);
    }

    #[test]
    fn update_article_should_modify_existing_article() {
        let mut service = make_service();
        service.create_article(make_article(1, "Old"));

        let updated = make_article(1, "New");
        service.update_article(updated, 1);

        assert_eq!(service.art_repo.article_db[0].article_title, "New");
    }

    #[test]
    fn get_by_title_should_return_article_when_found() {
        let mut service = make_service();
        service.create_article(make_article(1, "Rust"));
        service.create_article(make_article(2, "Java"));

        let result = service.get_by_title("Java");

        assert!(result.is_some());
        assert_eq!(result.unwrap().id, 2);
    }

    #[test]
    fn get_by_title_should_return_none_when_not_found() {
        let mut service = make_service();
        service.create_article(make_article(1, "Rust"));

        let result = service.get_by_title("Python");

        assert!(result.is_none());
    }

    #[test]
    fn get_article_page_should_return_first_page() {
        let mut service = make_service();
        service.create_article(make_article(1, "A"));
        service.create_article(make_article(2, "B"));
        service.create_article(make_article(3, "C"));

        let pageable = Pageable { page: 1, size: 2 };

        let result = service.get_article_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_article_page_should_return_none_for_invalid_page() {
        let service = make_service();
        let pageable = Pageable { page: 0, size: 10 };

        let result = service.get_article_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    #[should_panic]
    fn get_article_page_by_title_should_panic_until_implemented() {
        ArticleService::get_article_page_by_title();
    }
}
