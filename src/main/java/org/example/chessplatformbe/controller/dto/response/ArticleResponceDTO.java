package org.example.chessplatformbe.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponceDTO {

    private Integer id;

    private String articleTitle;

    private String imageUrl;

    private String authorName;

    private Integer authorId;

    private String contentText;

    private List<Integer> commentsIds = new ArrayList<>();
}
