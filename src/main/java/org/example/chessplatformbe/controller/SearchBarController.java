package org.example.chessplatformbe.controller;


import org.example.chessplatformbe.business.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(articleService.getArticlePageByTitle(title, page, size));
    }
}
