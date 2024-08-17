package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.service.ExcelToPdfService;
import com.dinhhieu.FruitWebApp.service.WordConvertPdfService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/convertPdf")
public class WordExcelConvertPdfController {
    private final ExcelToPdfService excelToPdfService;
    private final WordConvertPdfService wordConvertPdfService;

    @PostMapping("")
    public ResponseEntity<?> convertToPdf(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return new ResponseEntity<>("Tên file không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        if (originalFilename.endsWith(".docx")) {
            File pdfFile = wordConvertPdfService.convertWordToPdf(file.getInputStream(), originalFilename);
            String filePath = pdfFile.getAbsolutePath();
            return ResponseEntity.ok("File PDF đã được lưu tại: " + filePath);
        } else if (originalFilename.endsWith(".xlsx")) {
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String pdfFilename = baseName + ".pdf";

            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            excelToPdfService.convertExcelToPdf(file.getInputStream(), pdfOutputStream);

            String downloadDir = "C:\\Users\\Acer\\Downloads";
            File outputFile = new File(downloadDir, pdfFilename);
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                fileOutputStream.write(pdfOutputStream.toByteArray());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFilename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body("File saved successfully as " + outputFile.getAbsolutePath());
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
        }

    }
}
