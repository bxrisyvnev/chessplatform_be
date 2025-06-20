package org.example.chessplatformbe.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ReportResponceDTO {
    private Integer id;

    private String description;

    private String type;

    private LocalDateTime dateTime;

    private Integer userId;
}
