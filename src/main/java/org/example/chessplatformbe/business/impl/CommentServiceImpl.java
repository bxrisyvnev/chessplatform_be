package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.ICommentService;
import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.mapper.CommentMapper;
import org.example.chessplatformbe.persistence.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(CreateCommentDTO dto) {
        return commentRepository.save(CommentMapper.requestToObject(dto));
    }

    @Override
    public Comment updateComment(CreateCommentDTO dto) {
        if (dto.getUpdateId() == null) {
            throw new IllegalArgumentException("Update ID must be provided");
        }

        Optional<Comment> existing = commentRepository.findById(dto.getUpdateId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Comment not found with ID: " + dto.getUpdateId());
        }

        return commentRepository.save(CommentMapper.requestToObject(dto));
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getCommentByArticleId(Integer articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    @Override
    public List<Comment> getCommentByAuthorUsername(String username) {
        return commentRepository.findCommentsByUsername(username);
    }
}
