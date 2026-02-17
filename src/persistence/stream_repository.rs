use crate::domain::{page::Page, pageable::Pageable, stream::Stream};

pub trait StreamRepository {
    fn find_by_id(&self, id: u32) -> Option<Stream>;
    fn save(&mut self, stream: Stream) -> Stream;
    fn delete(&mut self, stream: Stream);
    fn get_stream_page(&self, pageable: Pageable) -> Page<Stream>;
}
