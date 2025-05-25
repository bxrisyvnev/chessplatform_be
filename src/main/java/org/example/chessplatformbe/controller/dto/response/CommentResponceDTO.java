package org.example.chessplatformbe.controller.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponceDTO {

    private Integer id;

    private String text;

    private Integer userId;

    private Integer articleId;
}
