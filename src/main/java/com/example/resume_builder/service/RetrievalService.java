package com.example.resume_builder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.resume_builder.dto.ChatResponse;
import com.example.resume_builder.entity.ChunkEntity;
import com.example.resume_builder.mapper.ChunkMapper;
import com.example.resume_builder.model.RetrievedChunk;
import com.example.resume_builder.repository.ChunkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final EmbeddingService embeddingService;
    private final ChunkRepository repository;
    private final ChunkMapper chunkMapper;

    public List<ChatResponse> retrieve(
            String question,
            int topK
    ) {

        float[] queryEmbedding =
                embeddingService.embed(question);

        List<ChunkEntity> chunkEntities =repository.search(
                queryEmbedding,
                topK
        );
        return chunkEntities.stream().map(chunkMapper::toDto).toList();
    }

}