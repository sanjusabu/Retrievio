package com.example.resume_builder.service;

import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ChunkEmbedding;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final EmbeddingModel embeddingModel;
    private final SimilarityService similarityService;

    public Chunk search(
            String query,
            List<ChunkEmbedding> embeddings
    ) {

        float[] queryVector =
                embeddingModel.embed(query);

        return embeddings.stream()

                .max(Comparator.comparingDouble(

                        embedding -> similarityService.cosineSimilarity(
                                queryVector,
                                embedding.embedding()
                        )

                ))

                .orElseThrow()

                .chunk();

    }

}