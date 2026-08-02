package com.example.resume_builder.service;

import com.example.resume_builder.model.Chunk;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 1000;

    public List<Chunk> split(String text) {

        List<Chunk> chunks = new ArrayList<>();

        int chunkNumber = 1;

        for (int start = 0; start < text.length(); start += CHUNK_SIZE) {

            int end = Math.min(start + CHUNK_SIZE, text.length());

            chunks.add(
                    new Chunk(
                            chunkNumber++,
                            text.substring(start, end)
                    )
            );
        }

        return chunks;
    }

}