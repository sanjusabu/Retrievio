package com.example.resume_builder.repository;

import com.example.resume_builder.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository
        extends JpaRepository<DocumentEntity, UUID> {
}