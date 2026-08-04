package com.example.resume_builder.repository;

import com.example.resume_builder.entity.ChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChunkRepository
        extends JpaRepository<ChunkEntity, UUID> {
                @Query(
    value = """
        SELECT *,
                 embedding <=> CAST(:embedding AS vector) AS similarity
        FROM chunks
        ORDER BY embedding <=> CAST(:embedding AS vector)
        LIMIT :limit
        """,
    nativeQuery = true
)
List<ChunkEntity> search(
        @Param("embedding") float[] embedding,
        @Param("limit") int topK
);
}