package org.example.chessplatformbe.controller;

import org.example.chessplatformbe.business.impl.StreamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.ok(streamService.getStreamPage(page, size));
    }
}
