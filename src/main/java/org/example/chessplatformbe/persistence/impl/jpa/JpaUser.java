package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUser extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
