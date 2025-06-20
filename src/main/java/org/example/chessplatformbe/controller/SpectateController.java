package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.StreamServiceImpl;
import org.example.chessplatformbe.controller.dto.response.StreamResponseDTO;
import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.mapper.StreamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spectate")
public class SpectateController {

    private final StreamServiceImpl streamService;

    @Autowired
    public SpectateController(StreamServiceImpl streamService) {
        this.streamService = streamService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStreams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {

        Page<Stream> streamPage = streamService.getStreamPage(page, size);

        List<StreamResponseDTO> streams = streamPage.getContent().stream()
                .map(StreamMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("streams", streams);
        response.put("currentPage", streamPage.getNumber());
        response.put("totalItems", streamPage.getTotalElements());
        response.put("totalPages", streamPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
