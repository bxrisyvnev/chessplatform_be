package org.example.chessplatformbe.controller.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
public class ProfessionalResponseDTO extends UserResponseDTO {

    private Integer playerElo;

    private double winRate;

    private Integer noOfGamesPlayed;

    private Integer followerCount;
}
