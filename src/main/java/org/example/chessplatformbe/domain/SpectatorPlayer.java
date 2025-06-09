package org.example.chessplatformbe.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpectatorPlayer extends User {

    private int playerElo;

    private boolean isChatBanned;

    private boolean isGameBanned;

    private int noOfGamesPlayed;

    private boolean hasPass;
}
