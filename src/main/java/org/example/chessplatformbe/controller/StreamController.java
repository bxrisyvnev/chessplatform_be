package org.example.chessplatformbe.controller;

import jakarta.validation.Valid;
import org.example.chessplatformbe.business.impl.StreamServiceImpl;
import org.example.chessplatformbe.controller.dto.request.CreateStreamDTO;
import org.example.chessplatformbe.controller.dto.response.StreamResponseDTO;
import org.example.chessplatformbe.mapper.StreamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/streams")
public class StreamController {
    private final StreamServiceImpl streamService;

    @Autowired
    public StreamController(StreamServiceImpl streamService) {
        this.streamService = streamService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamResponseDTO> getStreamById(@PathVariable("id") Integer identification) {
        return ResponseEntity.ok(StreamMapper.objectToResponse(streamService.getStreamById(identification)));
    }

    @PostMapping
    public ResponseEntity<StreamResponseDTO> createStream(@Valid @RequestBody CreateStreamDTO dto) {
        return ResponseEntity.ok(StreamMapper.objectToResponse(streamService.createStream(StreamMapper.requestToObject(dto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStream(@PathVariable("id") Integer identification) {
        streamService.deleteStream(identification);
        return ResponseEntity.ok().build();
    }
}
