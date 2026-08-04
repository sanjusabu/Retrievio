package com.example.resume_builder.mapper;

import org.springframework.stereotype.Component;

import com.example.resume_builder.dto.ChatResponse;
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

        public ChatResponse toDto(
            ChunkEntity chunkEntity
    ) {

        return  new ChatResponse(chunkEntity.getContent(), chunkEntity.getSimilarity());

    }

}