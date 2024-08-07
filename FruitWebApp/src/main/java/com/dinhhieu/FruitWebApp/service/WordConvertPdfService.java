package com.dinhhieu.FruitWebApp.service;

import org.springframework.stereotype.Service;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class WordConvertPdfService {
//    public byte[] convertWordToPdf(InputStream inputStream) throws IOException {
//        try {
//            Document document = new Document(inputStream);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            document.save(outputStream, SaveFormat.PDF);
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            throw new IOException("Error converting document to PDF", e);
//        }
//    }
    public File convertWordToPdf(InputStream inputStream, String originalFilename) throws IOException {
        try {
            Document document = new Document(inputStream);

            String pdfFilename = originalFilename.replaceAll("\\.docx?$", ".pdf");
            File pdfFile = new File(pdfFilename);

            document.save(pdfFile.getAbsolutePath(), SaveFormat.PDF);
            return pdfFile;
        } catch (Exception e) {
            throw new IOException("Error converting document to PDF", e);
        }
    }
}
