package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateStreamDTO {
    private Integer updateId;

    private String name;

    private LocalDateTime creationDateTime;

    private String streamUrl;

    private Integer streamerId;
}
