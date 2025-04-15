package org.example.chessplatformbe.controller.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserResponseDTO {

    private Integer id;

    private Integer age;

    private String username;

    private String displayName;

    private String nationality;
}
