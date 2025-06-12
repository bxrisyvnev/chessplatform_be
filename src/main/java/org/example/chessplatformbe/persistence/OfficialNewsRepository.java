package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.OfficialNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfficialNewsRepository {

    List<OfficialNews> save(List<OfficialNews> officialNews);

    Page<OfficialNews> getOfficialNewsPage(Pageable pageable);
}
