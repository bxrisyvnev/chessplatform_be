package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Name is required")
    private String name;

    private LocalDateTime creationDateTime;

    @NotBlank(message = "Stream Url is required")
    private String streamUrl;

    @NotNull(message = "Streamer id is required")
    private Integer streamerId;
}
