package com.example.retrievio.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.retrievio.model.UploadResponse;
import com.example.retrievio.service.DocumentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public UploadResponse upload(@RequestParam("file") MultipartFile file)
            throws IOException {

        return documentService.upload(file);

    }
    @PostMapping("/save")
    public void save() {
        documentService.save();

    }

    @DeleteMapping("/chunks/{documentId}")
    public ResponseEntity<Void> deleteChunks(
            @PathVariable UUID documentId
    ) {
        documentService.deleteChunks(documentId);
        return ResponseEntity.noContent().build();
    }
}