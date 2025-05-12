package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.DTO.Request.CreateUserDTO;
import org.example.chessplatformbe.controller.DTO.Response.UserResponseDTO;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.springframework.http.ResponseEntity;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserResponseDTO getUserById(Integer id) throws InvalidUserException; // replace dto with object

    UserResponseDTO createUser(CreateUserDTO user);

    UserResponseDTO updateUser(Integer id, CreateUserDTO user) throws InvalidUserException;

    void deleteUser(Integer id) throws InvalidUserException;
}
