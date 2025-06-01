package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
import org.example.chessplatformbe.controller.dto.request.CreateStreamDTO;
import org.example.chessplatformbe.controller.dto.response.CommentResponceDTO;
import org.example.chessplatformbe.controller.dto.response.StreamResponseDTO;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.CommentEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.StreamEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class StreamMapper {
    private StreamMapper() {}

    public static Stream entityToObject(StreamEntity streamEntity) {
        Stream stream = new Stream();
        stream.setId(streamEntity.getId());
        stream.setName(streamEntity.getName());
        stream.setStreamUrl(streamEntity.getStreamUrl());
        stream.setStreamerId(streamEntity.getUser().getId());
        stream.setCreationDateTime(streamEntity.getCreationDateTime());

        return stream;
    }

    public static StreamEntity objectToEntity(Stream stream, UserEntity user) {
        StreamEntity streamEntity = new StreamEntity();
        streamEntity.setStreamUrl(stream.getStreamUrl());
        streamEntity.setCreationDateTime(stream.getCreationDateTime());
        streamEntity.setUser(user);
        streamEntity.setName(stream.getName());

        return streamEntity;
    }

    public static Stream requestToObject(CreateStreamDTO dto) {
        Stream stream = new Stream();
        stream.setId(dto.getUpdateId()); // Used when updating
        stream.setName(dto.getName());
        stream.setStreamerId(dto.getStreamerId());
        stream.setCreationDateTime(dto.getCreationDateTime());
        stream.setStreamUrl(dto.getStreamUrl());

        return stream;
    }

    public static StreamResponseDTO objectToResponse(Stream stream) {
        StreamResponseDTO dto = new StreamResponseDTO();
        dto.setId(stream.getId());
        dto.setName(stream.getName());
        dto.setStreamUrl(stream.getStreamUrl());
        dto.setCreationDateTime(stream.getCreationDateTime());
        dto.setStreamerId(stream.getStreamerId());

        return dto;
    }
}
