package com.example.retrievio.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.example.retrievio.dto.ChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryRewriteService {

    private final ChatClient chatClient;

    public String rewrite(
            String question,
            List<ChatMessage> history
    ) {

        if (history == null || history.isEmpty()) {
            return question;
        }

        String conversation = history.stream()
                .map(message ->
                        message.getRole() + ": " + message.getContent()
                )
                .reduce(
                        "",
                        (current, message) ->
                                current + "\n" + message
                );

        String prompt = """
                Rewrite the user's latest question into a standalone
                search query for retrieving information from the document.

                Conversation history:
                %s

                Latest question:
                %s

                Rules:
                - Resolve references such as "it", "that", "this", "they", etc.
                - Preserve the user's intent.
                - Do not answer the question.
                - Return ONLY the rewritten search query.
                - If the question is already standalone, return it unchanged.
                """.formatted(conversation, question);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}