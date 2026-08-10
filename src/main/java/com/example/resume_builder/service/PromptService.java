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
                You are a helpful assistant answering questions
                about the uploaded document.

                Answer the question using ONLY the provided context.

                If the answer cannot be found in the context,
                say that you don't know based on the provided document.

                Context:
                --------------------
                %s
                --------------------

                Question:
                %s

                Answer:
                """.formatted(context, question);
    }
}