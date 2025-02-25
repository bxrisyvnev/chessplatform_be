package org.example.s3chessplatform.business;

import org.example.s3chessplatform.domain.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
}
