/*package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestUserService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setUsername("chessMaster123");
        user.setPassword("securePass");
        user.setAge(25);
        user.setDisplayName("Chess Master");
        user.setNationality("Norway");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserWhenFound() {
        User updatedUser = new User();
        updatedUser.setUsername("newName");
        updatedUser.setPassword("newPass");
        updatedUser.setAge(30);
        updatedUser.setDisplayName("Updated Master");
        updatedUser.setNationality("Sweden");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<User> result = userService.updateUser(1, updatedUser);

        assertTrue(result.isPresent());
        assertEquals("newName", result.get().getUsername());
        assertEquals("Sweden", result.get().getNationality());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserWhenNotFound() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        Optional<User> result = userService.updateUser(999, user);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(999);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }
 }
 */

