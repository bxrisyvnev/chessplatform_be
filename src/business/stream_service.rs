use crate::domain::{page::Page, stream::Stream};

pub trait StreamService {
    fn get_stream_by_id(&self, stream_id: u32) -> Stream;
    fn create_stream(&mut self, stream: Stream) -> Stream;
    fn delete_stream(&mut self, stream_id: u32);
    fn get_stream_page(&self, page: u32, size: u32) -> Page<Stream>;
}
