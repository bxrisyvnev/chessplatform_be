package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCommentDTO {

    private Integer updateId;

    private String text;

    private Integer userId;

    private Integer articleId;
}
