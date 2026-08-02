package com.example.resume_builder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.service.DocumentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public List<Chunk> upload(@RequestParam("file") MultipartFile file)
            throws IOException {

        return documentService.extractChunks(file);

    }

}