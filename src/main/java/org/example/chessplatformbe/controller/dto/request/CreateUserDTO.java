package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

//
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "roles"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateAdminDTO.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = CreateSpectatorDTO.class, name = "SPECTATOR_PLAYER"),
        @JsonSubTypes.Type(value = CreateProfessionalDTO.class, name = "PROFESSIONAL_PLAYER")
})
public abstract class CreateUserDTO {

    private Integer updateId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Age is required")
    @Positive(message = "You are not that young")
    private Integer age;

    @NotBlank(message = "Display Name is required")
    private String displayName;

    @NotBlank(message = "Nationality is required")
    private String nationality;
}
