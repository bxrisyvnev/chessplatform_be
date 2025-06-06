package org.example.chessplatformbe.controller.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpectatorProfileDTO extends UserProfileDTO{
    private int playerElo;
    private boolean isChatBanned;
    private boolean isGameBanned;
    private int noOfGamesPlayed;
    private String chroma;
    private boolean hasPass;
}
