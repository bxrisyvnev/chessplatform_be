package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.business.IUserService;
import org.example.chessplatformbe.controller.DTO.Request.CreateUserDTO;
import org.example.chessplatformbe.controller.DTO.Response.UserResponseDTO;
import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO createUser(CreateUserDTO user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Convert DTO to entity
        User userEntity = userMapper.toEntity(user);

        // Save to DB
        User savedUser = userRepository.save(userEntity);

        // Convert saved entity to response DTO
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(Integer id, CreateUserDTO user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        User updatedUser = userMapper.toEntity(user);
        updatedUser.setId(existingUser.getId());

        User saved = userRepository.save(updatedUser);
        return userMapper.toResponse(saved);
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
