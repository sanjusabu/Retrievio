package com.example.resume_builder.parser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PdfParser {

    public String parse(MultipartFile file)
            throws IOException {

        return "PDF PARSED TEXT";

    }

}