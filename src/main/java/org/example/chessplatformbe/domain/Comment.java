package org.example.chessplatformbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Integer id;

    private String text;

    private Integer userId;

    private Integer articleId;
}
