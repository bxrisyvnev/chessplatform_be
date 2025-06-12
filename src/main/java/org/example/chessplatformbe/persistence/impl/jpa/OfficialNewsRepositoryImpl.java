package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.mapper.OfficialNewsMapper;
import org.example.chessplatformbe.persistence.OfficialNewsRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.OfficialNewsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OfficialNewsRepositoryImpl implements OfficialNewsRepository {

    private final JpaOfficialNews jpaOfficialNews;

    @Autowired
    public OfficialNewsRepositoryImpl(JpaOfficialNews jpaOfficialNews) {
        this.jpaOfficialNews = jpaOfficialNews;
    }

    @Override
    public List<OfficialNews> save(List<OfficialNews> officialNewsList) {

        List<OfficialNews> officialNewsReturnList = new ArrayList<>();

        for (OfficialNews officialNews : officialNewsList) {
            OfficialNewsEntity officialNewsEntity = OfficialNewsMapper.objectToEntity(officialNews);
            OfficialNewsEntity saved = jpaOfficialNews.save(officialNewsEntity);
            officialNewsReturnList.add(OfficialNewsMapper.entityToObject(saved));
        }

        return officialNewsReturnList;
    }

    @Override
    public Page<OfficialNews> getOfficialNewsPage(Pageable pageable) {
        Page<OfficialNewsEntity> entityPage = jpaOfficialNews.findAll(pageable);

        List<OfficialNews> officialNews = entityPage.getContent()
                .stream()
                .map(OfficialNewsMapper::entityToObject)
                .toList();

        return new PageImpl<>(officialNews, pageable, entityPage.getTotalElements());
    }
}
