package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOfficialNewsDTO {
    private Integer updateId;

    private String title;

    private String link;

    private String publishedDate;

    private String description;

    private String author;
}
