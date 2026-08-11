package com.example.resume_builder.service;
import org.springframework.stereotype.Service;

import com.example.resume_builder.model.RetrievedChunk;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;

@Service
// @RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final RetrievalService retrievalService;
    private final PromptService promptService;

    public ChatService(
            ChatClient.Builder chatClientBuilder,
            RetrievalService retrievalService,
            PromptService promptService
    ) {
        this.chatClient = chatClientBuilder.build();
        this.retrievalService = retrievalService;
        this.promptService = promptService;
    }


    public String ask(String question) {

        List<RetrievedChunk> chunks =
                retrievalService.retrieve(question, 3);

        String prompt =
                promptService.buildPrompt(question, chunks);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}