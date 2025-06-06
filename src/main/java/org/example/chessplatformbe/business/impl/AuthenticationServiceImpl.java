package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.AuthenticationService;
import org.example.chessplatformbe.config.security.token.AccessToken;
import org.example.chessplatformbe.config.security.token.TokenEncoder;
import org.example.chessplatformbe.domain.LoginToken;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.ProfessionalPlayer;
import org.example.chessplatformbe.domain.SpectatorPlayer;
import org.example.chessplatformbe.exceptions.InvalidCredentialsException;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenEncoder accessTokenEncoder;

    public LoginToken login(String username, String password) throws InvalidCredentialsException {
        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (InvalidUserException e) {
            throw new InvalidCredentialsException();
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return doLogIn(user);
        }

        throw new InvalidCredentialsException();
    }

    private LoginToken doLogIn(User user) {
        int userId = user.getId();
        String role;

        if (user instanceof Admin) {
            role = "Admin";
        } else if (user instanceof ProfessionalPlayer) {
            role = "ProfessionalPlayer";
        } else if (user instanceof SpectatorPlayer) {
            role = "SpectatorPlayer";
        } else {
            throw new IllegalArgumentException("Unknown user type for token");
        }

        String accessToken = accessTokenEncoder.encode(
                new AccessToken(user.getUsername(), userId, List.of(role))
        );

        return LoginToken.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public void logout(int userId) {
        // Optional logout logic
    }
}
