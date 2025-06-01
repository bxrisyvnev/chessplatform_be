package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IStreamService;
import org.example.chessplatformbe.controller.dto.request.CreateStreamDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.example.chessplatformbe.controller.dto.response.StreamResponseDTO;
import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.mapper.StreamMapper;
import org.example.chessplatformbe.persistence.StreamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements IStreamService {

    private final StreamRepository streamRepository;

    @Override
    public Stream getStreamById(Integer streamId) {
        Optional<Stream> streamOptional = streamRepository.findById(streamId);
        return streamOptional.orElseThrow(() -> new IllegalArgumentException("Stream not found with ID: " + streamId));
    }

    @Override
    public Stream createStream(CreateStreamDTO dto) {
        return streamRepository.save(StreamMapper.requestToObject(dto));
    }

    @Override
    public void deleteStream(Integer streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new IllegalArgumentException("Stream not found with ID: " + streamId));
        streamRepository.delete(stream);
    }

    @Override
    public Map<String, java. lang. Object> getStreamPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Stream> streamPage = streamRepository.getStreamPage(pageable);

        List<StreamResponseDTO> streams = streamPage.getContent().stream()
                .map(StreamMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("streams", streams);
        response.put("currentPage", streamPage.getNumber());
        response.put("totalItems", streamPage.getTotalElements());
        response.put("totalPages", streamPage.getTotalPages());

        return response;
    }
}
