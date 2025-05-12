package org.example.chessplatformbe.business;


import org.example.chessplatformbe.domain.LoginToken;
import org.example.chessplatformbe.exceptions.InvalidCredentialsException;

public interface AuthenticationService {
    LoginToken login(String username, String password) throws InvalidCredentialsException;
    void logout(int userId);
}
