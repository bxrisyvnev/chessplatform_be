package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;

public interface IUserService {

    User getUserById(Integer id) throws InvalidUserException;

    User createUser(CreateUserDTO user) throws InvalidUserException;

    User updateUser(Integer id, CreateUserDTO user) throws InvalidUserException;

    void deleteUser(Integer id);

    User getUserByUsername(String username) throws InvalidUserException;

    Double getAverageCommentPerArticle(Integer userId);
}
