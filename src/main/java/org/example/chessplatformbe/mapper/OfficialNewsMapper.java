package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.dto.response.OfficialNewsResponceDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.persistence.impl.jpa.entity.OfficialNewsEntity;
import org.springframework.stereotype.Component;

@Component
public class OfficialNewsMapper {
    private OfficialNewsMapper() {}

    public static OfficialNewsResponceDTO objectToResponse(OfficialNews officialNews) {
        OfficialNewsResponceDTO dto = new OfficialNewsResponceDTO();
        dto.setId(officialNews.getId());
        dto.setAuthor(officialNews.getAuthor());
        dto.setLink(officialNews.getLink());
        dto.setTitle(officialNews.getTitle());
        dto.setDescription(officialNews.getDescription());
        dto.setPublishedDate(String.valueOf(officialNews.getPublishedDate()));

        return dto;
    }

    public static OfficialNewsEntity objectToEntity(OfficialNews officialNews) {
        OfficialNewsEntity officialNewsEntity = new OfficialNewsEntity();
        officialNewsEntity.setAuthor(officialNews.getAuthor());
        officialNewsEntity.setDescription(officialNews.getDescription());
        officialNewsEntity.setLink(officialNews.getLink());
        officialNewsEntity.setPublishedDate(officialNews.getPublishedDate());
        officialNewsEntity.setTitle(officialNews.getTitle());

        return officialNewsEntity;
    }

    public static OfficialNews entityToObject(OfficialNewsEntity officialNewsEntity) {
        OfficialNews officialNews = new OfficialNews();
        officialNews.setId(officialNewsEntity.getId());
        officialNews.setAuthor(officialNewsEntity.getAuthor());
        officialNews.setDescription(officialNewsEntity.getDescription());
        officialNews.setLink(officialNewsEntity.getLink());
        officialNews.setPublishedDate(officialNewsEntity.getPublishedDate());
        officialNews.setTitle(officialNewsEntity.getTitle());

        return officialNews;
    }
}
