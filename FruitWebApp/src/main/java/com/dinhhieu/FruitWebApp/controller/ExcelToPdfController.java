//package com.dinhhieu.FruitWebApp.controller;
//
//import com.dinhhieu.FruitWebApp.service.ExcelToPdfService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import com.itextpdf.text.DocumentException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//
//@RestController
//@RequestMapping("/api/convert")
//public class ExcelToPdfController {
//
//    @Autowired
//    private ExcelToPdfService excelToPdfService;
//
//    @PostMapping("/excel-to-pdf")
//    public ResponseEntity<byte[]> convertExcelToPdf(@RequestParam("file") MultipartFile file) {
//        try {
//            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
//            excelToPdfService.convertExcelToPdf(file.getInputStream(), pdfOutputStream);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.pdf");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
//                    .body(pdfOutputStream.toByteArray());
//        } catch (IOException | DocumentException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.service.ExcelToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/convert")
public class ExcelToPdfController {

    @Autowired
    private ExcelToPdfService excelToPdfService;
    // co phai ngay luc dau e chang lua chon anh dau
    @PostMapping("/excel-to-pdf")
    public ResponseEntity<String> convertExcelToPdf(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return ResponseEntity.badRequest().body("Filename is missing.");
            }

            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String pdfFilename = baseName + ".pdf";

            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            excelToPdfService.convertExcelToPdf(file.getInputStream(), pdfOutputStream);

            // Define the path to save the PDF file
            String downloadDir = "C:\\Users\\Acer\\Downloads";
            File outputFile = new File(downloadDir, pdfFilename);
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                fileOutputStream.write(pdfOutputStream.toByteArray());
            }

            // Prepare response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFilename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body("File saved successfully as " + outputFile.getAbsolutePath());
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the file: " + e.getMessage());
        }
    }
}

