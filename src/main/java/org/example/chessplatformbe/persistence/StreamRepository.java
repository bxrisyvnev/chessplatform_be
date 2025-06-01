package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StreamRepository {
    Optional<Stream> findById(Integer id);

    Stream save(Stream stream);

    void delete(Stream stream);

    Page<Stream> getStreamPage(Pageable pageable);
}
