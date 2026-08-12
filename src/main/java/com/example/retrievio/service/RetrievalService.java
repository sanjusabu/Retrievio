package com.example.retrievio.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.example.retrievio.model.RetrievedChunk;
import com.example.retrievio.entity.ChunkEntity;
import com.example.retrievio.mapper.ChunkMapper;
import com.example.retrievio.repository.ChunkRepository;
import com.example.retrievio.repository.ChunkSearchProjection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final EmbeddingService embeddingService;
    private final ChunkRepository repository;
    private final ChunkMapper chunkMapper;

    public List<RetrievedChunk> retrieve(
            String question,
            int topK
    ) {

        float[] queryEmbedding =
                embeddingService.embed(question);

        List<ChunkSearchProjection> chunkProjections = repository.search(
                queryEmbedding,
                topK
        );
        return chunkProjections.stream().map(chunkMapper::toDto).toList();
    }

}