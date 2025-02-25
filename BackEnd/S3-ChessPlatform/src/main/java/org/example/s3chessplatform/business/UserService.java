package org.example.s3chessplatform.business;

import org.example.s3chessplatform.domain.User;
import org.example.s3chessplatform.persistence.IUserRepository;
import org.example.s3chessplatform.persistence.MockedUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(MockedUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setDisplayName(updatedUser.getDisplayName());
            existingUser.setNationality(updatedUser.getNationality());
            return userRepository.save(existingUser);
        });
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
