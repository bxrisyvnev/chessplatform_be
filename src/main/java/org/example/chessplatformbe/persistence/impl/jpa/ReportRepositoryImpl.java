package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.Report;
import org.example.chessplatformbe.mapper.ReportMapper;
import org.example.chessplatformbe.persistence.ReportRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ReportEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final JpaReport jpaReport;
    private final JpaUser jpaUser;

    @Autowired
    public ReportRepositoryImpl(JpaReport jpaReport, JpaUser jpaUser) {
        this.jpaReport = jpaReport;
        this.jpaUser = jpaUser;
    }

    @Override
    public Optional<Report> findById(Integer id) {
        Optional<ReportEntity> entityOptional = this.jpaReport.findById(id);

        if (entityOptional.isEmpty()) {
            throw  new IllegalArgumentException(String.valueOf(id));
        }

        Report report = ReportMapper.entityToObject(entityOptional.get());
        return Optional.of(report);
    }

    @Override
    public Page<Report> getReportPage(Pageable pageable) {
        Page<ReportEntity> entityPage = jpaReport.findAll(pageable);

        List<Report> reports = entityPage.getContent()
                .stream()
                .map(ReportMapper::entityToObject)
                .toList();

        return new PageImpl<>(reports, pageable, entityPage.getTotalElements());
    }

    @Override
    public Report save(Report report) {
        UserEntity userEntity = jpaUser.findById(report.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + report.getUserId() + " not found"));

        ReportEntity reportEntity = ReportMapper.objectToEntity(report, userEntity);
        ReportEntity saved = jpaReport.save(reportEntity);
        return ReportMapper.entityToObject(saved);
    }

    @Override
    public void delete(Report report) {
        jpaReport.deleteById(report.getId());
    }
}
