package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOfficialNewsService {

    List<OfficialNews> getLatestFive() throws Exception;

    List<OfficialNews> createOfficialNews(List<CreateOfficialNewsDTO> dtos);

    Page<OfficialNews> getOfficialNewsPage(Integer page, Integer size);
}
