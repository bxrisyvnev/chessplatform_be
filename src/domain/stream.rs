use chrono::NaiveDate;

#[derive(Debug)]
pub struct Stream {
    id: i32,
    name: String,
    creation_date_time: NaiveDate,
    stream_url: String,
    streamer_id: i32,
}

impl Stream {
    pub fn new(
        id: i32,
        name: String,
        creation_date_time: NaiveDate,
        stream_url: String,
        streamer_id: i32,
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
