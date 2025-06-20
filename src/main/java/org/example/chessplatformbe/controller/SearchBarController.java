package org.example.chessplatformbe.controller;


import org.example.chessplatformbe.business.impl.ArticleServiceImpl;
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
@RequestMapping("/searchbar")
public class SearchBarController {

    private final ArticleServiceImpl articleService;

    @Autowired
    public SearchBarController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    @GetMapping(params = "title")
    public ResponseEntity<Map<String, Object>> searchByTitle(
            @RequestParam String title,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<Article> articlePage = articleService.getArticlePageByTitle(title, page, size);

        List<ArticleResponceDTO> dtos = articlePage.getContent().stream()
                .map(ArticleMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", dtos);
        response.put("currentPage", articlePage.getNumber());
        response.put("totalItems", articlePage.getTotalElements());
        response.put("totalPages", articlePage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
