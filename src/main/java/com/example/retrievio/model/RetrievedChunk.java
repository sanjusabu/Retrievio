package com.example.retrievio.model;

public record RetrievedChunk(
        Chunk chunk,
        double similarity
) {
}
