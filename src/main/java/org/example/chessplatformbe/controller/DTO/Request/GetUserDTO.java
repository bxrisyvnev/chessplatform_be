package org.example.chessplatformbe.controller.DTO.Request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class GetUserDTO {

    @NotNull(message = "User ID is required")
    private Integer userId;
}
