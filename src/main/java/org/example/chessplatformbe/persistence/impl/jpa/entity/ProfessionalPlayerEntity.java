package org.example.chessplatformbe.persistence.impl.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.chessplatformbe.enums.Chroma;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalPlayerEntity extends UserEntity {

    @Column(nullable = false)
    private Integer playerElo;

    @Column(nullable = false)
    private double winRate;

    @Column(nullable = false)
    private Integer noOfGamesPlayed;

    @Column(nullable = false)
    private Integer followerCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Chroma chroma;

}
