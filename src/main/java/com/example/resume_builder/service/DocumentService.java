package com.example.resume_builder.service;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.resume_builder.parser.PdfParser;
import com.example.resume_builder.repository.DocumentRepository;
import com.example.resume_builder.entity.DocumentEntity;
import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ParsedDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final PdfParser pdfParser;
    private final ChunkService chunkService;
    private final DocumentRepository documentRepository;

    public List<Chunk> extractChunks(MultipartFile file)
            throws IOException {

        // String text = pdfParser.parse(file);
        String text = new ClassPathResource("sample.txt")
        .getContentAsString(java.nio.charset.StandardCharsets.UTF_8);

        return chunkService.split(new ParsedDocument(file.getOriginalFilename(), text));

    }

    public DocumentEntity save() {

        return documentRepository.save(
                new DocumentEntity(
                        "sample.pdf",
                        Instant.now()
                )
        );

    }
}