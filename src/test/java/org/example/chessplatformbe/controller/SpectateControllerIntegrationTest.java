package org.example.chessplatformbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chessplatformbe.controller.dto.request.LoginRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SpectateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setup() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("BorisCool1");
        loginRequestDTO.setPassword("pass123");

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, String> loginResponse = objectMapper.readValue(
                loginResult.getResponse().getContentAsString(), Map.class
        );
        jwtToken = loginResponse.get("accessToken");
    }

    @Test
    void getStream_shouldReturnList() throws Exception {
        mockMvc.perform(get("/spectate?page=0&size=5")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void getStream_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/spectate?page=0&size=5"))
                .andExpect(status().isUnauthorized());
    }
}
