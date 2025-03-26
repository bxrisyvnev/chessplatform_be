package org.example.chessplatformbe.controller;

import jakarta.validation.Valid;
import org.example.chessplatformbe.business.impl.UserService;
import org.example.chessplatformbe.controller.DTO.*;
import org.example.chessplatformbe.controller.DTO.UpdateUserUsernameDTO;
import org.example.chessplatformbe.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestBody @Valid GetUserByIdDTO getUserByIdDTO) {
        Optional<User> user = userService.getUserById(Long.valueOf(getUserByIdDTO.getId()));

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        User user = new User(createUserDTO.getUsername(), createUserDTO.getAge(), createUserDTO.getDisplayName(), createUserDTO.getNationality());
        user.setPassword(createUserDTO.getPassword());
        return userService.createUser(user);
    }

    @PutMapping("/update-username")
    public ResponseEntity<User> updateUserUsername(@RequestBody @Valid UpdateUserUsernameDTO updatedUserUsernameDTO) {
        Optional<User> updated = userService.updateUserUsername(updatedUserUsernameDTO.getId(), updatedUserUsernameDTO.getUsername());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update-password")
    public ResponseEntity<User> updateUserPassword(@RequestBody @Valid UpdateUserPasswordDTO updatedUserPasswordDTO){

        Optional<User> updated = userService.updateUserPassword(updatedUserPasswordDTO.getId(), updatedUserPasswordDTO.getPassword());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update-age")
    public ResponseEntity<User> updateUserAge(@RequestBody @Valid UpdateUserAgeDTO updatedUserAgeDTO){

        Optional<User> updated = userService.updateUserAge(updatedUserAgeDTO.getId(), updatedUserAgeDTO.getAge());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update-displayName")
    public ResponseEntity<User> updateUserDisplayName(@RequestBody @Valid UpdateUserDisplayNameDTO updatedUserDisplayNameDTO){

        Optional<User> updated = userService.updateUserDisplayName(updatedUserDisplayNameDTO.getId(), updatedUserDisplayNameDTO.getDisplayName());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update-nationality")
    public ResponseEntity<User> updateUserNationality(@RequestBody @Valid UpdateUserNationalityDTO updatedUserNationalityDTO){

        Optional<User> updated = userService.updateUseNationality(updatedUserNationalityDTO.getId(), updatedUserNationalityDTO.getNationality());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody @Valid DeleteUserDTO deleteUserDTO) {
        userService.deleteUser(Long.valueOf(deleteUserDTO.getId()));
        return ResponseEntity.noContent().build();
    }
}
