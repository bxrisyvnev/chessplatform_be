package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCommentDTO {

    private Integer updateId;

    @NotBlank(message = "Text is required")
    private String text;

    @NotNull(message = "User id is required")
    private Integer userId;

    @NotNull(message = "Article id is required")
    private Integer articleId;
}
