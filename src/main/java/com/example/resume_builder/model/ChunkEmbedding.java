package com.example.resume_builder.model;

public record ChunkEmbedding(
        Chunk chunk,
        float[] embedding
) {}
