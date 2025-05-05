package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.UserService;
import org.example.chessplatformbe.controller.DTO.Request.CreateUserDTO;
import org.example.chessplatformbe.controller.DTO.Request.GetUserDTO;
import org.example.chessplatformbe.controller.DTO.Response.UserResponseDTO;
import org.example.chessplatformbe.mapper.UserMapper;
import org.h2.engine.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getUser(@RequestBody GetUserDTO getUserDTO) {
        return ResponseEntity.ok(userService.getUserById(getUserDTO.getUserId()));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO request) {

        UserResponseDTO response = userService.createUser(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody CreateUserDTO request) {
        return ResponseEntity.ok(userService.updateUser(request.getUpdateId(), request));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestBody GetUserDTO getUserDTO) {
        userService.deleteUser(getUserDTO.getUserId());
        return ResponseEntity.noContent().build();
    }
}
