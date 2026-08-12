package com.example.retrievio.model;

public record Chunk(
        int chunkNumber,
        String content
) {
}