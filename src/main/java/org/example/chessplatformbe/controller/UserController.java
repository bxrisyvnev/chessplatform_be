package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.CommentServiceImpl;
import org.example.chessplatformbe.business.impl.UserService;
import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.controller.dto.request.GetUserDTO;
import org.example.chessplatformbe.controller.dto.response.UserProfileDTO;
import org.example.chessplatformbe.controller.dto.response.UserResponseDTO;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.CommentRepository;
import org.example.chessplatformbe.persistence.impl.jpa.CommentRepositoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CommentServiceImpl commentService;

    public UserController(UserService userService, CommentServiceImpl commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getUser(@RequestBody GetUserDTO getUserDTO) throws InvalidUserException {
        return ResponseEntity.ok(UserMapper.objectToResponce(userService.getUserById(getUserDTO.getUserId())));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO request) {
        return ResponseEntity.ok(UserMapper.objectToResponce(userService.createUser(request)));
    }

    @PutMapping()
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody CreateUserDTO request) throws InvalidUserException {
        return ResponseEntity.ok(UserMapper.objectToResponce(userService.updateUser(request.getUpdateId(), request)));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestBody GetUserDTO getUserDTO) {
        userService.deleteUser(getUserDTO.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable String username) throws InvalidUserException {
        return ResponseEntity.ok(UserMapper.userToProfileDTO(userService.getUserByUsername(username), commentService.getCommentByAuthorUsername(username)));
    }
}
