package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.mapper.CommentMapper;
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
    void testCreateComment_Success() {
        CreateCommentDTO dto = new CreateCommentDTO();
        dto.setUpdateId(null);
        dto.setText("Updated text");
        dto.setArticleId(1);
        dto.setUserId(1);
        Comment expected = CommentMapper.requestToObject(dto);

        when(commentRepository.save(any(Comment.class))).thenReturn(expected);

        Comment result = commentService.createComment(dto);

        assertEquals(expected.getText(), result.getText());
        assertEquals(expected.getArticleId(), result.getArticleId());
    }

    @Test
    void testUpdateComment_Success() {
        CreateCommentDTO dto = new CreateCommentDTO();
        dto.setUpdateId(10);
        dto.setText("Updated text");
        dto.setArticleId(1);
        dto.setUserId(1);
        Comment comment = CommentMapper.requestToObject(dto);

        when(commentRepository.findById(10)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment result = commentService.updateComment(dto);

        assertEquals("Updated text", result.getText());
    }

    @Test
    void testUpdateComment_MissingId() {
        CreateCommentDTO dto = new CreateCommentDTO();
        dto.setUpdateId(null);
        dto.setText("Updated");
        dto.setArticleId(1);
        dto.setUserId(1);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(dto));
        assertEquals("Update ID must be provided", ex.getMessage());
    }

    @Test
    void testUpdateComment_NotFound() {
        CreateCommentDTO dto = new CreateCommentDTO();
        dto.setUpdateId(5);
        dto.setText("Updated");
        dto.setArticleId(1);
        dto.setUserId(1);

        when(commentRepository.findById(5)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(dto));
        assertTrue(ex.getMessage().contains("Comment not found"));
    }

    @Test
    void testDeleteComment_Success() {
        Comment comment = new Comment(1, "Test", 1, 1);
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1);

        verify(commentRepository).delete(comment);
    }

    @Test
    void testDeleteComment_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(1));
        assertTrue(ex.getMessage().contains("Comment not found"));
    }

    @Test
    void testGetCommentByArticleId_Success() {
        Comment comment = new Comment(1, "Text", 2, 3);
        when(commentRepository.findByArticleId(3)).thenReturn(List.of(comment));

        List<Comment> result = commentService.getCommentByArticleId(3);

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getArticleId());
    }

    @Test
    void testGetCommentByUsername_Success() {
        Comment comment = new Comment(1, "Text", 2, 3);
        when(commentRepository.findCommentsByUsername("john")).thenReturn(List.of(comment));

        List<Comment> result = commentService.getCommentByAuthorUsername("john");

        assertEquals(1, result.size());
        assertEquals("Text", result.get(0).getText());
    }
}
