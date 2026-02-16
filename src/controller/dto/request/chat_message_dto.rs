pub struct ChatMessageDTO {
    content: String,
    sender: String,
    stream_id: u32,
}

impl ChatMessageDTO {
    pub fn new(content: String, sender: String, stream_id: u32) -> Self {
        Self {
            content,
            sender,
            stream_id,
        }
    }
}
