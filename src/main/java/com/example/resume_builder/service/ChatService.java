package com.example.resume_builder.service;
import org.springframework.stereotype.Service;

import com.example.resume_builder.dto.ChatRequest;
import com.example.resume_builder.dto.ChatResponse;

import org.springframework.ai.chat.client.ChatClient;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public ChatResponse chat(ChatRequest chatRequest) {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setResponse(chatClient
                .prompt()
                .user(chatRequest.getRequest())
                .call()
                .content());
        return chatResponse;
    }
}