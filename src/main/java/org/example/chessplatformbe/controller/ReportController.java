package org.example.chessplatformbe.controller;

import jakarta.validation.Valid;
import org.example.chessplatformbe.business.impl.ReportServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateReportDTO;
import org.example.chessplatformbe.controller.dto.response.ReportResponceDTO;
import org.example.chessplatformbe.domain.Report;
import org.example.chessplatformbe.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportServiceImpl reportService;

    @Autowired
    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Page<Report> reportPage = reportService.getReportPage(page, size);

        List<ReportResponceDTO> reports = reportPage.getContent().stream()
                .map(ReportMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("reports", reports);
        response.put("currentPage", reportPage.getNumber());
        response.put("totalItems", reportPage.getTotalElements());
        response.put("totalPages", reportPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReportResponceDTO> createReport(@Valid @RequestBody CreateReportDTO dto) {
        return ResponseEntity.ok(ReportMapper.objectToResponse(reportService.createReport(ReportMapper.requestToObject(dto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") Integer identification) {
        reportService.deleteReport(identification);
        return ResponseEntity.ok().build();
    }
}
