package org.example.chessplatformbe.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessionalPlayer extends User {
    private int playerElo;
    private double winRate;
    private int noOfGamesPlayed;
    private int followerCount;
    private String chroma;

    public ProfessionalPlayer(String username, String password, int age, String displayName, String nationality,
                              int playerElo, double winRate, int noOfGamesPlayed, int followerCount, String chroma) {
        super(username, password, age, displayName, nationality);
        this.playerElo = playerElo;
        this.winRate = winRate;
        this.noOfGamesPlayed = noOfGamesPlayed;
        this.followerCount = followerCount;
        this.chroma = chroma;
    }
}
