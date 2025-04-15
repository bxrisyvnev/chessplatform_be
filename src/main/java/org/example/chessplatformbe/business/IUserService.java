package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.DTO.Request.CreateUserDTO;
import org.example.chessplatformbe.controller.DTO.Response.UserResponseDTO;
import org.example.chessplatformbe.domain.User;
import org.springframework.http.ResponseEntity;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();

    UserResponseDTO getUserById(Integer id);

    UserResponseDTO createUser(CreateUserDTO user);

    UserResponseDTO updateUser(Integer id, CreateUserDTO user);

    void deleteUser(Integer id);
}
