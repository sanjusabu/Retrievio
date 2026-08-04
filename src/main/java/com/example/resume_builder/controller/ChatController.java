package com.example.resume_builder.controller;

import com.example.resume_builder.dto.ChatRequest;
import com.example.resume_builder.dto.ChatResponse;
import com.example.resume_builder.entity.ChunkEntity;
import com.example.resume_builder.service.ChatService;
import com.example.resume_builder.service.RetrievalService;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final RetrievalService retrievedService;


    public ChatController(ChatService chatService, RetrievalService retrievalService) {
        this.chatService = chatService;
        this.retrievedService = retrievalService;
    }

    // @PostMapping
    // public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
    //     return chatService.chat(chatRequest);
    // }

    @PostMapping("/retrieve")
    public List<ChatResponse> retrieve(@RequestBody ChatRequest chatRequest) {
        return retrievedService.retrieve(chatRequest.getRequest(), 3);
    }
}