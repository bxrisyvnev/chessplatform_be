package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.DTO.Request.CreateAdminDTO;
import org.example.chessplatformbe.controller.DTO.Request.CreateProfessionalDTO;
import org.example.chessplatformbe.controller.DTO.Request.CreateSpectatorDTO;
import org.example.chessplatformbe.controller.DTO.Request.CreateUserDTO;
import org.example.chessplatformbe.controller.DTO.Response.AdminResponseDTO;
import org.example.chessplatformbe.controller.DTO.Response.ProfessionalResponseDTO;
import org.example.chessplatformbe.controller.DTO.Response.SpectatorResponseDTO;
import org.example.chessplatformbe.controller.DTO.Response.UserResponseDTO;
import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.ProfessionalPlayer;
import org.example.chessplatformbe.domain.SpectatorPlayer;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.enums.Chroma;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserDTO dto) {
        if (dto instanceof CreateAdminDTO adminDto) {
            Admin admin = new Admin();
            admin.setUsername(adminDto.getUsername());
            admin.setPassword(adminDto.getPassword());
            admin.setAge(adminDto.getAge());
            admin.setDisplayName(adminDto.getDisplayName());
            admin.setNationality(adminDto.getNationality());
            admin.setMonthlySalary(adminDto.getMonthlySalary());
            admin.setAddress(adminDto.getAddress());
            admin.setContractEndDate(adminDto.getContractEndDate());
            admin.setContractStartDate(adminDto.getContractStartDate());

            return admin;
        }
        else if (dto instanceof CreateProfessionalDTO professionalDTO) {
            ProfessionalPlayer proPlayer = new ProfessionalPlayer();
            proPlayer.setUsername(professionalDTO.getUsername());
            proPlayer.setPassword(professionalDTO.getPassword());
            proPlayer.setAge(professionalDTO.getAge());
            proPlayer.setDisplayName(professionalDTO.getDisplayName());
            proPlayer.setNationality(professionalDTO.getNationality());
            proPlayer.setPlayerElo(professionalDTO.getPlayerElo());
            proPlayer.setWinRate(professionalDTO.getWinRate());
            proPlayer.setNoOfGamesPlayed(professionalDTO.getNoOfGamesPlayed());
            proPlayer.setFollowerCount(professionalDTO.getFollowerCount());
            proPlayer.setChroma(Chroma.valueOf(professionalDTO.getChroma()));

            return proPlayer;
        }

        else if (dto instanceof CreateSpectatorDTO spectatorDTO) {
            SpectatorPlayer specPlayer = new SpectatorPlayer();
            specPlayer.setUsername(spectatorDTO.getUsername());
            specPlayer.setPassword(spectatorDTO.getPassword());
            specPlayer.setAge(spectatorDTO.getAge());
            specPlayer.setDisplayName(spectatorDTO.getDisplayName());
            specPlayer.setNationality(spectatorDTO.getNationality());
            specPlayer.setPlayerElo(spectatorDTO.getPlayerElo());
            specPlayer.setChatBanned(spectatorDTO.isChatBanned());
            specPlayer.setGameBanned(spectatorDTO.isGameBanned());
            specPlayer.setNoOfGamesPlayed(spectatorDTO.getNoOfGamesPlayed());
            specPlayer.setChroma(Chroma.valueOf(spectatorDTO.getChroma()));
            specPlayer.setHasPass(spectatorDTO.isHasPass());

            return specPlayer;
        }
        throw new IllegalArgumentException("Unknown user type");
    }

    public UserResponseDTO toResponse(User user) {
        if (user instanceof Admin admin) {

            AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
            adminResponseDTO.setUsername(admin.getUsername());
            adminResponseDTO.setAge(admin.getAge());
            adminResponseDTO.setId(admin.getId());
            adminResponseDTO.setDisplayName(admin.getDisplayName());
            adminResponseDTO.setNationality(admin.getNationality());
            adminResponseDTO.setMonthlySalary(admin.getMonthlySalary());
            adminResponseDTO.setAddress(admin.getAddress());
            adminResponseDTO.setContractStartDate(String.valueOf(admin.getContractStartDate()));
            adminResponseDTO.setContractEndDate(String.valueOf(admin.getContractEndDate()));

            return adminResponseDTO;
        }

        else if (user instanceof ProfessionalPlayer professional) {

            ProfessionalResponseDTO professionalResponseDTO = new ProfessionalResponseDTO();
            professionalResponseDTO.setUsername(professional.getUsername());
            professionalResponseDTO.setAge(professional.getAge());
            professionalResponseDTO.setId(professional.getId());
            professionalResponseDTO.setDisplayName(professional.getDisplayName());
            professionalResponseDTO.setNationality(professional.getNationality());
            professionalResponseDTO.setPlayerElo(professional.getPlayerElo());
            professionalResponseDTO.setWinRate(professional.getWinRate());
            professionalResponseDTO.setNoOfGamesPlayed(professional.getNoOfGamesPlayed());
            professionalResponseDTO.setFollowerCount(professional.getFollowerCount());
            professionalResponseDTO.setChroma(professional.getChroma().toString());

            return professionalResponseDTO;
        }

        else if (user instanceof SpectatorPlayer spectator) {

            SpectatorResponseDTO spectatorResponseDTO = new SpectatorResponseDTO();
            spectatorResponseDTO.setUsername(spectator.getUsername());
            spectatorResponseDTO.setAge(spectator.getAge());
            spectatorResponseDTO.setId(spectator.getId());
            spectatorResponseDTO.setDisplayName(spectator.getDisplayName());
            spectatorResponseDTO.setNationality(spectator.getNationality());
            spectatorResponseDTO.setPlayerElo(spectator.getPlayerElo());
            spectatorResponseDTO.setChatBanned(spectator.isChatBanned());
            spectatorResponseDTO.setGameBanned(spectator.isGameBanned());
            spectatorResponseDTO.setHasPass(spectator.isHasPass());
            spectatorResponseDTO.setNoOfGamesPlayed(spectator.getNoOfGamesPlayed());
            spectatorResponseDTO.setChroma(spectator.getChroma().toString());

            return spectatorResponseDTO;
        }

        throw new IllegalArgumentException("Unknown user type");
    }
}
