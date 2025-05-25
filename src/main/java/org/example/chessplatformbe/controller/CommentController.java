package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.CommentServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
import org.example.chessplatformbe.controller.dto.request.GetCommentDTO;
import org.example.chessplatformbe.controller.dto.response.CommentResponceDTO;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentResponceDTO>> getCommentById(@PathVariable("id") Integer identification) {
        List<Comment> comments = commentService.getCommentByArticleId(identification);
        List<CommentResponceDTO> dtos = comments.stream()
                .map(CommentMapper::objectToResponse)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CommentResponceDTO> createComment(@RequestBody CreateCommentDTO dto) {
        return ResponseEntity.ok(CommentMapper.objectToResponse(commentService.createComment(dto)));
    }

    @PutMapping
    public ResponseEntity<CommentResponceDTO> updateComment(@RequestBody CreateCommentDTO dto) {
        return ResponseEntity.ok(CommentMapper.objectToResponse(commentService.updateComment(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Integer identification) {
        commentService.deleteComment(identification);
        return ResponseEntity.noContent().build();
    }
}
