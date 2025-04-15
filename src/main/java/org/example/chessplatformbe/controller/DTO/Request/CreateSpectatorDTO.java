package org.example.chessplatformbe.controller.DTO.Request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateSpectatorDTO extends CreateUserDTO {

    private int playerElo;

    private boolean isChatBanned;

    private boolean isGameBanned;

    private int noOfGamesPlayed;

    private String chroma;

    private boolean hasPass;
}