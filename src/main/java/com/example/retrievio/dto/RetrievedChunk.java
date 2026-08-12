package com.example.retrievio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetrievedChunk {
    String response;
    double similarity;
}
