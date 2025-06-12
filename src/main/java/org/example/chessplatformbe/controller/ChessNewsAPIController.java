package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.OfficialNewsServiceImpl;
import org.example.chessplatformbe.controller.dto.response.OfficialNewsResponceDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.mapper.OfficialNewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chess")
public class ChessNewsAPIController {

    private final OfficialNewsServiceImpl officialNewsService;

    @Autowired
    public ChessNewsAPIController(OfficialNewsServiceImpl officialNewsService) {
        this.officialNewsService = officialNewsService;
    }

    @GetMapping
    public ResponseEntity<List<OfficialNewsResponceDTO>> getNewsByFive() throws Exception {
        List<OfficialNews> news = officialNewsService.getLatestFive();
        List<OfficialNewsResponceDTO> dtos = news.stream()
                .map(OfficialNewsMapper::objectToResponse)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
