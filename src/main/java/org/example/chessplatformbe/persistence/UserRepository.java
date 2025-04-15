package org.example.chessplatformbe.persistence;


import org.example.chessplatformbe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

