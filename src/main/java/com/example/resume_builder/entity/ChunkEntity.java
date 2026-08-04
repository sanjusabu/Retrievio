package com.example.resume_builder.entity;

import java.util.UUID;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "chunks")
public class ChunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentEntity document;

    @Column(nullable = false)
    private int chunkNumber;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    @Column(nullable = false)
    private float[] embedding;

    public ChunkEntity(
            DocumentEntity document,
            int chunkNumber,
            String content,
            float[] embedding
    ) {
        this.document = document;
        this.chunkNumber = chunkNumber;
        this.content = content;
        this.embedding = embedding;
    }
}
