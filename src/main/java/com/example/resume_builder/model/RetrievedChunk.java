package com.example.resume_builder.model;

public record RetrievedChunk(
        Chunk chunk,
        double similarity
) {
}
