package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.dto.request.CreateReportDTO;
import org.example.chessplatformbe.controller.dto.response.ReportResponceDTO;
import org.example.chessplatformbe.domain.Report;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ReportEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {
    private ReportMapper() {}

    public static Report entityToObject(ReportEntity reportEntity) {
        Report report = new Report();
        report.setId(reportEntity.getId());
        report.setDescription(reportEntity.getDescription());
        report.setUserId(reportEntity.getUser().getId());
        report.setType(reportEntity.getType());
        report.setDateTime(reportEntity.getDateTime());

        return report;
    }

    public static ReportEntity objectToEntity(Report report, UserEntity user) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setDescription(report.getDescription());
        reportEntity.setType(report.getType());
        reportEntity.setUser(user);
        reportEntity.setDateTime(report.getDateTime());

        return reportEntity;
    }

    public static Report requestToObject(CreateReportDTO dto) {
        Report report = new Report();
        report.setId(dto.getUpdateId()); // Used when updating
        report.setDescription(dto.getDescription());
        report.setUserId(dto.getUserId());
        report.setDateTime(dto.getDateTime());
        report.setType(dto.getType());

        return report;
    }

    public static ReportResponceDTO objectToResponse(Report report) {
        ReportResponceDTO dto = new ReportResponceDTO();
        dto.setId(report.getId());
        dto.setDescription(report.getDescription());
        dto.setUserId(report.getUserId());
        dto.setDateTime(report.getDateTime());
        dto.setType(report.getType());

        return dto;
    }
}
