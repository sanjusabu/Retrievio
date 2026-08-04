package com.example.resume_builder.service;

import com.example.resume_builder.model.Chunk;
import com.example.resume_builder.model.ParsedDocument;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 100;
    private static final int OVERLAP = 20;

    public List<Chunk> split(ParsedDocument text) {

        String[] words = text.content().split("\\s+");

        List<Chunk> chunks = new ArrayList<>();

        int chunkNumber = 1;

        for (int start = 0; start < words.length; start += (CHUNK_SIZE - OVERLAP)) {

            int end = Math.min(start + CHUNK_SIZE, words.length);

            StringBuilder builder = new StringBuilder();

            for (int i = start; i < end; i++) {
                builder.append(words[i]).append(" ");
            }

            chunks.add(new Chunk(
                    chunkNumber++,
                    builder.toString().trim()
            ));

            if (end == words.length) {
                break;
            }
        }

        return chunks;
    }
}
