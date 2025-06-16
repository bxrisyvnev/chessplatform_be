package org.example.chessplatformbe.controller.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserProfileDTO {
    private String username;
    private Integer age;
    private String displayName;
    private String nationality;
    private List<CommentResponceDTO> comments;
    private Double averageCommentsPerArticle;
}
