package org.example.chessplatformbe.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentDTO {
    @NotNull(message = "Comment ID is required")
    private Integer commentId;
}
