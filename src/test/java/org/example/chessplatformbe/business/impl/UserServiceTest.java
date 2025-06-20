package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.SpectatorPlayer;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Admin admin;
    private SpectatorPlayer spectator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        admin = new Admin();
        admin.setId(1);
        admin.setUsername("adminuser");
        admin.setPassword("hashedpass");
        admin.setAge(30);
        admin.setDisplayName("AdminDisplay");
        admin.setNationality("US");
        admin.setMonthlySalary(5000.0);
        admin.setContractStartDate(LocalDate.of(2024, 1, 1));
        admin.setContractEndDate(LocalDate.of(2025, 1, 1));
        admin.setAddress("123 Street");

        spectator = new SpectatorPlayer();
        spectator.setId(2);
        spectator.setUsername("spectator");
        spectator.setPassword("hashedpass2");
        spectator.setAge(25);
        spectator.setDisplayName("SpecUser");
        spectator.setNationality("UK");
        spectator.setPlayerElo(1200);
        spectator.setChatBanned(false);
        spectator.setGameBanned(false);
        spectator.setNoOfGamesPlayed(10);
        spectator.setHasPass(true);
    }

    /* ---------- get by id ---------- */
    @Test
    void getUserById_found_returnsUser() throws InvalidUserException {
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));

        User result = userService.getUserById(1);

        assertThat(result).isEqualTo(admin);
    }

    @Test
    void getUserById_notFound_throwsException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(999))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("User not found is not a valid user");
    }

    /* ---------- create ---------- */
    @Test
    void createUser_admin_success() throws InvalidUserException {
        when(userRepository.findByUsername("adminuser"))
                .thenThrow(new InvalidUserException("User not found"));
        when(passwordEncoder.encode("plainpass")).thenReturn("hashedpass");
        admin.setPassword("plainpass");

        when(userRepository.save(admin)).thenReturn(admin);

        User result = userService.createUser(admin);

        assertThat(result.getUsername()).isEqualTo("adminuser");
        verify(passwordEncoder).encode("plainpass");
        verify(userRepository).save(admin);
    }

    @Test
    void createUser_existingUsername_throws() throws InvalidUserException {
        when(userRepository.findByUsername("adminuser")).thenReturn(admin);

        admin.setPassword("plainpass");

        assertThatThrownBy(() -> userService.createUser(admin))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("adminuser is not a valid user");
    }

    /* ---------- update ---------- */
    @Test
    void updateUser_existingUser_success() throws InvalidUserException {
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(userRepository.save(admin)).thenReturn(admin);

        User result = userService.updateUser(1, admin);

        assertThat(result).isEqualTo(admin);
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void updateUser_userNotFound_throws() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(999, admin))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("User with ID 999 not found. is not a valid user");
    }

    /* ---------- delete ---------- */
    @Test
    void deleteUser_found_deletes() {
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));

        userService.deleteUser(1);

        verify(userRepository).delete(admin);
    }

    @Test
    void deleteUser_notFound_throws() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found with ID: 999");
    }

    /* ---------- get by username ---------- */
    @Test
    void getUserByUsername_found() throws InvalidUserException {
        when(userRepository.findByUsername("adminuser")).thenReturn(admin);

        User result = userService.getUserByUsername("adminuser");

        assertThat(result).isEqualTo(admin);
    }

    /* ---------- get average comments ---------- */
    @Test
    void getAverageCommentPerArticle_validId() {
        when(userRepository.getAverageCommentsByUser(1)).thenReturn(2.67);

        Double avg = userService.getAverageCommentPerArticle(1);

        assertThat(avg).isEqualTo(2.67);
    }
}
