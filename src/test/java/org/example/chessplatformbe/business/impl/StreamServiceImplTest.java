package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.persistence.StreamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreamServiceImplTest {

    @Mock
    private StreamRepository repository;

    @InjectMocks
    private StreamServiceImpl service;

    /* ---------- get by id ---------- */
    @Test
    void getStreamById_success() {
        Stream s = new Stream(); s.setId(1); s.setStreamUrl("url"); s.setName("name"); s.setStreamerId(3);
        when(repository.findById(1)).thenReturn(Optional.of(s));

        Stream result = service.getStreamById(1);

        assertThat(result).isEqualTo(s);
    }

    @Test
    void getStreamById_notFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> service.getStreamById(1));

        assertThat(ex)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Stream not found with ID: 1");
    }

    /* ---------- create ---------- */
    @Test
    void createStream_success() {
        Stream s = new Stream(); s.setStreamUrl("url"); s.setName("name"); s.setStreamerId(2);
        when(repository.save(s)).thenReturn(s);

        Stream result = service.createStream(s);

        assertThat(result).isEqualTo(s);
        verify(repository).save(s);
    }

    /* ---------- delete ---------- */
    @Test
    void deleteStream_success() {
        Stream s = new Stream(); s.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(s));

        service.deleteStream(1);

        verify(repository).delete(s);
    }

    @Test
    void deleteStream_notFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> service.deleteStream(1));

        assertThat(ex)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Stream not found with ID: 1");
    }

    /* ---------- paging ---------- */
    @Test
    void getStreamPage_withContent() {
        Stream s1 = new Stream(); s1.setId(1);
        Stream s2 = new Stream(); s2.setId(2);
        Page<Stream> page =
                new PageImpl<>(List.of(s1, s2),
                        PageRequest.of(0, 2, Sort.by("id").descending()),
                        6);

        when(repository.getStreamPage(any(Pageable.class))).thenReturn(page);

        Page<Stream> result = service.getStreamPage(0, 2);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalElements()).isEqualTo(6);
        assertThat(result.getTotalPages()).isEqualTo(3);
    }

    @Test
    void getStreamPage_empty() {
        Page<Stream> empty =
                new PageImpl<>(Collections.emptyList(),
                        PageRequest.of(1, 5),
                        0);

        when(repository.getStreamPage(any(Pageable.class))).thenReturn(empty);

        Page<Stream> result = service.getStreamPage(1, 5);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getNumber()).isEqualTo(1);
        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getTotalPages()).isZero();
    }
}
