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
import org.example.chessplatformbe.persistence.impl.jpa.entity.AdminEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ProfessionalPlayerEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.SpectatorPlayerEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User requestToObject(CreateUserDTO dto) {
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

    public static UserResponseDTO objectToResponce(User user) {
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

    public static User entityToObject(UserEntity userEntity) {
        if (userEntity instanceof AdminEntity admin) {

            Admin adminObject = new Admin();
            adminObject.setUsername(admin.getUsername());
            adminObject.setAge(admin.getAge());
            adminObject.setId(admin.getId());
            adminObject.setDisplayName(admin.getDisplayName());
            adminObject.setNationality(admin.getNationality());
            adminObject.setMonthlySalary(admin.getMonthlySalary());
            adminObject.setAddress(admin.getAddress());
            adminObject.setContractStartDate(admin.getContractStartDate());
            adminObject.setContractEndDate(admin.getContractEndDate());

            return adminObject;
        }

        else if (userEntity instanceof ProfessionalPlayerEntity professional) {

            ProfessionalPlayer professionalObject = new ProfessionalPlayer();
            professionalObject.setUsername(professional.getUsername());
            professionalObject.setAge(professional.getAge());
            professionalObject.setId(professional.getId());
            professionalObject.setDisplayName(professional.getDisplayName());
            professionalObject.setNationality(professional.getNationality());
            professionalObject.setPlayerElo(professional.getPlayerElo());
            professionalObject.setWinRate(professional.getWinRate());
            professionalObject.setNoOfGamesPlayed(professional.getNoOfGamesPlayed());
            professionalObject.setFollowerCount(professional.getFollowerCount());
            professionalObject.setChroma(professional.getChroma());

            return professionalObject;
        }

        else if (userEntity instanceof SpectatorPlayerEntity spectator) {

            SpectatorPlayer spectatorObject = new SpectatorPlayer();
            spectatorObject.setUsername(spectator.getUsername());
            spectatorObject.setAge(spectator.getAge());
            spectatorObject.setId(spectator.getId());
            spectatorObject.setDisplayName(spectator.getDisplayName());
            spectatorObject.setNationality(spectator.getNationality());
            spectatorObject.setPlayerElo(spectator.getPlayerElo());
            spectatorObject.setChatBanned(spectator.isChatBanned());
            spectatorObject.setGameBanned(spectator.isGameBanned());
            spectatorObject.setHasPass(spectator.isHasPass());
            spectatorObject.setNoOfGamesPlayed(spectator.getNoOfGamesPlayed());
            spectatorObject.setChroma(spectator.getChroma());

            return spectatorObject;
        }

        throw new IllegalArgumentException("Unknown user type");
    }

    public static UserEntity objectToEntity(User user) {
        if (user instanceof Admin admin) {

            AdminEntity adminEntity = new AdminEntity();
            adminEntity.setUsername(admin.getUsername());
            adminEntity.setAge(admin.getAge());
            adminEntity.setId(admin.getId());
            adminEntity.setPassword(user.getPassword());
            adminEntity.setDisplayName(admin.getDisplayName());
            adminEntity.setNationality(admin.getNationality());
            adminEntity.setMonthlySalary(admin.getMonthlySalary());
            adminEntity.setAddress(admin.getAddress());
            adminEntity.setContractStartDate(admin.getContractStartDate());
            adminEntity.setContractEndDate(admin.getContractEndDate());

            return adminEntity;
        }

        else if (user instanceof ProfessionalPlayer professional) {

            ProfessionalPlayerEntity professionalEntity = new ProfessionalPlayerEntity();
            professionalEntity.setUsername(professional.getUsername());
            professionalEntity.setAge(professional.getAge());
            professionalEntity.setId(professional.getId());
            professionalEntity.setDisplayName(professional.getDisplayName());
            professionalEntity.setNationality(professional.getNationality());
            professionalEntity.setPlayerElo(professional.getPlayerElo());
            professionalEntity.setWinRate(professional.getWinRate());
            professionalEntity.setPassword(user.getPassword());
            professionalEntity.setNoOfGamesPlayed(professional.getNoOfGamesPlayed());
            professionalEntity.setFollowerCount(professional.getFollowerCount());
            professionalEntity.setChroma(professional.getChroma());

            return professionalEntity;
        }

        else if (user instanceof SpectatorPlayer spectator) {

            SpectatorPlayerEntity spectatorEntity = new SpectatorPlayerEntity();
            spectatorEntity.setUsername(spectator.getUsername());
            spectatorEntity.setAge(spectator.getAge());
            spectatorEntity.setId(spectator.getId());
            spectatorEntity.setDisplayName(spectator.getDisplayName());
            spectatorEntity.setNationality(spectator.getNationality());
            spectatorEntity.setPlayerElo(spectator.getPlayerElo());
            spectatorEntity.setPassword(user.getPassword());
            spectatorEntity.setChatBanned(spectator.isChatBanned());
            spectatorEntity.setGameBanned(spectator.isGameBanned());
            spectatorEntity.setHasPass(spectator.isHasPass());
            spectatorEntity.setNoOfGamesPlayed(spectator.getNoOfGamesPlayed());
            spectatorEntity.setChroma(spectator.getChroma());

            return spectatorEntity;
        }

        throw new IllegalArgumentException("Unknown user type");
    }
}
