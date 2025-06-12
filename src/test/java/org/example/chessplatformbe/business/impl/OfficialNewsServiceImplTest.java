package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.persistence.OfficialNewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OfficialNewsServiceImplTest {

    @Mock
    private OfficialNewsRepository repository;

    @InjectMocks
    private OfficialNewsServiceImpl service;

    @BeforeEach
    void setUp() {
        // No setup required as repository is mocked and injected
    }

    @Test
    void getLatestFive_shouldReturnUpToFiveItems() throws Exception {
        // This is an integration-like test hitting the real RSS feed.
        List<OfficialNews> newsList = service.getLatestFive();
        assertThat(newsList).isNotNull().hasSizeLessThanOrEqualTo(5);
        // Each item should have title, link and publishedDate
        for (OfficialNews news : newsList) {
            assertThat(news.getTitle()).isNotBlank();
            assertThat(news.getLink()).isNotBlank();
            assertThat(news.getPublishedDate()).isNotNull();
        }
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
        given(repository.save(anyList())).willReturn(saved);

        List<OfficialNews> result = service.createOfficialNews(Arrays.asList(validDto, invalidDto));

        assertThat(result).isEqualTo(saved);

        // Verify mapping logic
        // Capture the list passed to repository
        ArgumentCaptor<List<OfficialNews>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).save(captor.capture());
        List<OfficialNews> toSave = captor.getValue();
        assertThat(toSave).hasSize(2);

        OfficialNews n1 = toSave.get(0);
        assertThat(n1.getPublishedDate()).isEqualTo(LocalDateTime.parse("2025-06-12T10:15:30"));

        OfficialNews n2 = toSave.get(1);
        assertThat(n2.getPublishedDate()).isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    void getOfficialNewsPage_withContent_shouldReturnPagedResponse() {
        OfficialNews o1 = new OfficialNews(); o1.setId(1);
        OfficialNews o2 = new OfficialNews(); o2.setId(2);
        List<OfficialNews> content = Arrays.asList(o1, o2);
        Page<OfficialNews> page = new PageImpl<>(content, PageRequest.of(0, 2, Sort.by("id").descending()), 10);
        given(repository.getOfficialNewsPage(any(Pageable.class))).willReturn(page);

        Map<String, Object> response = service.getOfficialNewsPage(0, 2);

        assertThat(response.get("officialNews")).isInstanceOf(List.class);
        assertThat((List<?>) response.get("officialNews")).hasSize(2);
        assertThat(response).containsEntry("currentPage", 0)
        .containsEntry("totalItems",10L)
        .containsEntry("totalPages", 5);
    }

    @Test
    void getOfficialNewsPage_withEmptyContent_shouldReturnEmptyList() {
        Page<OfficialNews> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(1, 5), 0);
        given(repository.getOfficialNewsPage(any(Pageable.class))).willReturn(emptyPage);

        Map<String, Object> response = service.getOfficialNewsPage(1, 5);

        assertThat(response.get("officialNews")).isInstanceOf(List.class);
        assertThat((List<?>) response.get("officialNews")).isEmpty();
        assertThat(response).containsEntry("currentPage", 1)
        .containsEntry("totalItems", 0L)
        .containsEntry("totalPages", 0);
    }
}
