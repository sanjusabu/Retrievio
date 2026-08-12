package com.example.retrievio.model;

public record ChunkEmbedding(
        Chunk chunk,
        float[] embedding
) {}
