use chrono::NaiveDate;

#[derive(Debug, Clone, Default)]
pub struct OfficialNews {
    pub id: u32,
    pub title: String,
    pub link: String,
    pub published_date: NaiveDate,
    pub description: String,
    pub author: String,
}

impl OfficialNews {
    pub fn new(
        id: u32,
        title: String,
        link: String,
        published_date: NaiveDate,
        description: String,
        author: String,
    ) -> Self {
        Self {
            id,
            title,
            link,
            published_date,
            description,
            author,
        }
    }
}
