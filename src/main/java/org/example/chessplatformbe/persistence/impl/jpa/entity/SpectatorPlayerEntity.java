package org.example.chessplatformbe.persistence.impl.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpectatorPlayerEntity extends UserEntity {

    @Column(nullable = false)
    private int playerElo;

    @Column(nullable = false)
    private boolean isChatBanned;

    @Column(nullable = false)
    private boolean isGameBanned;

    @Column(nullable = false)
    private int noOfGamesPlayed;

    @Column(nullable = false)
    private boolean hasPass;
}
