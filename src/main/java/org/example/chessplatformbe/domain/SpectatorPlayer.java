package org.example.chessplatformbe.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpectatorPlayer extends User {
    private int playerElo;
    private boolean isChatBanned;
    private boolean isGameBanned;
    private int noOfGamesPlayed;
    private String chroma;
    private boolean hasPass;

    public SpectatorPlayer(String username, int age, String displayName, String nationality,
                           int playerElo, boolean isChatBanned, boolean isGameBanned, int noOfGamesPlayed, String chroma, boolean hasPass) {
        super(username, age, displayName, nationality);
        this.playerElo = playerElo;
        this.isChatBanned = isChatBanned;
        this.isGameBanned = isGameBanned;
        this.noOfGamesPlayed = noOfGamesPlayed;
        this.chroma = chroma;
        this.hasPass = hasPass;
    }
}
