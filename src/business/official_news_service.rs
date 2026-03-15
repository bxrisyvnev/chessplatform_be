use crate::{
    domain::official_news::OfficialNews,
    persistence::test::official_news_repository::OfficialNewsRepositoryTest,
};

const NEWS_RSS: &str = "https://www.chess.com/rss/news";

pub struct OfficialNewsService {
    off_news_repo: OfficialNewsRepositoryTest,
}
