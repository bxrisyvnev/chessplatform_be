use crate::domain::article::Article;
use crate::domain::comment::Comment;
use crate::domain::page::Page;
use crate::domain::pageable::Pageable;
use crate::domain::user::User;
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

pub trait UserRepository: Send + Sync {
    fn insert_user(&self, user: User) -> Result<u64>;
    fn delete_user(&self, user: User) -> Result<()>;
    fn find_user_by_id(&self, user_id: u32) -> Result<Option<User>>;
    fn find_user_by_username(&self, username: &str) -> Result<Option<User>>;
    fn get_average_comments_by_user_id(&self, user_id: u32) -> Result<Option<f32>>;
}

pub trait CommentRepository: Send + Sync {
    fn insert_comment(&self, comment: Comment) -> Result<u64>;
    fn delete_comment(&self, comment: Comment) -> Result<()>;
    fn find_comment_by_id(&self, comment_id: u32) -> Result<Option<Comment>>;
    fn find_comment_by_article_id(&self, article_id: u32) -> Result<Option<Comment>>;
    fn find_comment_by_username(&self, username: &str) -> Result<Option<Comment>>;
}
