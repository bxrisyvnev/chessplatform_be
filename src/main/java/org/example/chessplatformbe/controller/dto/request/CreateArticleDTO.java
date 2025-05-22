package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateArticleDTO {
    private Integer updateId;

    private String articleTitle;

    private String imageUrl;

    private Integer authorId;

    private String contentText;

    private List<Integer> commentsIds;
}
