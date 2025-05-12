package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IUserService;
import org.example.chessplatformbe.controller.DTO.Request.CreateUserDTO;
import org.example.chessplatformbe.controller.DTO.Response.UserResponseDTO;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO getUserById(Integer id) throws InvalidUserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            return UserMapper.objectToResponce(user1);
        }
        else{
            throw new InvalidUserException("User not found");
        }
    }

    @Override
    public UserResponseDTO createUser(CreateUserDTO user) {

        User userObject = UserMapper.requestToObject(user);
        UserResponseDTO response = UserMapper.objectToResponce(userObject);

        userObject.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userObject);

        return response;
    }

    @Override
    public UserResponseDTO updateUser(Integer id, CreateUserDTO user) throws InvalidUserException {
        Optional<User> existingUser = userRepository.findById(id);

        User updatedUser = UserMapper.requestToObject(user);
        updatedUser.setId(existingUser.get().getId());

        User saved = userRepository.save(updatedUser);
        return UserMapper.objectToResponce(saved);
    }

    @Override
    public void deleteUser(Integer id) throws InvalidUserException {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }
}
