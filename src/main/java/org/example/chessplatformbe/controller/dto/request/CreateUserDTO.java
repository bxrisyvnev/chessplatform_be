package org.example.chessplatformbe.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

    private String username;

    private String password;

    private Integer age;

    private String displayName;

    private String nationality;
}
