package com.example.resume_builder.mapper;

import org.springframework.stereotype.Component;

import com.example.resume_builder.model.RetrievedChunk;
import com.example.resume_builder.repository.ChunkSearchProjection;
import com.example.resume_builder.entity.ChunkEntity;
import com.example.resume_builder.entity.DocumentEntity;
import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ChunkEmbedding;

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