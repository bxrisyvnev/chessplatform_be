package org.example.chessplatformbe.business;

import org.example.chessplatformbe.domain.Stream;
import org.springframework.data.domain.Page;

public interface IStreamService {

    Stream getStreamById(Integer streamId);

    Stream createStream(Stream stream);

    void deleteStream(Integer streamId);

    Page<Stream> getStreamPage(Integer page, Integer size);
}
