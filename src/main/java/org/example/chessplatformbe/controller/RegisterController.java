package org.example.chessplatformbe.controller;

import jakarta.validation.Valid;
import org.example.chessplatformbe.business.impl.UserService;
import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.controller.dto.response.UserResponseDTO;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO request) throws InvalidUserException {
        return ResponseEntity.ok(UserMapper.objectToResponce(userService.createUser(request)));
    }
}
