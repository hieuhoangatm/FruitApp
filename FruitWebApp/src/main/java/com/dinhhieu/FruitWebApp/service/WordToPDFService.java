package com.dinhhieu.FruitWebApp.service;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class WordToPDFService {
    public File convertDocxToPdf(File docxFile) throws IOException {
        File pdfFile = new File("C:\\Users\\Acer\\Downloads\\", docxFile.getName().replace(".docx", ".pdf"));

        try (FileInputStream fis = new FileInputStream(docxFile);
             XWPFDocument document = new XWPFDocument(fis);
             FileOutputStream fos = new FileOutputStream(pdfFile)) {

            PdfOptions options = PdfOptions.create();

            PdfConverter.getInstance().convert(document, fos, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error while converting DOCX to PDF: " + e.getMessage());
        }

        return pdfFile;
    }
}
