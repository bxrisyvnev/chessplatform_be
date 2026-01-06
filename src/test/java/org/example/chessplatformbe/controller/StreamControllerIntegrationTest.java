package org.example.chessplatformbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chessplatformbe.controller.dto.request.CreateStreamDTO;
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

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StreamControllerIntegrationTest {

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
    void createStream_shouldReturnSuccess() throws Exception {
        CreateStreamDTO stream = new CreateStreamDTO();

        stream.setStreamUrl("some url");
        stream.setName("Name");
        stream.setStreamerId(1);
        stream.setUpdateId(1);
        stream.setCreationDateTime(LocalDateTime.parse("2025-05-26T23:15:42"));

        mockMvc.perform(post("/streams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(stream)))
                .andExpect(status().isOk());
    }

    @Test
    void createStream_shouldReturnBadRequest() throws Exception {
        CreateStreamDTO stream = new CreateStreamDTO();

        stream.setStreamUrl("");
        stream.setName("");
        stream.setStreamerId(1);
        stream.setUpdateId(1);
        stream.setCreationDateTime(LocalDateTime.parse("2025-05-26T23:15:42"));

        mockMvc.perform(post("/streams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(stream)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createStream_shouldReturnForbidenNoToken() throws Exception {
        CreateStreamDTO stream = new CreateStreamDTO();

        stream.setStreamUrl("some url");
        stream.setName("Name");
        stream.setStreamerId(1);
        stream.setUpdateId(1);
        stream.setCreationDateTime(LocalDateTime.parse("2025-05-26T23:15:42"));


        mockMvc.perform(post("/streams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stream)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getStream_shouldReturnList() throws Exception {
        mockMvc.perform(get("/streams/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void getStream_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(get("/streams/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteStream_shouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/streams/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStream_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(delete("/streams/2"))
                .andExpect(status().isUnauthorized());
    }
}
