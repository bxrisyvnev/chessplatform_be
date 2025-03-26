package org.example.chessplatformbe.business;

import org.example.chessplatformbe.domain.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUserUsername(Long id, String updatedUsername);
    Optional<User> updateUserPassword(Long id, String updatedPassword);
    Optional<User> updateUserAge(Long id, int updatedAge);
    Optional<User> updateUserDisplayName(Long id, String updatedDisplayName);
    Optional<User> updateUseNationality(Long id, String updatedNationality);
    void deleteUser(Long id);
}
