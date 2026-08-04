package com.example.resume_builder.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.resume_builder.dto.EmbeddingPreview;
import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ChunkEmbedding;
import com.example.resume_builder.model.UploadResponse;
import com.example.resume_builder.service.ChunkService;
import com.example.resume_builder.service.DocumentService;
import com.example.resume_builder.service.EmbeddingService;
import com.example.resume_builder.service.SearchService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final ChunkService chunkService;
    private final EmbeddingService embeddingService;
    private final SearchService searchService;

    @PostMapping("/upload")
    public UploadResponse upload(@RequestParam("file") MultipartFile file)
            throws IOException {

        return documentService.upload(file);

    }
    @PostMapping("/save")
    public void save() {

        documentService.save();

    }
    // @GetMapping("/embedding/test")
    // public List<EmbeddingPreview> test() throws Exception {

    //     String text = new ClassPathResource("sample.txt")
    //             .getContentAsString(StandardCharsets.UTF_8);

    //     List<Chunk> chunks = chunkService.split(text);

    //     return embeddingService.preview(chunks);
    // }

    // @GetMapping("/search/test")
    // public Chunk search() throws Exception {

    //     String text =
    //             new ClassPathResource("sample.txt")
    //                     .getContentAsString(StandardCharsets.UTF_8);

    //     List<Chunk> chunks =
    //             chunkService.split(text);

    //     List<ChunkEmbedding> embeddings =
    //             embeddingService.embed(chunks);

    //     return searchService.search(
    //             "Explain Retrieval Augmented Generation",
    //             embeddings
    //     );

    // }

}