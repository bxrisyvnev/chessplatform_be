package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.business.IStreamViewerService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StreamViewerServiceImpl implements IStreamViewerService {
    private final Map<String, AtomicInteger> viewerCounts = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public StreamViewerServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void viewerJoined(String streamId) {
        int count = viewerCounts.computeIfAbsent(streamId, k -> new AtomicInteger(0)).incrementAndGet();
        broadcastViewerCount(streamId, count);
    }

    @Override
    public void viewerLeft(String streamId) {
        AtomicInteger count = viewerCounts.get(streamId);
        if (count != null && count.decrementAndGet() >= 0) {
            broadcastViewerCount(streamId, count.get());
        }
    }

    @Override
    public void broadcastViewerCount(String streamId, int count) {
        messagingTemplate.convertAndSend("/topic/stream/" + streamId + "/viewers", count);
    }
}
