package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReportRepository {
    Optional<Report> findById(Integer id);

    Page<Report> getReportPage(Pageable pageable);

    Report save(Report report);

    void delete(Report report);
}
