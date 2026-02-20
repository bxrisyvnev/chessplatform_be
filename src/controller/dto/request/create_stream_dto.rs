use chrono::NaiveDate;

pub struct CreateStreamDTO {
    update_id: u32,
    name: String,
    creation_date_time: NaiveDate,
    stream_url: String,
    streamer_id: u32,
}

impl CreateStreamDTO {
    pub fn new(
        update_id: u32,
        name: String,
        creation_date_time: NaiveDate,
        stream_url: String,
        streamer_id: u32,
    ) -> Self {
        Self {
            update_id,
            name,
            creation_date_time,
            stream_url,
            streamer_id,
        }
    }
}
