package org.example.chessplatformbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerIntegrationTest {

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
    void createArticle_shouldReturnSuccess() throws Exception {
        CreateArticleDTO article = new CreateArticleDTO();

        article.setArticleTitle("My Test Article");
        article.setContentText("This is a body of the test article.");
        article.setImageUrl("http://example.com/image.jpg");
        article.setUpdateId(1);
        article.setAuthorId(1);

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk());
    }

    @Test
    void createArticle_shouldReturnBadRequest() throws Exception {
        CreateArticleDTO article = new CreateArticleDTO();

        article.setArticleTitle("");
        article.setContentText("This is a body of the test article.");
        article.setImageUrl("http://example.com/image.jpg");
        article.setUpdateId(1);
        article.setAuthorId(1);

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createArticle_shouldReturnForbidenNoToken() throws Exception {
        CreateArticleDTO article = new CreateArticleDTO();

        article.setArticleTitle("");
        article.setContentText("This is a body of the test article.");
        article.setImageUrl("http://example.com/image.jpg");
        article.setUpdateId(1);
        article.setAuthorId(1);

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getArticles_shouldReturnList() throws Exception {
        mockMvc.perform(get("/articles?page=0&size=5")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray());
    }

    @Test
    void getArticles_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(get("/articles?page=0&size=5"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteArticle_shouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/articles/2")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void deleteArticle_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(delete("/articles/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateArticle_shouldReturnSuccess() throws Exception {
        CreateArticleDTO article = new CreateArticleDTO();

        article.setArticleTitle("Something else");
        article.setContentText("This is a body of the updated test article.");
        article.setImageUrl("http://example.com/image.jpg");
        article.setUpdateId(1);
        article.setAuthorId(1);

        mockMvc.perform(put("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk());
    }

    @Test
    void updateArticle_shouldReturnBadRequest() throws Exception {
        CreateArticleDTO article = new CreateArticleDTO();

        article.setArticleTitle("");
        article.setContentText("This is a body of the updated test article.");
        article.setImageUrl("http://example.com/image.jpg");
        article.setUpdateId(1);
        article.setAuthorId(1);

        mockMvc.perform(put("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateArticle_shouldReturnUnauthorised() throws Exception {
        CreateArticleDTO article = new CreateArticleDTO();

        article.setArticleTitle("");
        article.setContentText("This is a body of the updated test article.");
        article.setImageUrl("http://example.com/image.jpg");
        article.setUpdateId(1);
        article.setAuthorId(1);

        mockMvc.perform(put("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isUnauthorized());
    }
}
