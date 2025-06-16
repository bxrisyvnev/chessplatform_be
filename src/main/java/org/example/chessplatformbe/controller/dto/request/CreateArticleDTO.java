package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateArticleDTO {
    private Integer updateId;

    @NotBlank(message = "Article title should not be blank")
    private String articleTitle;

    private String imageUrl;

    @NotNull(message = "AuthorId is required")
    private Integer authorId;

    private String contentText;

    private List<Integer> commentsIds;
}
