package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.controller.dto.response.UserResponseDTO;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;

public interface IUserService {

    User getUserById(Integer id) throws InvalidUserException; // replace dto with object

    User createUser(CreateUserDTO user);

    User updateUser(Integer id, CreateUserDTO user) throws InvalidUserException;

    void deleteUser(Integer id);
}
