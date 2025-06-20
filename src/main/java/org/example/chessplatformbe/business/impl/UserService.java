package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IUserService;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Integer id) throws InvalidUserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        else{
            throw new InvalidUserException("User not found");
        }
    }

    @Override
    public User createUser(User user) throws InvalidUserException {
        try{
            userRepository.findByUsername(user.getUsername());
        }
        catch (InvalidUserException e){
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            return user;
        }
        throw new InvalidUserException(user.getUsername());
    }

    @Override
    public User updateUser(Integer id, User user) throws InvalidUserException {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new InvalidUserException("User with ID " + id + " not found.");
        }

        user.setId(existingUser.get().getId());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.delete(optionalUser.get());
    }

    @Override
    public User getUserByUsername(String username) throws InvalidUserException {
        return userRepository.findByUsername(username);
    }

    @Override
    public Double getAverageCommentPerArticle(Integer userId) {
        return userRepository.getAverageCommentsByUser(userId);
    }
}
