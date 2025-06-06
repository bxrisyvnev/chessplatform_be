package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.dto.request.CreateAdminDTO;
import org.example.chessplatformbe.controller.dto.request.CreateProfessionalDTO;
import org.example.chessplatformbe.controller.dto.request.CreateSpectatorDTO;
import org.example.chessplatformbe.controller.dto.request.CreateUserDTO;
import org.example.chessplatformbe.controller.dto.response.*;
import org.example.chessplatformbe.domain.*;
import org.example.chessplatformbe.enums.Chroma;
import org.example.chessplatformbe.persistence.impl.jpa.entity.AdminEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ProfessionalPlayerEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.SpectatorPlayerEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private UserMapper() {}

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
            proPlayer.setChroma(Chroma.valueOf(professionalDTO.getChroma().toString().toUpperCase()));

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
            specPlayer.setChroma(Chroma.valueOf(spectatorDTO.getChroma().toString().toUpperCase()));
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
            professionalResponseDTO.setChroma(professional.getChroma().toString().toUpperCase());

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
            spectatorResponseDTO.setChroma(spectator.getChroma().toString().toUpperCase());

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
            adminObject.setPassword(admin.getPassword());
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
            professionalObject.setPassword(professional.getPassword());
            professionalObject.setId(professional.getId());
            professionalObject.setDisplayName(professional.getDisplayName());
            professionalObject.setNationality(professional.getNationality());
            professionalObject.setPlayerElo(professional.getPlayerElo());
            professionalObject.setWinRate(professional.getWinRate());
            professionalObject.setNoOfGamesPlayed(professional.getNoOfGamesPlayed());
            professionalObject.setFollowerCount(professional.getFollowerCount());
            professionalObject.setChroma(Chroma.valueOf(professional.getChroma().toString().toUpperCase()));

            return professionalObject;
        }

        else if (userEntity instanceof SpectatorPlayerEntity spectator) {

            SpectatorPlayer spectatorObject = new SpectatorPlayer();
            spectatorObject.setUsername(spectator.getUsername());
            spectatorObject.setAge(spectator.getAge());
            spectatorObject.setPassword(spectator.getPassword());
            spectatorObject.setId(spectator.getId());
            spectatorObject.setDisplayName(spectator.getDisplayName());
            spectatorObject.setNationality(spectator.getNationality());
            spectatorObject.setPlayerElo(spectator.getPlayerElo());
            spectatorObject.setChatBanned(spectator.isChatBanned());
            spectatorObject.setGameBanned(spectator.isGameBanned());
            spectatorObject.setHasPass(spectator.isHasPass());
            spectatorObject.setNoOfGamesPlayed(spectator.getNoOfGamesPlayed());
            spectatorObject.setChroma(Chroma.valueOf(spectator.getChroma().toString().toUpperCase()));

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
            professionalEntity.setChroma(Chroma.valueOf(professional.getChroma().toString().toUpperCase()));

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
            spectatorEntity.setChroma(Chroma.valueOf(spectator.getChroma().toString().toUpperCase()));

            return spectatorEntity;
        }

        throw new IllegalArgumentException("Unknown user type");
    }

    public static UserProfileDTO userToProfileDTO(User user, List<Comment> comments) {
        List<CommentResponceDTO> commentDTOs = comments.stream()
                .map(CommentMapper::objectToResponse)
                .toList();

        if (user instanceof Admin admin) {
            AdminProfileDTO dto = new AdminProfileDTO();
            dto.setUsername(admin.getUsername());
            dto.setAge(admin.getAge());
            dto.setDisplayName(admin.getDisplayName());
            dto.setNationality(admin.getNationality());
            dto.setMonthlySalary(admin.getMonthlySalary());
            dto.setContractStartDate(admin.getContractStartDate().toString());
            dto.setContractEndDate(admin.getContractEndDate().toString());
            dto.setAddress(admin.getAddress());
            dto.setComments(commentDTOs);
            return dto;
        }

        if (user instanceof ProfessionalPlayer p) {
            ProfessionalProfileDTO dto = new ProfessionalProfileDTO();
            dto.setUsername(p.getUsername());
            dto.setAge(p.getAge());
            dto.setDisplayName(p.getDisplayName());
            dto.setNationality(p.getNationality());
            dto.setPlayerElo(p.getPlayerElo());
            dto.setWinRate(p.getWinRate());
            dto.setNoOfGamesPlayed(p.getNoOfGamesPlayed());
            dto.setFollowerCount(p.getFollowerCount());
            dto.setChroma(p.getChroma().toString());
            dto.setComments(commentDTOs);
            return dto;
        }

        if (user instanceof SpectatorPlayer s) {
            SpectatorProfileDTO dto = new SpectatorProfileDTO();
            dto.setUsername(s.getUsername());
            dto.setAge(s.getAge());
            dto.setDisplayName(s.getDisplayName());
            dto.setNationality(s.getNationality());
            dto.setPlayerElo(s.getPlayerElo());
            dto.setChatBanned(s.isChatBanned());
            dto.setGameBanned(s.isGameBanned());
            dto.setNoOfGamesPlayed(s.getNoOfGamesPlayed());
            dto.setChroma(s.getChroma().toString());
            dto.setHasPass(s.isHasPass());
            dto.setComments(commentDTOs);
            return dto;
        }

        throw new IllegalArgumentException("Invalid user type for profile mapping");
    }
}
