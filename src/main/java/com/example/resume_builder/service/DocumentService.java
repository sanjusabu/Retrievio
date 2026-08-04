package com.example.resume_builder.service;

import com.example.resume_builder.mapper.ChunkMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.resume_builder.parser.PdfDocumentParser;
import com.example.resume_builder.repository.ChunkRepository;
import com.example.resume_builder.repository.DocumentRepository;

import jakarta.transaction.Transactional;

import com.example.resume_builder.entity.ChunkEntity;
import com.example.resume_builder.entity.DocumentEntity;
import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ChunkEmbedding;
import com.example.resume_builder.model.ParsedDocument;
import com.example.resume_builder.model.UploadResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ChunkMapper chunkMapper;
    private final PdfDocumentParser pdfParser;
    private final ChunkService chunkService;
    private final DocumentRepository documentRepository;
    private final ChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;


    public List<Chunk> extractChunks(MultipartFile file)
            throws IOException {

        // String text = pdfParser.parse(file);
        String text = new ClassPathResource("sample.txt")
        .getContentAsString(java.nio.charset.StandardCharsets.UTF_8);

        return chunkService.split(new ParsedDocument(file.getOriginalFilename(), text));

    }

    public DocumentEntity save() {

        DocumentEntity document =
                documentRepository.save(
                        new DocumentEntity(
                                "spring.pdf",
                                Instant.now()
                        )
                );

        // chunkRepository.save(
        //         new ChunkEntity(
        //                 document,
        //                 1,
        //                 "Spring Boot is a Java framework."
        //         )
        // );

        // chunkRepository.save(
        //         new ChunkEntity(
        //                 document,
        //                 2,
        //                 "Dependency Injection..."
        //         )
        // );

        return document;
    }
    
    @Transactional
    public UploadResponse upload(MultipartFile file) throws IOException {
        ParsedDocument parsedDocument = pdfParser.parse(file);
        List<Chunk> chunks = chunkService.split(parsedDocument);
        List<ChunkEmbedding> embeddings = embeddingService.embed(chunks);

        DocumentEntity document = documentRepository.save(
                new DocumentEntity(
                        file.getOriginalFilename(),
                        Instant.now()
                )
        );
        
        List<ChunkEntity> entities = embeddings.stream()
        .map(embedding -> {
                return chunkMapper.toEntity(embedding, document);
        }).toList();

        chunkRepository.saveAll(entities);

        return new UploadResponse(document.getId(), entities.size());
    }
}