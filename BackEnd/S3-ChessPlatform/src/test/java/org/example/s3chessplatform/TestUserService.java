package org.example.s3chessplatform;

import org.example.s3chessplatform.business.UserService;
import org.example.s3chessplatform.domain.User;
import org.example.s3chessplatform.persistence.IUserRepository;
import org.example.s3chessplatform.persistence.MockedUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private IUserRepository userRepository;
    @Mock
    private MockedUserRepository mockedUserRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        userRepository = mockedUserRepository;
        sampleUser = new User(
                "testUser",
                "securePass",
                25,
                "Test Display",
                "Bulgaria"
        );
        sampleUser.setId(1L);
    }

    @Test
    void getAllUsers_ShouldReturnUsers() {
        List<User> mockUsers = Arrays.asList(sampleUser);
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldReturnEmpty_WhenNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(2L);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void createUser_ShouldReturnSavedUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User createdUser = userService.createUser(sampleUser);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        User updatedUser = new User(
                "updatedUser",
                "newPass",
                30,
                "Updated display",
                "Netherlands"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        Optional<User> result = userService.updateUser(1L, updatedUser);

        assertTrue(result.isPresent());
        assertEquals("updatedUser", result.get().getUsername());
        assertEquals(30, result.get().getAge());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldReturnEmpty_WhenUserNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> result = userService.updateUser(2L, sampleUser);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
