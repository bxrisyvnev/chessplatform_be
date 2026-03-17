pub struct CreateOfficialNewsDTO {
    update_id: u32,
    title: String,
    link: String,
    published_date: String,
    description: String,
    author: String,
}

impl CreateOfficialNewsDTO {
    pub fn new(
        update_id: u32,
        title: String,
        link: String,
        published_date: String,
        description: String,
        author: String,
    ) -> Self {
        Self {
            update_id,
            title,
            link,
            published_date,
            description,
            author,
        }
    }
}
