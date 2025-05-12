package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.config.security.token.AccessToken;
import org.example.chessplatformbe.controller.DTO.Response.LoginResponseDTO;
import org.example.chessplatformbe.domain.LoginToken;
import org.springframework.stereotype.Component;

@Component
public class LogInTokenMapper {
    public static LoginResponseDTO objectToResponse(LoginToken logInToken) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(logInToken.getAccessToken());
        return loginResponseDTO;
    }
}
