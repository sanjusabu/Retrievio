package com.example.resume_builder.parser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.resume_builder.model.ParsedDocument;

import java.io.IOException;

@Component
public class PdfParser {

    public ParsedDocument parse(MultipartFile file)
            throws IOException {

        return new ParsedDocument(
                file.getOriginalFilename(),
                "PDF PARSED TEXT"
        );

    }

}