package org.example.chessplatformbe.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.AuthenticationService;
import org.example.chessplatformbe.config.security.token.AccessToken;
import org.example.chessplatformbe.controller.dto.request.LoginRequestDTO;
import org.example.chessplatformbe.controller.dto.response.LoginResponseDTO;
import org.example.chessplatformbe.domain.LoginToken;
import org.example.chessplatformbe.mapper.LogInTokenMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private AccessToken requestAccessToken;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        LoginToken loginToken = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
        LoginResponseDTO loginResponse = LogInTokenMapper.objectToResponse(loginToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        if (requestAccessToken != null) {
            authenticationService.logout(requestAccessToken.getUserId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
