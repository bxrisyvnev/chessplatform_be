package org.example.chessplatformbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chessplatformbe.controller.dto.request.CreateOfficialNewsDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OfficialNewsControllerIntegrationTest {

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
    void createOfficialNews_shouldReturnSuccess() throws Exception {
        CreateOfficialNewsDTO officialNews = new CreateOfficialNewsDTO();

        officialNews.setAuthor("idk");
        officialNews.setLink("someline");
        officialNews.setDescription("description");
        officialNews.setTitle("Title");
        officialNews.setPublishedDate("2025-06-10 15:00:00.000000");
        officialNews.setUpdateId(1);

        List<CreateOfficialNewsDTO> officialNewsList = new ArrayList<>();
        officialNewsList.add(officialNews);

        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(officialNewsList)))
                .andExpect(status().isOk());
    }

    @Test
    void createOfficialNews_shouldReturnBadRequest() throws Exception {
        CreateOfficialNewsDTO officialNews = new CreateOfficialNewsDTO();

        officialNews.setAuthor("idk");
        officialNews.setLink("someline");
        officialNews.setDescription("");
        officialNews.setTitle("");
        officialNews.setPublishedDate("2025-06-10 15:00:00.000000");
        officialNews.setUpdateId(1);

        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(officialNews)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOfficialNews_shouldReturnUnauthorized() throws Exception {
        CreateOfficialNewsDTO officialNews = new CreateOfficialNewsDTO();

        officialNews.setAuthor("idk");
        officialNews.setLink("someline");
        officialNews.setDescription("");
        officialNews.setTitle("");
        officialNews.setPublishedDate("2025-06-10 15:00:00.000000");
        officialNews.setUpdateId(1);

        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(officialNews)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getOfficialNews_shouldReturnList() throws Exception {
        mockMvc.perform(get("/news?page=0&size=5")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void getOfficialNews_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(get("/news?page=0&size=5"))
                .andExpect(status().isUnauthorized());
    }
}
