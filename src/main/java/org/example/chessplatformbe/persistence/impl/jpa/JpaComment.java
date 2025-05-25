package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaComment extends JpaRepository<CommentEntity, Integer>{
    List<CommentEntity> findByArticle_Id(int id);
}
