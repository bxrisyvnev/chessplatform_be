use chrono::NaiveDate;

pub struct OfficialNews {
    id: u32,
    title: String,
    link: String,
    published_date: NaiveDate,
    description: String,
    author: String,
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
