package org.example.chessplatformbe.business.impl;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IOfficialNewsService;
import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
import org.example.chessplatformbe.domain.OfficialNews;
import org.example.chessplatformbe.persistence.OfficialNewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficialNewsServiceImpl implements IOfficialNewsService {

    private static final String NEWS_RSS = "https://www.chess.com/rss/news";

    private final OfficialNewsRepository officialNewsRepository;

    /**
     * Fetches the Chess.com news RSS feed, parses it,
     * and returns up to five of the most recent items.
     */
    @Override
    public List<OfficialNews> getLatestFive() throws Exception {
        SyndFeedInput input = new SyndFeedInput();
        try (XmlReader reader = new XmlReader(new URL(NEWS_RSS))) {
            SyndFeed feed = input.build(reader);
            return feed.getEntries().stream()
                    .limit(5)
                    .map(this::toOfficialNews)
                    .toList();
        }
    }

    private OfficialNews toOfficialNews(SyndEntry entry) {
        OfficialNews n = new OfficialNews();
        n.setTitle(entry.getTitle());
        n.setLink(entry.getLink());
        n.setDescription(entry.getDescription() != null
                ? entry.getDescription().getValue()
                : "");
        n.setAuthor(entry.getAuthor());
        // publishedDate may be null; guard for that
        if (entry.getPublishedDate() != null) {
            Instant inst = entry.getPublishedDate().toInstant();
            n.setPublishedDate(
                    LocalDateTime.ofInstant(inst, ZoneOffset.UTC)
            );
        }
        return n;
    }

    @Override
    public List<OfficialNews> createOfficialNews(List<CreateOfficialNewsDTO> dtos) {
        List<OfficialNews> toSave = dtos.stream().map(dto -> {
            OfficialNews n = new OfficialNews();
            n.setTitle(dto.getTitle());
            n.setLink(dto.getLink());
            n.setDescription(dto.getDescription());
            n.setAuthor(dto.getAuthor());
            try {
                n.setPublishedDate(LocalDateTime.parse(dto.getPublishedDate()));
            } catch (Exception e) {
                n.setPublishedDate(LocalDateTime.now());
            }
            return n;
        }).toList();

        return officialNewsRepository.save(toSave);
    }

    @Override
    public Page<OfficialNews> getOfficialNewsPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return officialNewsRepository.getOfficialNewsPage(pageable);
    }
}
