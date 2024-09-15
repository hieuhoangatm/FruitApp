package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.service.FileConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileConversionController {

    @Autowired
    private FileConversionService fileConversionService;

    @PostMapping("/convert")
    public ResponseEntity<String> convertFileToPdf(@RequestParam("inputFile") MultipartFile inputFile) {
        try {
            // Tạo đường dẫn tệp tạm thời để lưu tệp trước khi chuyển đổi
            String inputFilePath = System.getProperty("java.io.tmpdir") + File.separator + inputFile.getOriginalFilename();
            File tempFile = new File(inputFilePath);

            inputFile.transferTo(tempFile);

            String outputFilePath = tempFile.getParent() + File.separator + tempFile.getName().replaceFirst("[.][^.]+$", "") + ".pdf";

            fileConversionService.convertToPdf(tempFile.getAbsolutePath(), outputFilePath);

            tempFile.delete();

            return ResponseEntity.ok("File converted successfully: " + outputFilePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Conversion failed: " + e.getMessage());
        }
    }

//    @GetMapping("/view/{filename}")
//    public String viewFileContent(@PathVariable String filename) {
//        try {
////            String filePath = "C:\\Users\\Acer\\AppData\\Local\\Temp\\upload\\47a9f916-a24e-47b5-a312-9332c645543c\\" + filename;
//            String filePath = "C:\\Users\\Acer\\Downloads\\test";
//            return fileConversionService.viewFileContent(filePath);
//        } catch (IOException e) {
//            return "Error: " + e.getMessage();
//        }
//    }
}

