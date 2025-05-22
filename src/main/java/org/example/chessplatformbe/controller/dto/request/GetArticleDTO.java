package org.example.chessplatformbe.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetArticleDTO {
    @NotNull(message = "Article ID is required")
    private Integer articleId;
}
