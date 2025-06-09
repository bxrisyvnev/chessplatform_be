package org.example.chessplatformbe.business.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

class StreamViewerServiceImplTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private StreamViewerServiceImpl streamViewerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewerJoined_FirstTime() {
        String streamId = "stream1";

        streamViewerService.viewerJoined(streamId);

        verify(messagingTemplate).convertAndSend("/topic/stream/" + streamId + "/viewers", 1);
    }

    @Test
    void testViewerJoined_MultipleJoins() {
        String streamId = "stream2";

        streamViewerService.viewerJoined(streamId); // count = 1
        streamViewerService.viewerJoined(streamId); // count = 2

        verify(messagingTemplate, times(1)).convertAndSend("/topic/stream/stream2/viewers", 1);
        verify(messagingTemplate, times(1)).convertAndSend("/topic/stream/stream2/viewers", 2);
    }

    @Test
    void testViewerLeft_ValidDecrement() {
        String streamId = "stream3";

        streamViewerService.viewerJoined(streamId); // count = 1
        streamViewerService.viewerLeft(streamId);   // count = 0

        verify(messagingTemplate).convertAndSend("/topic/stream/" + streamId + "/viewers", 0);
    }

    @Test
    void testViewerLeft_BelowZeroShouldNotBroadcastNegative() {
        String streamId = "stream4";

        streamViewerService.viewerJoined(streamId); // count = 1
        streamViewerService.viewerLeft(streamId);   // count = 0
        streamViewerService.viewerLeft(streamId);   // count = -1 (but still broadcast 0)

        verify(messagingTemplate, times(1)).convertAndSend("/topic/stream/stream4/viewers", 0);
        verify(messagingTemplate, never()).convertAndSend("/topic/stream/stream4/viewers", -1);
    }

    @Test
    void testBroadcastViewerCount_SendsToCorrectTopic() {
        String streamId = "stream5";

        streamViewerService.broadcastViewerCount(streamId, 42);

        verify(messagingTemplate).convertAndSend("/topic/stream/stream5/viewers", 42);
    }
}
