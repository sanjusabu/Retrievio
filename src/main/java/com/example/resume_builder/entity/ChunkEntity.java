package com.example.resume_builder.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "chunks")
public class ChunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentEntity document;

    private int chunkNumber;

    @Column(columnDefinition = "TEXT")
    private String content;

    // embedding comes later
}
