package com.example.resume_builder.service;

import com.example.resume_builder.dto.EmbeddingPreview;
import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ChunkEmbedding;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public List<ChunkEmbedding> embed(List<Chunk> chunks) {

        List<ChunkEmbedding> result = new ArrayList<>();

        for (Chunk chunk : chunks) {

            float[] vector =
                    embeddingModel.embed(chunk.content());

            result.add(
                    new ChunkEmbedding(
                            chunk,
                            vector
                    )
            );
        }

        return result;
    }

    public List<EmbeddingPreview> preview(List<Chunk> chunks) {

        List<EmbeddingPreview> previews = new ArrayList<>();

        for (Chunk chunk : chunks) {

            float[] embedding = embeddingModel.embed(chunk.content());

            List<Float> firstTen = new ArrayList<>();

            System.out.println("Embedding length: " + embedding.length);

            for (int i = 0; i < Math.min(10, embedding.length); i++) {
                firstTen.add(embedding[i]);
            }

            previews.add(
                    new EmbeddingPreview(
                            chunk.chunkNumber(),
                            firstTen
                    )
            );
        }

        return previews;
    }
}