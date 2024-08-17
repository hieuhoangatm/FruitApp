package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.service.FileConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileConversionController {

    @Autowired
    private FileConversionService fileConversionService;

    @PostMapping("/convert")
    public ResponseEntity<String> convertFileToPdf(@RequestParam("inputFile") MultipartFile inputFile) {
        try {
            // Tạo đường dẫn tệp tạm thời để lưu tệp trước khi chuyển đổi
            String inputFilePath = System.getProperty("java.io.tmpdir") + File.separator + inputFile.getOriginalFilename();
            File tempFile = new File(inputFilePath);

            // Lưu tệp tạm thời vào hệ thống
            inputFile.transferTo(tempFile);

            // Đặt đường dẫn đầu ra cho tệp PDF
            String outputFilePath = tempFile.getParent() + File.separator + tempFile.getName().replaceFirst("[.][^.]+$", "") + ".pdf";

            // Gọi dịch vụ để chuyển đổi tệp
            fileConversionService.convertToPdf(tempFile.getAbsolutePath(), outputFilePath);

            // Xóa tệp tạm thời sau khi chuyển đổi
            tempFile.delete();

            return ResponseEntity.ok("File converted successfully: " + outputFilePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Conversion failed: " + e.getMessage());
        }
    }
}

