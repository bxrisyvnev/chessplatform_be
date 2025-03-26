package org.example.chessplatformbe.controller.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserNationalityDTO {

    private Long id;

    @NotBlank(message = "Which country are you from?")
    private String nationality;
}
