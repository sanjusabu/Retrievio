package com.example.resume_builder.service;

import com.example.resume_builder.model.Chunk;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 100;

    public List<Chunk> split(String text) {

        List<Chunk> chunks = new ArrayList<>();

        int chunkNumber = 1;

        String[] texts = text.split(" ");

        for (int start = 0; start < texts.length; start += CHUNK_SIZE) {

            int end = Math.min(start + CHUNK_SIZE, texts.length);
            StringBuilder chunkText = new StringBuilder();
            for (int i = start; i < end; i++) {
                chunkText.append(texts[i]).append(" ");
            }

            chunks.add(
                    new Chunk(
                            chunkNumber++,
                            chunkText.toString().trim()
                    )
            );
        }

        return chunks;
    }

}