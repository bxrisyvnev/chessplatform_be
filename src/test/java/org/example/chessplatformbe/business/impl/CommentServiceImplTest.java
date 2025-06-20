package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.persistence.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComment_success() {
        Comment comment = new Comment(null, "Text", 1, 1);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.createComment(comment);

        assertEquals("Text", result.getText());
        assertEquals(1, result.getArticleId());
        assertEquals(1, result.getUserId());
    }

    @Test
    void updateComment_success() {
        Comment updated = new Comment(null, "Updated text", 1, 1);

        when(commentRepository.findById(10)).thenReturn(Optional.of(updated));
        when(commentRepository.save(updated)).thenReturn(updated);

        Comment result = commentService.updateComment(updated, 10);

        assertEquals("Updated text", result.getText());
    }

    @Test
    void updateComment_missingId() {
        Comment updated = new Comment(null, "Updated", 1, 1);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> commentService.updateComment(updated, null));

        assertEquals("Update ID must be provided", ex.getMessage());
    }

    @Test
    void updateComment_notFound() {
        Comment updated = new Comment(null, "Updated", 1, 1);
        when(commentRepository.findById(5)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> commentService.updateComment(updated, 5));

        assertEquals("Comment not found with ID: 5", ex.getMessage());
    }

    @Test
    void deleteComment_success() {
        Comment comment = new Comment(1, "Text", 1, 1);
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1);

        verify(commentRepository).delete(comment);
    }

    @Test
    void deleteComment_notFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> commentService.deleteComment(1));

        assertEquals("Comment not found with ID: 1", ex.getMessage());
    }

    @Test
    void getCommentByArticleId_success() {
        Comment comment = new Comment(1, "Text", 2, 3);
        when(commentRepository.findByArticleId(3)).thenReturn(List.of(comment));

        List<Comment> result = commentService.getCommentByArticleId(3);

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getArticleId());
    }

    @Test
    void getCommentByAuthorUsername_success() {
        Comment comment = new Comment(1, "Text", 2, 3);
        when(commentRepository.findCommentsByUsername("john")).thenReturn(List.of(comment));

        List<Comment> result = commentService.getCommentByAuthorUsername("john");

        assertEquals(1, result.size());
        assertEquals("Text", result.get(0).getText());
    }
}
