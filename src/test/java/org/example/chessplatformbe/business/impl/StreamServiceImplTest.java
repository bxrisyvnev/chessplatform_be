package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.dto.request.CreateStreamDTO;
import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.mapper.StreamMapper;
import org.example.chessplatformbe.persistence.StreamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StreamServiceImplTest {

    @Mock
    private StreamRepository streamRepository;

    @InjectMocks
    private StreamServiceImpl streamService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // GET STREAM BY ID
    @Test
    void testGetStreamById_Success() {
        Stream stream = new Stream();
        stream.setId(1);
        stream.setStreamUrl("TwitchLink");
        stream.setName("Description");
        stream.setStreamerId(3);
        when(streamRepository.findById(1)).thenReturn(Optional.of(stream));

        Stream result = streamService.getStreamById(1);

        assertEquals(stream.getId(), result.getId());
        assertEquals("TwitchLink", result.getStreamUrl());
    }

    @Test
    void testGetStreamById_NotFound() {
        when(streamRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> streamService.getStreamById(1));
        assertTrue(ex.getMessage().contains("Stream not found"));
    }

    // CREATE STREAM
    @Test
    void testCreateStream_Success() {
        CreateStreamDTO dto = new CreateStreamDTO();
        dto.setStreamUrl("Link");
        dto.setName("Desc");
        dto.setStreamerId(1);

        Stream stream = StreamMapper.requestToObject(dto);

        when(streamRepository.save(any(Stream.class))).thenReturn(stream);

        Stream result = streamService.createStream(dto);

        assertEquals("Link", result.getStreamUrl());
        assertEquals("Desc", result.getName());
    }

    // DELETE STREAM
    @Test
    void testDeleteStream_Success() {
        Stream stream = new Stream();
        stream.setId(1);
        stream.setStreamUrl("Link");
        stream.setName("Desc");
        stream.setStreamerId(1);

        when(streamRepository.findById(1)).thenReturn(Optional.of(stream));

        streamService.deleteStream(1);

        verify(streamRepository).delete(stream);
    }

    @Test
    void testDeleteStream_NotFound() {
        when(streamRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> streamService.deleteStream(1));
        assertTrue(ex.getMessage().contains("Stream not found"));
    }

    // GET STREAM PAGE
    @Test
    void testGetStreamPage_Success() {
        Stream stream = new Stream();
        stream.setId(1);
        stream.setStreamUrl("Link");
        stream.setName("Desc");
        stream.setStreamerId(1);

        Page<Stream> page = new PageImpl<>(List.of(stream));
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());

        when(streamRepository.getStreamPage(pageable)).thenReturn(page);

        Map<String, Object> result = streamService.getStreamPage(0, 5);

        List<?> streams = (List<?>) result.get("streams");

        assertEquals(1, streams.size());
        assertEquals(0, result.get("currentPage"));
        assertEquals(1L, result.get("totalItems"));
        assertEquals(1, result.get("totalPages"));
    }
}
