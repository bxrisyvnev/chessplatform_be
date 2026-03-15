use std::sync::Mutex;

use mysql::Result;

use crate::domain::article::Article;
use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use crate::persistence::traits::ArticleRepository;

pub struct ArticleRepositoryTest {
    pub article_db: Mutex<Vec<Article>>,
    pub next_id: Mutex<u32>,
}

impl ArticleRepositoryTest {
    pub fn new() -> Self {
        Self {
            article_db: Mutex::new(vec![]),
            next_id: Mutex::new(1),
        }
    }
}

impl ArticleRepository for ArticleRepositoryTest {
    fn insert_article(&self, mut article: Article) -> Result<u64> {
        if article.id == 0 {
            let mut next_id = self.next_id.lock().unwrap();
            article.id = *next_id;
            *next_id += 1;
        }

        let id = article.id as u64;
        self.article_db.lock().unwrap().push(article);
        Ok(id)
    }

    fn delete_article(&self, article: Article) -> Result<()> {
        self.article_db
            .lock()
            .unwrap()
            .retain(|a| a.id != article.id);
        Ok(())
    }

    fn get_article_page(&self, pageable: &Pageable) -> Result<Option<Page<Article>>> {
        let data = self.article_db.lock().unwrap();

        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return Ok(None);
        }

        let start = (page - 1) * size;
        if start >= data.len() {
            return Ok(None);
        }

        let end = (start + size).min(data.len());
        let items = data[start..end].to_vec();

        Ok(Some(Page::new(items, data.len() as u32, pageable)))
    }

    fn find_article_by_id(&self, article_id: u32) -> Result<Option<Article>> {
        Ok(self
            .article_db
            .lock()
            .unwrap()
            .iter()
            .find(|art| art.id == article_id)
            .cloned())
    }

    fn get_by_title(&self, title: &str) -> Result<Option<Article>> {
        Ok(self
            .article_db
            .lock()
            .unwrap()
            .iter()
            .find(|art| art.article_title == title)
            .cloned())
    }

    fn update_article(&self, article: &Article, update_id: u32) -> Result<bool> {
        let mut data = self.article_db.lock().unwrap();

        if let Some(existing) = data.iter_mut().find(|a| a.id == update_id) {
            *existing = article.clone();
            return Ok(true);
        }

        Ok(false)
    }

    fn get_articles_by_title_page(
        &self,
        title_query: &str,
        pageable: &Pageable,
    ) -> Result<Option<Page<Article>>> {
        let size = pageable.size as usize;
        let page = pageable.page as usize;

        if page == 0 || size == 0 {
            return Ok(None);
        }

        let filtered: Vec<Article> = self
            .article_db
            .lock()
            .unwrap()
            .iter()
            .filter(|art| art.article_title.contains(title_query))
            .cloned()
            .collect();

        let start = (page - 1) * size;
        if start >= filtered.len() {
            return Ok(None);
        }

        let end = (start + size).min(filtered.len());
        let items = filtered[start..end].to_vec();

        Ok(Some(Page::new(items, filtered.len() as u32, pageable)))
    }
}
