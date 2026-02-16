pub trait StreamViewerService {
    fn viewer_joined(&mut self, stream_id: &str);
    fn viewer_left(&mut self, stream_id: &str);
    fn broadcast_viewer_count(&self, stream_id: &str, count: u32);
}
