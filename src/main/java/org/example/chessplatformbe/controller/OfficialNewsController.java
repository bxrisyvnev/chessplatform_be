package org.example.chessplatformbe.controller;


import org.example.chessplatformbe.business.impl.OfficialNewsServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
import org.example.chessplatformbe.controller.dto.response.OfficialNewsResponceDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.mapper.OfficialNewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class OfficialNewsController {
    private final OfficialNewsServiceImpl officialNewsService;

    @Autowired
    public OfficialNewsController(OfficialNewsServiceImpl officialNewsService) {
        this.officialNewsService = officialNewsService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOfficialNewsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        return ResponseEntity.ok(officialNewsService.getOfficialNewsPage(page, size));
    }

    @PostMapping
    public ResponseEntity<List<OfficialNewsResponceDTO>> createNews(
            @RequestBody List<CreateOfficialNewsDTO> dtos
    ) {
        List<OfficialNews> news = officialNewsService.createOfficialNews(dtos);
        List<OfficialNewsResponceDTO> dtoList = news.stream()
                .map(OfficialNewsMapper::objectToResponse)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
