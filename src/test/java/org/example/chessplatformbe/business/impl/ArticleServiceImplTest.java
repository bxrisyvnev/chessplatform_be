package org.example.chessplatformbe.business.impl;

import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.exceptions.InvalidArticleException;
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

    private Article buildArticle(Integer id, String title, String img, String text, Integer authorId, String authorName) {
        Article art = new Article();
        art.setId(id);
        art.setArticleTitle(title);
        art.setImageUrl(img);
        art.setContentText(text);
        art.setAuthorId(authorId);
        art.setAuthorName(authorName);
        return art;
    }

    @Test
    void getArticleById_success() {
        Article article = buildArticle(1, "title", "img", "text", 2, "Mime");
        when(articleRepository.findById(1)).thenReturn(Optional.of(article));

        Article result = articleService.getArticleById(1);

        assertEquals(article, result);
    }

    @Test
    void getArticleById_notFound() {
        when(articleRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> articleService.getArticleById(1));

        assertEquals("Article not found with ID: 1", ex.getMessage());
    }

    @Test
    void getArticlePage_success() {
        Article article = buildArticle(1, "title", "img", "text", 2, "Mime");
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        Page<Article> mockPage = new PageImpl<>(List.of(article));

        when(articleRepository.getArticlePage(pageable)).thenReturn(mockPage);

        Page<Article> result = articleService.getArticlePage(0, 5);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getNumber());
    }

    @Test
    void getArticlePageByTitle_success() {
        Article article = buildArticle(1, "title", "img", "text", 2, "Mime");
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        Page<Article> mockPage = new PageImpl<>(List.of(article));

        when(articleRepository.getArticlesByTitlePage("title", pageable))
                .thenReturn(mockPage);

        Page<Article> result =
                articleService.getArticlePageByTitle("title", 0, 5);

        assertEquals(1, result.getContent().size());
        assertEquals("title", result.getContent()
                .get(0)
                .getArticleTitle());
    }

    @Test
    void updateArticle_success() {
        Article updated = buildArticle(null, "newTitle", "img", "text", 1, "Mime");

        when(articleRepository.findById(1)).thenReturn(Optional.of(updated));
        when(articleRepository.update(updated, 1)).thenReturn(updated);

        Article result = articleService.updateArticle(updated, 1);

        assertEquals("newTitle", result.getArticleTitle());
    }

    @Test
    void updateArticle_noId() {
        Article updated = buildArticle(null, "title", "img", "text", 1, "Mime");

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> articleService.updateArticle(updated, null));

        assertEquals("Update ID must be provided", ex.getMessage());
    }

    @Test
    void updateArticle_notFound() {
        Article updated = buildArticle(null, "title", "img", "text", 1, "Mime");
        when(articleRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> articleService.updateArticle(updated, 1));

        assertEquals("Article not found with ID: 1", ex.getMessage());
    }

    @Test
    void deleteArticle_success() {
        Article article = buildArticle(1, "title", "img", "text", 1, "mime");
        when(articleRepository.findById(1)).thenReturn(Optional.of(article));

        articleService.deleteArticle(1);

        verify(articleRepository).delete(article);
    }

    @Test
    void deleteArticle_notFound() {
        when(articleRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> articleService.deleteArticle(1));

        assertEquals("Article not found with ID: 1", ex.getMessage());
    }

    @Test
    void getByTitle_success() throws InvalidArticleException {
        Article article = buildArticle(1, "title", "img", "text", 1, "Mime");
        when(articleRepository.findByTitle("title")).thenReturn(article);

        Article result = articleService.getByTitle("title");

        assertEquals("title", result.getArticleTitle());
    }

    @Test
    void getByTitle_throws() throws InvalidArticleException {
        when(articleRepository.findByTitle("title"))
                .thenThrow(new InvalidArticleException("title"));

        assertThrows(InvalidArticleException.class,
                () -> articleService.getByTitle("title"));
    }
}
