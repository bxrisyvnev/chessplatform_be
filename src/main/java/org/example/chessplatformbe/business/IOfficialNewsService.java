package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
import org.example.chessplatformbe.domain.OfficialNews;

import java.util.List;
import java.util.Map;

public interface IOfficialNewsService {

    List<OfficialNews> getLatestFive() throws Exception;

    List<OfficialNews> createOfficialNews(List<CreateOfficialNewsDTO> dtos);

    Map<String, Object> getOfficialNewsPage(Integer page, Integer size);
}
