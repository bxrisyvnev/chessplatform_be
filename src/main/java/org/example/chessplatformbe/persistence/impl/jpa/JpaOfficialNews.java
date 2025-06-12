package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.OfficialNewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOfficialNews extends JpaRepository<OfficialNewsEntity, Integer> {

    Page<OfficialNewsEntity> findAll(Pageable pageable);
}
