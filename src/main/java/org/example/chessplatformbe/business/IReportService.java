package org.example.chessplatformbe.business;

import org.example.chessplatformbe.domain.Report;
import org.springframework.data.domain.Page;

public interface IReportService {

    Page<Report> getReportPage(Integer page, Integer size);

    Report createReport(Report report);

    void deleteReport(Integer reportId);
}
