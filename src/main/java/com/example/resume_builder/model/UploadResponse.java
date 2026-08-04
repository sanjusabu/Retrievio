package com.example.resume_builder.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadResponse {
    UUID documentId;
    long size;
}
