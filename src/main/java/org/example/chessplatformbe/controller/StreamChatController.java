package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.StreamViewerServiceImpl;
import org.example.chessplatformbe.controller.dto.request.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StreamChatController {

    private final StreamViewerServiceImpl viewerService;

    public StreamChatController(StreamViewerServiceImpl viewerService) {
        this.viewerService = viewerService;
    }

    @MessageMapping("/chat/{streamId}")
    @SendTo("/topic/stream/{streamId}")
    public ChatMessageDTO sendMessage(@DestinationVariable Integer streamId, ChatMessageDTO message) {
        return message;
    }

    @MessageMapping("/stream/{streamId}/viewer/join")
    public void handleJoin(@DestinationVariable String streamId) {
        viewerService.viewerJoined(streamId);
    }

    @MessageMapping("/stream/{streamId}/viewer/leave")
    public void handleLeave(@DestinationVariable String streamId) {
        viewerService.viewerLeft(streamId);
    }
}
