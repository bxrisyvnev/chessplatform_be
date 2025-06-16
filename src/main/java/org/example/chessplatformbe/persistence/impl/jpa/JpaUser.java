package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUser extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);

    @Query(value = """
        SELECT COALESCE(AVG(sub.comment_count), 0) AS avg_comments
          FROM (
               SELECT COUNT(c.id) AS comment_count
                 FROM articles a
            LEFT JOIN comments c ON a.id = c.article_id
                WHERE a.author_id = ?1
             GROUP BY a.id
          ) sub
        """, nativeQuery = true)
    Double findAverageCommentsByUser(Integer userId);
}
