package com.example.retrievio.repository;

import com.example.retrievio.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository
        extends JpaRepository<DocumentEntity, UUID> {
}