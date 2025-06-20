package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.domain.Report;
import org.example.chessplatformbe.persistence.ReportRepository;
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
class ReportServiceImplTest {

    @Mock
    private ReportRepository repository;

    @InjectMocks
    private ReportServiceImpl service;

    @Test
    void getReportPage_withContent_shouldReturnPagedResponse() {
        Report r1 = new Report(); r1.setId(1);
        Report r2 = new Report(); r2.setId(2);
        List<Report> content = List.of(r1, r2);

        Page<Report> page =
                new PageImpl<>(content,
                        PageRequest.of(0, 2, Sort.by("id").descending()),
                        10);

        when(repository.getReportPage(any(Pageable.class))).thenReturn(page);

        Page<Report> result = service.getReportPage(0, 2);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(5);
    }

    @Test
    void getReportPage_withEmptyContent_shouldReturnEmptyPage() {
        Page<Report> empty =
                new PageImpl<>(Collections.emptyList(),
                        PageRequest.of(1, 5),
                        0);

        when(repository.getReportPage(any(Pageable.class))).thenReturn(empty);

        Page<Report> result = service.getReportPage(1, 5);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getNumber()).isEqualTo(1);
        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getTotalPages()).isZero();
    }

    @Test
    void createReport_success() {
        Report report = new Report(); report.setId(1);

        when(repository.save(report)).thenReturn(report);

        Report result = service.createReport(report);

        assertThat(result).isEqualTo(report);
        verify(repository).save(report);
    }

    @Test
    void deleteReport_success() {
        Report report = new Report(); report.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(report));

        service.deleteReport(1);

        verify(repository).delete(report);
    }

    @Test
    void deleteReport_notFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> service.deleteReport(1));

        assertThat(ex)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Article not found with ID: 1");
    }
}
