package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.exceptions.InvalidArticleException;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.persistence.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetArticleById_Success() {
        Article article = new Article();
        article.setId(1);
        article.setArticleTitle("title");
        article.setImageUrl("img");
        article.setContentText("text");
        article.setAuthorId(2);

        when(articleRepository.findById(1)).thenReturn(Optional.of(article));

        Article result = articleService.getArticleById(1);

        assertEquals(article, result);
    }

    @Test
    void testGetArticleById_NotFound() {
        when(articleRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> articleService.getArticleById(1));
        assertTrue(ex.getMessage().contains("Article not found"));
    }

    @Test
    void testGetArticlePage_Success() {
        Article article = new Article();
        article.setId(1);
        article.setArticleTitle("title");
        article.setImageUrl("img");
        article.setContentText("text");
        article.setAuthorId(2);

        List<Article> articleList = List.of(article);
        Page<Article> articlePage = new PageImpl<>(articleList);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        when(articleRepository.getArticlePage(pageable)).thenReturn(articlePage);

        Map<String, Object> result = articleService.getArticlePage(0, 5);

        assertEquals(1, ((List<?>) result.get("articles")).size());
        assertEquals(0, result.get("currentPage"));
        assertEquals(1L, result.get("totalItems"));
        assertEquals(1, result.get("totalPages"));
    }

    @Test
    void testCreateArticle_Success() {
        CreateArticleDTO dto = new CreateArticleDTO();
        dto.setUpdateId(1);
        dto.setArticleTitle("title");
        dto.setImageUrl("img");
        dto.setContentText("text");
        dto.setAuthorId(1);
        Article article = ArticleMapper.requestToObject(dto);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        Article result = articleService.createArticle(dto);

        assertEquals(article.getArticleTitle(), result.getArticleTitle());
    }

    @Test
    void testUpdateArticle_Success() {
        CreateArticleDTO dto = new CreateArticleDTO();
        dto.setUpdateId(1);
        dto.setArticleTitle("newTitle");
        dto.setImageUrl("img");
        dto.setContentText("text");
        dto.setAuthorId(1);
        Article updated = ArticleMapper.requestToObject(dto);

        when(articleRepository.findById(1)).thenReturn(Optional.of(updated));
        when(articleRepository.update(any(), eq(1))).thenReturn(updated);

        Article result = articleService.updateArticle(dto);

        assertEquals("newTitle", result.getArticleTitle());
    }

    @Test
    void testUpdateArticle_NoId() {
        CreateArticleDTO dto = new CreateArticleDTO();
        dto.setUpdateId(null);
        dto.setArticleTitle("title");
        dto.setImageUrl("img");
        dto.setContentText("text");
        dto.setAuthorId(1);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> articleService.updateArticle(dto));
        assertEquals("Update ID must be provided", ex.getMessage());
    }

    @Test
    void testUpdateArticle_NotFound() {
        CreateArticleDTO dto = new CreateArticleDTO();
        dto.setUpdateId(1);
        dto.setArticleTitle("title");
        dto.setImageUrl("img");
        dto.setContentText("text");
        dto.setAuthorId(1);

        when(articleRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> articleService.updateArticle(dto));
        assertTrue(ex.getMessage().contains("Article not found"));
    }

    @Test
    void testDeleteArticle_Success() {
        Article article = new Article();
        article.setId(1);
        article.setArticleTitle("title");
        article.setImageUrl("img");
        article.setContentText("text");
        article.setAuthorId(1);

        when(articleRepository.findById(1)).thenReturn(Optional.of(article));

        articleService.deleteArticle(1);

        verify(articleRepository).delete(article);
    }

    @Test
    void testDeleteArticle_NotFound() {
        when(articleRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> articleService.deleteArticle(1));
        assertTrue(ex.getMessage().contains("Article not found"));
    }

    @Test
    void testGetByTitle_Success() throws InvalidArticleException {
        Article article = new Article();
        article.setId(1);
        article.setArticleTitle("title");
        article.setImageUrl("img");
        article.setContentText("text");
        article.setAuthorId(1);

        when(articleRepository.findByTitle("title")).thenReturn(article);

        Article result = articleService.getByTitle("title");

        assertEquals("title", result.getArticleTitle());
    }

    @Test
    void testGetByTitle_Throws() throws InvalidArticleException {
        when(articleRepository.findByTitle("title")).thenThrow(new InvalidArticleException("title"));

        assertThrows(InvalidArticleException.class, () -> articleService.getByTitle("title"));
    }
}
