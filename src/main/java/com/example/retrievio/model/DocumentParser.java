package com.example.retrievio.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentParser {

    ParsedDocument parse(MultipartFile file) throws IOException;

}