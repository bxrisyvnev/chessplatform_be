use crate::domain::article::Article;
use crate::domain::page::Page;
use crate::domain::pageable::Pageable;

pub struct ArticleRepositoryTest {
    pub article_db: Vec<Article>,
}

impl ArticleRepositoryTest {
    pub fn save(&mut self, article: Article) -> Article {
        self.article_db.push(article.clone());
        article
    }

    pub fn delete(&mut self, article: Article) {
        self.article_db.retain(|art| art.id != article.id);
    }

    pub fn find_by_id(&self, id: u32) -> Option<Article> {
        self.article_db.iter().find(|art| art.id == id).cloned()
    }

    pub fn get_article_page(&self, pageable: Pageable) -> Option<Page<Article>> {
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return None;
        }

        let start = (page - 1) * size;
        if start >= self.article_db.len() {
            return None;
        }

        let end = (start + size).min(self.article_db.len());
        let items: Vec<Article> = self.article_db[start..end].to_vec();

        Some(Page::new(items, (end - start) as u32, &pageable))
    }

    pub fn update(&mut self, article: Article, update_id: u32) {
        self.article_db[(update_id - 1) as usize] = article
    }

    pub fn find_by_title(&self, title: &str) -> Option<Article> {
        self.article_db
            .iter()
            .find(|art| art.article_title == title)
            .cloned()
    }

    pub fn grt_articles_by_title_page() {
        todo!()
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::domain::article::Article;
    use crate::domain::pageable::Pageable;

    fn make_repo() -> ArticleRepositoryTest {
        ArticleRepositoryTest { article_db: vec![] }
    }

    fn make_article(id: u32, title: &str) -> Article {
        Article {
            id,
            article_title: title.to_string(),
            ..Default::default()
        }
    }

    #[test]
    fn save_should_store_article_and_return_it() {
        let mut repo = make_repo();
        let article = make_article(1, "Test");

        let saved = repo.save(article.clone());

        assert_eq!(saved.id, 1);
        assert_eq!(repo.article_db.len(), 1);
        assert_eq!(repo.article_db[0].article_title, "Test");
    }

    #[test]
    fn delete_should_remove_article_by_id() {
        let mut repo = make_repo();
        let a1 = make_article(1, "A");
        let a2 = make_article(2, "B");

        repo.save(a1.clone());
        repo.save(a2.clone());

        repo.delete(a1);

        assert_eq!(repo.article_db.len(), 1);
        assert_eq!(repo.article_db[0].id, 2);
    }

    #[test]
    fn find_by_id_should_return_article_when_found() {
        let mut repo = make_repo();
        repo.save(make_article(10, "Hello"));

        let result = repo.find_by_id(10);

        assert!(result.is_some());
        assert_eq!(result.unwrap().article_title, "Hello");
    }

    #[test]
    fn find_by_id_should_return_none_when_not_found() {
        let repo = make_repo();

        let result = repo.find_by_id(999);

        assert!(result.is_none());
    }

    #[test]
    fn find_by_title_should_return_article_when_found() {
        let mut repo = make_repo();
        repo.save(make_article(1, "Rust"));
        repo.save(make_article(2, "Java"));

        let result = repo.find_by_title("Java");

        assert!(result.is_some());
        let article = result.unwrap();
        assert_eq!(article.id, 2);
    }

    #[test]
    fn find_by_title_should_return_none_when_not_found() {
        let mut repo = make_repo();
        repo.save(make_article(1, "Rust"));

        let result = repo.find_by_title("Python");

        assert!(result.is_none());
    }

    #[test]
    fn update_should_replace_article() {
        let mut repo = make_repo();
        repo.save(make_article(1, "Old"));

        let updated = make_article(1, "New");
        repo.update(updated, 1);

        assert_eq!(repo.article_db[0].article_title, "New");
    }

    #[test]
    fn get_article_page_should_return_first_page() {
        let mut repo = make_repo();
        repo.save(make_article(1, "A"));
        repo.save(make_article(2, "B"));
        repo.save(make_article(3, "C"));

        let pageable = Pageable { page: 1, size: 2 };

        let result = repo.get_article_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 2);
        assert_eq!(page.content[0].id, 1);
        assert_eq!(page.content[1].id, 2);
    }

    #[test]
    fn get_article_page_should_return_last_partial_page() {
        let mut repo = make_repo();
        repo.save(make_article(1, "A"));
        repo.save(make_article(2, "B"));
        repo.save(make_article(3, "C"));

        let pageable = Pageable { page: 2, size: 2 };

        let result = repo.get_article_page(pageable);

        assert!(result.is_some());
        let page = result.unwrap();

        assert_eq!(page.content.len(), 1);
        assert_eq!(page.content[0].id, 3);
    }

    #[test]
    fn get_article_page_should_return_none_for_invalid_page() {
        let repo = make_repo();
        let pageable = Pageable { page: 0, size: 10 };

        let result = repo.get_article_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    fn get_article_page_should_return_none_when_page_out_of_bounds() {
        let mut repo = make_repo();
        repo.save(make_article(1, "A"));

        let pageable = Pageable { page: 2, size: 10 };

        let result = repo.get_article_page(pageable);

        assert!(result.is_none());
    }

    #[test]
    #[should_panic]
    fn grt_articles_by_title_page_should_panic_until_implemented() {
        ArticleRepositoryTest::grt_articles_by_title_page();
    }
}
