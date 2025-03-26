package org.example.chessplatformbe.controller.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private int age;

    @NotBlank(message = "Display name is required")
    @Size(min = 4, max = 16, message = "Display name must be between 4 and 16 characters long")
    private String displayName;

    @NotBlank(message = "Which country are you from?")
    private String nationality;
}
