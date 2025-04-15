package org.example.chessplatformbe.controller.DTO.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
public class SpectatorResponseDTO extends UserResponseDTO  {

    private int playerElo;

    private boolean isChatBanned;

    private boolean isGameBanned;

    private int noOfGamesPlayed;

    private String chroma;

    private boolean hasPass;
}
