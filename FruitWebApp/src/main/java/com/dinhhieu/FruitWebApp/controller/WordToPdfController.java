package com.dinhhieu.FruitWebApp.controller;


import com.dinhhieu.FruitWebApp.service.WordConvertPdfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/word")
@Slf4j
public class WordToPdfController {

    @Autowired
    private WordConvertPdfService documentConversionService;
//    @PostMapping(value = "/convert")
//    public ResponseEntity<byte[]> convertWordToPdf(@RequestParam("file") MultipartFile file) {
//        try {
//            byte[] pdfBytes = documentConversionService.convertWordToPdf(file.getInputStream());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "document.pdf");
//
//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @PostMapping("/convert")
    public ResponseEntity<String> convertWordToPdf(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return new ResponseEntity<>("Tên file không hợp lệ", HttpStatus.BAD_REQUEST);
            }

            File pdfFile = documentConversionService.convertWordToPdf(file.getInputStream(), originalFilename);

            String filePath = pdfFile.getAbsolutePath();
            return ResponseEntity.ok("File PDF đã được lưu tại: " + filePath);

        } catch (IOException e) {
            return new ResponseEntity<>("Lỗi khi chuyển đổi tài liệu sang PDF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
