package com.example.retrievio.dto;

import java.util.List;

public record EmbeddingPreview(
        int chunkNumber,
        List<Float> preview
) {
}