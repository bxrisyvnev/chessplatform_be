package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.controller.dto.response.UserResponseDTO;
import org.example.chessplatformbe.exceptions.InvalidUserException;

public interface IUserService {

    UserResponseDTO getUserById(Integer id) throws InvalidUserException; // replace dto with object

    UserResponseDTO createUser(CreateUserDTO user);

    UserResponseDTO updateUser(Integer id, CreateUserDTO user) throws InvalidUserException;

    void deleteUser(Integer id);
}
