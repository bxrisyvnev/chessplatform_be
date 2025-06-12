package org.example.chessplatformbe.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficialNewsResponceDTO {
    private Integer id;

    private String title;

    private String link;

    private String publishedDate;

    private String description;

    private String author;
}
