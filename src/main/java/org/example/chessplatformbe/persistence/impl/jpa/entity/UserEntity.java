package org.example.chessplatformbe.persistence.impl.jpa.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "roles"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminEntity.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = SpectatorPlayerEntity.class, name = "SPECTATOR_PLAYER"),
        @JsonSubTypes.Type(value = ProfessionalPlayerEntity.class, name = "PROFESSIONAL_PLAYER")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String nationality;
}
