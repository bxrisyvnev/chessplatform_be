pub struct OfficialNewsResponseDTO {
    id: u32,
    title: String,
    link: String,
    published_date: String,
    description: String,
    author: String,
}

impl OfficialNewsResponseDTO {
    pub fn new(
        id: u32,
        title: String,
        link: String,
        published_date: String,
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
