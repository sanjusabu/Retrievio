package com.example.retrievio.repository;

import java.util.UUID;

public interface ChunkSearchProjection {

    UUID getId();

    UUID getDocumentId();

    int getChunkNumber();

    String getContent();

    float[] getEmbedding();

    double getSimilarity();
}