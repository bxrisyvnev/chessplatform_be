package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Integer id);

    User save(User user);

    void delete(User user);

    User findByUsername(String username) throws InvalidUserException;
}
