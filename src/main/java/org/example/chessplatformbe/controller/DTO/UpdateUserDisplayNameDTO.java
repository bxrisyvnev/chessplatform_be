package org.example.chessplatformbe.controller.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDisplayNameDTO {

    private Long id;

    @NotBlank(message = "Display name is required")
    @Size(min = 4, max = 16, message = "Display name must be between 4 and 16 characters long")
    private String displayName;
}
