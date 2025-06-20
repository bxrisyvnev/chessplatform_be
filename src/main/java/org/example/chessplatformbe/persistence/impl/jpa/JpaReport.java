package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReport extends JpaRepository<ReportEntity, Integer> {
    Page<ReportEntity> findAll(Pageable pageable);
}
