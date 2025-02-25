package org.example.s3chessplatform.persistence;


import org.example.s3chessplatform.domain.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
