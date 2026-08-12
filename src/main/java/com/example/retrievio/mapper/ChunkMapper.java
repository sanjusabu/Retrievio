package com.example.retrievio.mapper;

import org.springframework.stereotype.Component;

import com.example.retrievio.model.RetrievedChunk;
import com.example.retrievio.repository.ChunkSearchProjection;
import com.example.retrievio.entity.ChunkEntity;
import com.example.retrievio.entity.DocumentEntity;
import com.example.retrievio.model.Chunk;
import com.example.retrievio.model.ChunkEmbedding;

@Component
public class ChunkMapper {

    public ChunkEntity toEntity(
            ChunkEmbedding chunkEmbedding,
            DocumentEntity document
    ) {

        return new ChunkEntity(
                document,
                chunkEmbedding.chunk().chunkNumber(),
                chunkEmbedding.chunk().content(),
                chunkEmbedding.embedding()
        );

    }

    public RetrievedChunk toDto(
            ChunkSearchProjection chunkEntity
    ) {
        Chunk chunk = new Chunk(
                chunkEntity.getChunkNumber(),
                chunkEntity.getContent()
        );
        return new RetrievedChunk(chunk, chunkEntity.getSimilarity());
    }

}