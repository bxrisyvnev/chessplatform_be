/*package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.DTO.Request.CreateAdminDTO;
import org.example.chessplatformbe.controller.DTO.Request.CreateSpectatorDTO;
import org.example.chessplatformbe.controller.DTO.Response.AdminResponseDTO;
import org.example.chessplatformbe.controller.DTO.Response.SpectatorResponseDTO;
import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.SpectatorPlayer;
import org.example.chessplatformbe.enums.Chroma;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.impl.jpa.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Admin testAdmin;
    private CreateAdminDTO createAdminDTO;
    private AdminResponseDTO adminResponseDTO;

    private SpectatorPlayer testSpectator;
    private CreateSpectatorDTO createSpectatorDTO;
    private SpectatorResponseDTO spectatorResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Admin setup (using setters instead of builder)
        testAdmin = new Admin();
        testAdmin.setId(1);
        testAdmin.setUsername("adminuser");
        testAdmin.setPassword(passwordEncoder.encode("plainpass"));
        testAdmin.setAge(30);
        testAdmin.setDisplayName("AdminDisplay");
        testAdmin.setNationality("US");
        testAdmin.setMonthlySalary(5000.0);
        testAdmin.setContractStartDate(LocalDate.of(2024, 1, 1));
        testAdmin.setContractEndDate(LocalDate.of(2025, 1, 1));
        testAdmin.setAddress("123 Street");

        createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setUsername("adminuser");
        createAdminDTO.setPassword("plainpass");
        createAdminDTO.setAge(30);
        createAdminDTO.setDisplayName("AdminDisplay");
        createAdminDTO.setNationality("US");
        createAdminDTO.setMonthlySalary(5000.0);
        createAdminDTO.setContractStartDate(LocalDate.of(2024, 1, 1));
        createAdminDTO.setContractEndDate(LocalDate.of(2025, 1, 1));
        createAdminDTO.setAddress("123 Street");

        adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setId(1);
        adminResponseDTO.setUsername("adminuser");
        adminResponseDTO.setAge(30);
        adminResponseDTO.setDisplayName("AdminDisplay");
        adminResponseDTO.setNationality("US");
        adminResponseDTO.setMonthlySalary(5000.0);
        adminResponseDTO.setContractStartDate("2025-01-01");
        adminResponseDTO.setContractEndDate("2026-01-01");
        adminResponseDTO.setAddress("123 Street");

        // SpectatorPlayer setup (using setters instead of builder)
        testSpectator = new SpectatorPlayer();
        testSpectator.setId(2);
        testSpectator.setUsername("spectator");
        testSpectator.setPassword(passwordEncoder.encode("plainpass2"));
        testSpectator.setAge(25);
        testSpectator.setDisplayName("SpecUser");
        testSpectator.setNationality("UK");
        testSpectator.setPlayerElo(1200);
        testSpectator.setChatBanned(false);
        testSpectator.setGameBanned(false);
        testSpectator.setNoOfGamesPlayed(10);
        testSpectator.setChroma(Chroma.Default);
        testSpectator.setHasPass(true);

        createSpectatorDTO = new CreateSpectatorDTO();
        createSpectatorDTO.setUsername("spectator");
        createSpectatorDTO.setPassword("plainpass2");
        createSpectatorDTO.setAge(25);
        createSpectatorDTO.setDisplayName("SpecUser");
        createSpectatorDTO.setNationality("UK");
        createSpectatorDTO.setPlayerElo(1200);
        createSpectatorDTO.setChatBanned(false);
        createSpectatorDTO.setGameBanned(false);
        createSpectatorDTO.setNoOfGamesPlayed(10);
        createSpectatorDTO.setChroma(String.valueOf(Chroma.Default));
        createSpectatorDTO.setHasPass(true);

        spectatorResponseDTO = new SpectatorResponseDTO();
        spectatorResponseDTO.setId(2);
        spectatorResponseDTO.setUsername("spectator");
        spectatorResponseDTO.setAge(25);
        spectatorResponseDTO.setDisplayName("SpecUser");
        spectatorResponseDTO.setNationality("UK");
        spectatorResponseDTO.setPlayerElo(1200);
        spectatorResponseDTO.setChatBanned(false);
        spectatorResponseDTO.setGameBanned(false);
        spectatorResponseDTO.setNoOfGamesPlayed(10);
        spectatorResponseDTO.setChroma(String.valueOf(Chroma.Default));
        spectatorResponseDTO.setHasPass(true);
    }


    @Test
    void getUserById_userNotFound_throwsException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(999);
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
        verify(userRepository).findById(999);
    }

    @Test
    void createUser_invalidData_throwsException() {
        // Invalid DTO with missing required fields
        CreateAdminDTO invalidDTO = new CreateAdminDTO();
        invalidDTO.setUsername("adminuser");
        invalidDTO.setPassword(null);  // Explicitly setting null to cause failure

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(invalidDTO);
        });

        assertEquals("rawPassword cannot be null", exception.getMessage());  // Match actual exception message
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(999, createAdminDTO);
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
        verify(userRepository).findById(999);
    }

    @Test
    void deleteUser_userNotFound_throwsException() {
        when(userRepository.existsById(999)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(999);
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
        verify(userRepository).existsById(999);
    }
}*/
