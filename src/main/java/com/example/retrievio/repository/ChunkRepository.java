package com.example.retrievio.repository;

import com.example.retrievio.entity.ChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChunkRepository
        extends JpaRepository<ChunkEntity, UUID> {
@Query(value = """
    SELECT
        c.id,
        c.document_id,
        c.chunk_number,
        c.content,
        c.embedding,
        1 - (c.embedding <=> CAST(:embedding AS vector)) AS similarity 
    FROM chunks c
    ORDER BY c.embedding <=> CAST(:embedding AS vector)
    LIMIT :limit
    """,
    nativeQuery = true)
List<ChunkSearchProjection> search(
        @Param("embedding") float[] embedding,
        @Param("limit") int limit
);

void deleteByDocumentId(UUID documentId);
}