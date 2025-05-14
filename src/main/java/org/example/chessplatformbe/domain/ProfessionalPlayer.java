package org.example.chessplatformbe.domain;

import lombok.*;
import org.example.chessplatformbe.enums.Chroma;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalPlayer extends User {

    private Integer playerElo;

    private double winRate;

    private Integer noOfGamesPlayed;

    private Integer followerCount;

    private Chroma chroma;

}
