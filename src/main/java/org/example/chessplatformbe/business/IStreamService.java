package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateStreamDTO;
import org.example.chessplatformbe.domain.Stream;

import java.util.Map;

public interface IStreamService {

    Stream getStreamById(Integer streamId);

    Stream createStream(CreateStreamDTO dto);

    void deleteStream(Integer streamId);

    Map<String, Object> getStreamPage(Integer page, Integer size);
}
