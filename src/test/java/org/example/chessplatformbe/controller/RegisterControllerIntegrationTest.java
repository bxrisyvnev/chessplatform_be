package org.example.chessplatformbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chessplatformbe.controller.dto.request.CreateSpectatorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegisterControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_shouldReturnSuccess() throws Exception {
        CreateSpectatorDTO user = new CreateSpectatorDTO();

        user.setUpdateId(1);
        user.setUsername("BorisNotCool111111");
        user.setPassword("pass123456");
        user.setAge(35);
        user.setDisplayName("Test");
        user.setNationality("Norway");
        user.setPlayerElo(1500);
        user.setChatBanned(false);
        user.setGameBanned(false);
        user.setNoOfGamesPlayed(25);
        user.setHasPass(true);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_shouldReturnBadRequest() throws Exception {
        CreateSpectatorDTO user = new CreateSpectatorDTO();

        user.setUpdateId(1);
        user.setUsername("");
        user.setPassword("");
        user.setAge(35);
        user.setDisplayName("Test");
        user.setNationality("Norway");
        user.setPlayerElo(1500);
        user.setChatBanned(false);
        user.setGameBanned(false);
        user.setNoOfGamesPlayed(25);
        user.setHasPass(true);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}
