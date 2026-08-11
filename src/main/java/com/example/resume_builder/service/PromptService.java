package com.example.resume_builder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.RetrievedChunk;

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
            based only on the provided resume context.

            Resume context:
            ----------------
            %s
            ----------------

            User question:
            %s

            Instructions:
            - Answer using only information from the resume context.
            - Be concise and direct.
            - Use plain text formatting.
            - Put each important point on a new line.
            - Use simple numbered lists when appropriate.
            - Do not repeat the question.
            - If the answer is not present in the resume, say:
              "I couldn't find that information in the resume."

            Answer:
            """.formatted(context, question);
    }
}