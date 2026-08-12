package com.example.retrievio.controller;

import com.example.retrievio.constants.Constants;
import com.example.retrievio.dto.ChatRequest;
import com.example.retrievio.model.RetrievedChunk;
import com.example.retrievio.service.ChatService;
import com.example.retrievio.service.RetrievalService;

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

    @PostMapping("/retrieve")
    public List<RetrievedChunk> retrieve(@RequestBody ChatRequest chatRequest) {
        return retrievedService.retrieve(chatRequest.getRequest(), Constants.TOPK);
    }
    
    @PostMapping("/ask")
    public String ask(@RequestBody ChatRequest chatRequest) {
        return chatService.ask(chatRequest);
    }
}