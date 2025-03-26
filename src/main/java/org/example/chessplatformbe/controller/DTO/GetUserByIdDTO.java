package org.example.chessplatformbe.controller.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserByIdDTO {

    private int id;
}
