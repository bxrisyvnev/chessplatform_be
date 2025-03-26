package org.example.chessplatformbe.controller.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserAgeDTO {

    private Long id;

    private int age;
}
