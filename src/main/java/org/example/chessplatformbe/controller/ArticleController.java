package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.ArticleServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(articleService.getArticlePage(page, size));
    }

    @PostMapping
    public ResponseEntity<ArticleResponceDTO> createArticle(@RequestBody CreateArticleDTO dto) {
        return ResponseEntity.ok(ArticleMapper.objectToResponse(articleService.createArticle(dto)));
    }

    @PutMapping
    public ResponseEntity<ArticleResponceDTO> updateArticle(@RequestBody CreateArticleDTO dto) {
        return ResponseEntity.ok(ArticleMapper.objectToResponse(articleService.updateArticle(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer identification) {
        articleService.deleteArticle(identification);
        return ResponseEntity.noContent().build();
    }
}
