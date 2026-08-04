package com.example.resume_builder.repository;

import com.example.resume_builder.entity.ChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChunkRepository
        extends JpaRepository<ChunkEntity, UUID> {
}