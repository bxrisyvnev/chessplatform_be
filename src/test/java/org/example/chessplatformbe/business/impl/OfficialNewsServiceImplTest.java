package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.persistence.OfficialNewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfficialNewsServiceImplTest {

    @Mock
    private OfficialNewsRepository repository;

    @InjectMocks
    private OfficialNewsServiceImpl service;

    @Test
    void getLatestFive_shouldReturnUpToFiveItems() throws Exception {
        // Integration-like: hits real RSS feed
        List<OfficialNews> newsList = service.getLatestFive();

        assertThat(newsList)
                .isNotNull()
                .hasSizeLessThanOrEqualTo(5);

        newsList.forEach(n -> {
            assertThat(n.getTitle()).isNotBlank();
            assertThat(n.getLink()).isNotBlank();
            assertThat(n.getPublishedDate()).isNotNull();
        });
    }

    @Test
    void createOfficialNews_withValidAndInvalidDates_shouldSaveCorrectly() {
        CreateOfficialNewsDTO validDto = new CreateOfficialNewsDTO();
        validDto.setTitle("Title 1");
        validDto.setLink("http://example.com/1");
        validDto.setDescription("Desc1");
        validDto.setAuthor("Author1");
        validDto.setPublishedDate("2025-06-12T10:15:30");

        CreateOfficialNewsDTO invalidDto = new CreateOfficialNewsDTO();
        invalidDto.setTitle("Title 2");
        invalidDto.setLink("http://example.com/2");
        invalidDto.setDescription("Desc2");
        invalidDto.setAuthor("Author2");
        invalidDto.setPublishedDate("not-a-date");

        List<OfficialNews> saved = Arrays.asList(new OfficialNews(), new OfficialNews());
        when(repository.save(any(List.class))).thenReturn(saved);

        List<OfficialNews> result =
                service.createOfficialNews(Arrays.asList(validDto, invalidDto));

        assertThat(result).isEqualTo(saved);

        ArgumentCaptor<List<OfficialNews>> captor =
                ArgumentCaptor.forClass(List.class);
        verify(repository).save(captor.capture());

        List<OfficialNews> toSave = captor.getValue();
        assertThat(toSave).hasSize(2);

        OfficialNews n1 = toSave.get(0);
        assertThat(n1.getPublishedDate())
                .isEqualTo(LocalDateTime.parse("2025-06-12T10:15:30"));

        OfficialNews n2 = toSave.get(1);
        assertThat(n2.getPublishedDate())
                .isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    void getOfficialNewsPage_withContent_shouldReturnPagedResponse() {
        OfficialNews o1 = new OfficialNews(); o1.setId(1);
        OfficialNews o2 = new OfficialNews(); o2.setId(2);
        List<OfficialNews> content = List.of(o1, o2);

        Page<OfficialNews> page =
                new PageImpl<>(content,
                        PageRequest.of(0, 2, Sort.by("id").descending()),
                        10);

        when(repository.getOfficialNewsPage(any(Pageable.class)))
                .thenReturn(page);

        Page<OfficialNews> result = service.getOfficialNewsPage(0, 2);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(5);
    }

    @Test
    void getOfficialNewsPage_withEmptyContent_shouldReturnEmptyPage() {
        Page<OfficialNews> empty =
                new PageImpl<>(Collections.emptyList(),
                        PageRequest.of(1, 5),
                        0);

        when(repository.getOfficialNewsPage(any(Pageable.class)))
                .thenReturn(empty);

        Page<OfficialNews> result = service.getOfficialNewsPage(1, 5);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getNumber()).isEqualTo(1);
        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getTotalPages()).isZero();
    }
}
