package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.dto.request.CreateAdminDTO;
import org.example.chessplatformbe.controller.dto.request.CreateSpectatorDTO;
import org.example.chessplatformbe.controller.dto.response.AdminResponseDTO;
import org.example.chessplatformbe.controller.dto.response.SpectatorResponseDTO;
import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.SpectatorPlayer;
import org.example.chessplatformbe.enums.Chroma;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.impl.jpa.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Admin testAdmin;
    private CreateAdminDTO createAdminDTO;
    private AdminResponseDTO adminResponseDTO;

    private SpectatorPlayer testSpectator;
    private CreateSpectatorDTO createSpectatorDTO;
    private SpectatorResponseDTO spectatorResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAdmin = new Admin();
        testAdmin.setId(1);
        testAdmin.setUsername("adminuser");
        testAdmin.setPassword("hashedpass");
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
        adminResponseDTO.setContractStartDate("2024-01-01");
        adminResponseDTO.setContractEndDate("2025-01-01");
        adminResponseDTO.setAddress("123 Street");

        testSpectator = new SpectatorPlayer();
        testSpectator.setId(2);
        testSpectator.setUsername("spectator");
        testSpectator.setPassword("hashedpass2");
        testSpectator.setAge(25);
        testSpectator.setDisplayName("SpecUser");
        testSpectator.setNationality("UK");
        testSpectator.setPlayerElo(1200);
        testSpectator.setChatBanned(false);
        testSpectator.setGameBanned(false);
        testSpectator.setNoOfGamesPlayed(10);
        testSpectator.setChroma(Chroma.DEFAULT);
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
        createSpectatorDTO.setChroma("Default");
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
        spectatorResponseDTO.setChroma("Default");
        spectatorResponseDTO.setHasPass(true);
    }

    @Test
    void getUserById_validId_returnsUser() throws InvalidUserException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testAdmin));

        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            mockedMapper.when(() -> UserMapper.objectToResponce(testAdmin)).thenReturn(adminResponseDTO);

            AdminResponseDTO result = (AdminResponseDTO) userService.getUserById(1);

            assertNotNull(result);
            assertEquals("adminuser", result.getUsername());
        }
    }

    @Test
    void getUserById_userNotFound_throwsException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getUserById(999));
    }

    @Test
    void createUser_validAdmin_returnsResponse() {
        when(passwordEncoder.encode("plainpass")).thenReturn("hashedpass");
        when(userRepository.save(any(Admin.class))).thenReturn(testAdmin);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.requestToObject(createAdminDTO)).thenReturn(testAdmin);
            mockedUserMapper.when(() -> UserMapper.objectToResponce(testAdmin)).thenReturn(adminResponseDTO);

            AdminResponseDTO result = (AdminResponseDTO) userService.createUser(createAdminDTO);

            assertNotNull(result);
            assertEquals("adminuser", result.getUsername());
            verify(userRepository).save(testAdmin);
            verify(passwordEncoder).encode("plainpass");
        }
    }

    @Test
    void createUser_validSpectator_returnsResponse() {
        when(passwordEncoder.encode("plainpass2")).thenReturn("hashedpass2");
        when(userRepository.save(any(SpectatorPlayer.class))).thenReturn(testSpectator);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.requestToObject(createSpectatorDTO)).thenReturn(testSpectator);
            mockedUserMapper.when(() -> UserMapper.objectToResponce(testSpectator)).thenReturn(spectatorResponseDTO);

            SpectatorResponseDTO result = (SpectatorResponseDTO) userService.createUser(createSpectatorDTO);

            assertNotNull(result);
            assertEquals("spectator", result.getUsername());
            verify(userRepository).save(testSpectator);
            verify(passwordEncoder).encode("plainpass2");
        }
    }

    @Test
    void updateUser_validId_returnsUpdatedUser() throws InvalidUserException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testAdmin));
        when(userRepository.save(any(Admin.class))).thenReturn(testAdmin);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.requestToObject(createAdminDTO)).thenReturn(testAdmin);
            mockedUserMapper.when(() -> UserMapper.objectToResponce(testAdmin)).thenReturn(adminResponseDTO);

            AdminResponseDTO result = (AdminResponseDTO) userService.updateUser(1, createAdminDTO);

            assertNotNull(result);
            assertEquals("adminuser", result.getUsername());
        }
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.updateUser(999, createAdminDTO));
    }

    @Test
    void deleteUser_validId_deletesSuccessfully() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testAdmin));

        assertDoesNotThrow(() -> userService.deleteUser(1));
        verify(userRepository).delete(testAdmin);
    }

    @Test
    void deleteUser_userNotFound_throwsException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(999);
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
    }
}
