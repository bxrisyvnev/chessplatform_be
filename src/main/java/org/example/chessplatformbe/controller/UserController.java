package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.UserService;
import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.controller.dto.request.GetUserDTO;
import org.example.chessplatformbe.controller.dto.response.UserResponseDTO;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getUser(@RequestBody GetUserDTO getUserDTO) throws InvalidUserException {
        return ResponseEntity.ok(userService.getUserById(getUserDTO.getUserId()));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO request) {

        UserResponseDTO response = userService.createUser(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody CreateUserDTO request) throws InvalidUserException {
        return ResponseEntity.ok(userService.updateUser(request.getUpdateId(), request));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestBody GetUserDTO getUserDTO) throws InvalidUserException {
        userService.deleteUser(getUserDTO.getUserId());
        return ResponseEntity.noContent().build();
    }
}
