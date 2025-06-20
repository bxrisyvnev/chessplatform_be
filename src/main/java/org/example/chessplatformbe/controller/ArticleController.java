package org.example.chessplatformbe.controller;

import jakarta.validation.Valid;
import org.example.chessplatformbe.business.impl.ArticleServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @Autowired
    public ArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponceDTO> getArticleById(@PathVariable("id") Integer identification) {
        return ResponseEntity.ok(ArticleMapper.objectToResponse(articleService.getArticleById(identification)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Article> articlePage = articleService.getArticlePage(page, size);
        List<ArticleResponceDTO> articles = articlePage.getContent().stream()
                .map(ArticleMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("currentPage", articlePage.getNumber());
        response.put("totalItems", articlePage.getTotalElements());
        response.put("totalPages", articlePage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ArticleResponceDTO> createArticle(@Valid @RequestBody CreateArticleDTO dto) {
        return ResponseEntity.ok(ArticleMapper.objectToResponse(articleService.createArticle(ArticleMapper.requestToObject(dto))));
    }

    @PutMapping
    public ResponseEntity<ArticleResponceDTO> updateArticle(@Valid @RequestBody CreateArticleDTO dto) {
        return ResponseEntity.ok(ArticleMapper.objectToResponse(articleService.updateArticle(ArticleMapper.requestToObject(dto), dto.getUpdateId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer identification) {
        articleService.deleteArticle(identification);
        return ResponseEntity.ok().build();
    }
}
