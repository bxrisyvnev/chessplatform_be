package org.example.chessplatformbe.controller.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfessionalProfileDTO extends UserProfileDTO {
    private Integer playerElo;
    private double winRate;
    private Integer noOfGamesPlayed;
    private Integer followerCount;
    private String chroma;
}
