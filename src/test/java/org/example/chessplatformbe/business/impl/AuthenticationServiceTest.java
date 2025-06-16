package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.config.security.token.AccessToken;
import org.example.chessplatformbe.config.security.token.TokenEncoder;
import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.LoginToken;
import org.example.chessplatformbe.exceptions.InvalidCredentialsException;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenEncoder tokenEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private Admin testAdmin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAdmin = new Admin();
        testAdmin.setId(1);
        testAdmin.setUsername("adminuser");
        testAdmin.setPassword("hashedpass");
    }

    @Test
    void login_validCredentials_returnsToken() throws InvalidCredentialsException, InvalidUserException {
        when(userRepository.findByUsername("adminuser")).thenReturn(testAdmin);
        when(passwordEncoder.matches("plainpass", "hashedpass")).thenReturn(true);
        when(tokenEncoder.encode(any(AccessToken.class))).thenReturn("mocked-token");

        LoginToken token = authenticationService.login("adminuser", "plainpass");

        assertNotNull(token);
        assertEquals("mocked-token", token.getAccessToken());
        verify(userRepository).findByUsername("adminuser");
        verify(passwordEncoder).matches("plainpass", "hashedpass");
    }

    @Test
    void login_invalidPassword_throwsException() throws InvalidUserException {
        when(userRepository.findByUsername("adminuser")).thenReturn(testAdmin);
        when(passwordEncoder.matches("wrongpass", "hashedpass")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> authenticationService.login("adminuser", "wrongpass"));
    }

    @Test
    void login_userNotFound_throwsException() throws InvalidUserException {
        when(userRepository.findByUsername("nonexistent")).thenThrow(new InvalidUserException("nonexistent"));

        assertThrows(InvalidCredentialsException.class,
                () -> authenticationService.login("nonexistent", "pass"));
    }

    @Test
    void logout_doesNothing() {
        assertDoesNotThrow(() -> authenticationService.logout(1));
    }
}
