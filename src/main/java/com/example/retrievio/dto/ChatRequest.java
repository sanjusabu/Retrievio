package com.example.retrievio.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChatRequest {

    private String request;

    private List<ChatMessage> history;
}