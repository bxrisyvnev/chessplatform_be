package org.example.chessplatformbe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.chessplatformbe.enums.Chroma;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpectatorPlayer extends User {

    @Column(nullable = false)
    private int playerElo;

    @Column(nullable = false)
    private boolean isChatBanned;

    @Column(nullable = false)
    private boolean isGameBanned;

    @Column(nullable = false)
    private int noOfGamesPlayed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Chroma chroma;

    @Column(nullable = false)
    private boolean hasPass;
}
