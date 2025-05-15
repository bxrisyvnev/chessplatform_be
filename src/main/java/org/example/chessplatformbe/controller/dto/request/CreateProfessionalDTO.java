package org.example.chessplatformbe.controller.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateProfessionalDTO extends CreateUserDTO {

    private Integer playerElo;

    private double winRate;

    private Integer noOfGamesPlayed;

    private Integer followerCount;

    private String chroma;
}