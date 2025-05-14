package org.example.chessplatformbe.domain;

import lombok.*;
import org.example.chessplatformbe.enums.Chroma;

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

    private Chroma chroma;

    private boolean hasPass;
}
