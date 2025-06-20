package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IStreamService;
import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.persistence.StreamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Stream createStream(Stream stream) {
        return streamRepository.save(stream);
    }

    @Override
    public void deleteStream(Integer streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new IllegalArgumentException("Stream not found with ID: " + streamId));
        streamRepository.delete(stream);
    }

    @Override
    public Page<Stream> getStreamPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return streamRepository.getStreamPage(pageable);
    }
}
