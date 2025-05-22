package org.example.chessplatformbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Integer id;

    private String articleTitle;

    private String imageUrl;

    private Integer authorId;

    private String contentText;

    private List<Integer> commentsIds = new ArrayList<>();
}
