package org.example.chessplatformbe.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OfficialNews {

    private Integer id;

    private String title;

    private String link;

    private LocalDateTime publishedDate;

    private String description;

    private String author;
}
