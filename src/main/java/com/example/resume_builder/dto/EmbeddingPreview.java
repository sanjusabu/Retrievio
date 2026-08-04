package com.example.resume_builder.dto;

import java.util.List;

public record EmbeddingPreview(
        int chunkNumber,
        List<Float> preview
) {
}