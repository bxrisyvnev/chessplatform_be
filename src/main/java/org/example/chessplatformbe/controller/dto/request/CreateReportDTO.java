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
public class CreateReportDTO {
    private Integer updateId;

    private String description;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Date is required")
    private LocalDateTime dateTime;

    @NotNull(message = "User is required")
    private Integer userId;
}

