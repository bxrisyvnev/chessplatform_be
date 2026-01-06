package org.example.chessplatformbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommentControllerIntegrationTest {
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

        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, String> loginResponse = objectMapper.readValue(
                loginResult.getResponse().getContentAsString(), Map.class
        );
        jwtToken = loginResponse.get("accessToken");
        assertThat(jwtToken).isNotBlank();
    }

    @Test
    void createComment_shouldReturnSuccess() throws Exception {
        CreateCommentDTO comment = new CreateCommentDTO();

        comment.setUserId(1);
        comment.setText("This is a body of the test text.");
        comment.setArticleId(1);
        comment.setUpdateId(1);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk());
    }

    @Test
    void createComment_shouldReturnBadRequest() throws Exception {
        CreateCommentDTO comment = new CreateCommentDTO();

        comment.setUserId(1);
        comment.setText("");
        comment.setArticleId(1);
        comment.setUpdateId(1);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createComment_shouldReturnForbidenNoToken() throws Exception {
        CreateCommentDTO comment = new CreateCommentDTO();

        comment.setUserId(1);
        comment.setText("");
        comment.setArticleId(1);
        comment.setUpdateId(1);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getComments_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/comments/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void getComments_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteComment_shouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/api/comments/2")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment_shouldReturnUnauthorised() throws Exception {
        mockMvc.perform(delete("/api/comments/3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateComment_shouldReturnBadRequest() throws Exception {
        CreateCommentDTO comment = new CreateCommentDTO();

        comment.setUserId(1);
        comment.setText("");
        comment.setArticleId(1);
        comment.setUpdateId(1);

        mockMvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateComment_shouldReturnUnauthorised() throws Exception {
        CreateCommentDTO comment = new CreateCommentDTO();

        comment.setUserId(1);
        comment.setText("update");
        comment.setArticleId(1);
        comment.setUpdateId(2);

        mockMvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isUnauthorized());
    }
}
