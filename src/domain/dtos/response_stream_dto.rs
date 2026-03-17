use chrono::NaiveDate;

pub struct StreamResponseDTO {
    id: u32,
    name: String,
    creation_date_time: NaiveDate,
    stream_url: String,
    streamer_id: u32,
}

impl StreamResponseDTO {
    pub fn new(
        id: u32,
        name: String,
        creation_date_time: NaiveDate,
        stream_url: String,
        streamer_id: u32,
    ) -> Self {
        Self {
            id,
            name,
            creation_date_time,
            stream_url,
            streamer_id,
        }
    }
}
