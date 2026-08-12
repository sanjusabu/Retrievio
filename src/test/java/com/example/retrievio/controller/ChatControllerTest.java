package com.example.retrievio.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.retrievio.controller.ChatController;
import com.example.retrievio.dto.ChatResponse;
import com.example.retrievio.service.ChatService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @Test
    void shouldReturnChatResponseForPostRequest() throws Exception {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setResponse("Echo: Build your resume");

        mockMvc.perform(post("/api/v1/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"request\":\"Build your resume\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Echo: Build your resume"));
    }
}
