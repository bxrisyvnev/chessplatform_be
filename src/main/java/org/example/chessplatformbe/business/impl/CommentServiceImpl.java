package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.ICommentService;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.persistence.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment, Integer updateId) {
        if (updateId == null) {
            throw new IllegalArgumentException("Update ID must be provided");
        }

        Optional<Comment> existing = commentRepository.findById(updateId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Comment not found with ID: " + updateId);
        }

        return commentRepository.save(comment);
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
