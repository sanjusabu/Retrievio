package com.example.retrievio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.retrievio.model.Chunk;
import com.example.retrievio.model.RetrievedChunk;

@Service
public class PromptService {

    public String buildPrompt(
            String question,
            List<RetrievedChunk> chunks
    ) {

        String context = chunks.stream()
        .map(RetrievedChunk::chunk)
        .map(Chunk::content)
        .collect(Collectors.joining("\n\n"));

 return """
            You are a helpful assistant that answers questions
            based only on the provided document context.

            Document context:
            ----------------
            %s
            ----------------

            User question:
            %s

            Instructions:
            - Answer using only information from the document context.
            - Be concise and direct.
            - Use plain text formatting.
            - Put each important point on a new line.
            - Use simple numbered lists when appropriate.
            - Do not repeat the question.
            - If the answer is not present in the document, say:
              "I couldn't find that information in the document."

            Answer:
            """.formatted(context, question);
    }
}