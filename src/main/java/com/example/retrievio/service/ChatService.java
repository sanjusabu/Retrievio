package com.example.retrievio.service;
import org.springframework.stereotype.Service;

import com.example.retrievio.constants.Constants;
import com.example.retrievio.dto.ChatRequest;
import com.example.retrievio.model.RetrievedChunk;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final RetrievalService retrievalService;
    private final PromptService promptService;
    private final QueryRewriteService queryRewriteService;
    

    public String ask(ChatRequest chatRequest) {

        List<RetrievedChunk> chunks =
                retrievalService.retrieve(chatRequest.getRequest(), Constants.TOPK);

        String prompt =
                promptService.buildPrompt(
                        chatRequest.getRequest(),
                        chunks
                );

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}