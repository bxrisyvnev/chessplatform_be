package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.ArticleServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.controller.dto.request.GetArticleDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(articleService.getArticleById(identification));
    }

    @PostMapping
    public ResponseEntity<ArticleResponceDTO> createArticle(@RequestBody CreateArticleDTO dto) {
        return ResponseEntity.ok(articleService.createArticle(dto));
    }

    @PutMapping
    public ResponseEntity<ArticleResponceDTO> updateArticle(@RequestBody CreateArticleDTO dto) {
        return ResponseEntity.ok(articleService.updateArticle(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteArticle(@RequestBody GetArticleDTO dto) {
        articleService.deleteArticle(dto.getArticleId());
        return ResponseEntity.noContent().build();
    }
}
