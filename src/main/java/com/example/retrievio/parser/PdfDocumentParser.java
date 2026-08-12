package com.example.retrievio.parser;

import com.example.retrievio.model.ParsedDocument;
import com.example.retrievio.model.DocumentParser;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PdfDocumentParser implements DocumentParser {

    @Override
    public ParsedDocument parse(MultipartFile file) throws IOException {

        try (PDDocument document =
                     Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper =
                    new PDFTextStripper();

            String text =
                    stripper.getText(document);

            return new ParsedDocument(
                    file.getOriginalFilename(),
                    text
            );

        }
    }
}