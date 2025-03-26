package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.business.IUserService;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.persistence.IUserRepository;
import org.example.chessplatformbe.persistence.impl.MockUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(MockUserRepository userRepository) {
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
        String hash = UUID.randomUUID().toString();
        user.setHash(hash);

        user.setPassword(passwordEncoder.encode(user.getPassword() + hash));

        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUserUsername(Long id, String updatedUsername) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(updatedUsername);
            return userRepository.save(existingUser);
        });
    }

    @Override
    public Optional<User> updateUserPassword(Long id, String updatedPassword) {
        String hash = UUID.randomUUID().toString();
        String hasedPass = passwordEncoder.encode(updatedPassword + hash);

        return userRepository.findById(id).map(existingUser -> {
            existingUser.setPassword(hasedPass);
            existingUser.setHash(hash);
            return userRepository.save(existingUser);
        });
    }

    @Override
    public Optional<User> updateUserAge(Long id, int updatedAge) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setAge(updatedAge);
            return userRepository.save(existingUser);
        });
    }

    @Override
    public Optional<User> updateUserDisplayName(Long id, String updatedDisplayName) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setDisplayName(updatedDisplayName);
            return userRepository.save(existingUser);
        });
    }

    @Override
    public Optional<User> updateUseNationality(Long id, String updatedNationality) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setNationality(updatedNationality);
            return userRepository.save(existingUser);
        });
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
