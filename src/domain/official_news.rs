use chrono::NaiveDate;

#[derive(Debug)]
pub struct OfficialNews {
    id: i32,
    title: String,
    link: String,
    published_date: NaiveDate,
    description: String,
    author: String,
}

impl OfficialNews {
    pub fn new(
        id: i32,
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
