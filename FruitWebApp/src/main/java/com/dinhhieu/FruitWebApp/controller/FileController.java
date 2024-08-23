package com.dinhhieu.FruitWebApp.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dinhhieu.FruitWebApp.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("")
    public ResponseEntity<?> helloo(){
        return ResponseEntity.status(HttpStatus.OK).body("hiiii");
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        Map<String, String> uploadResults = new HashMap<>();
        try {
            for (MultipartFile file : files) {
                Map<String, Object> uploadParams = new HashMap<>();
                uploadParams.put("resource_type", "raw");  // Đối với các file không phải ảnh

                Map<String, Object> result = cloudinaryService.uploadFile(file.getBytes(), uploadParams);
                uploadResults.put(file.getOriginalFilename(), (String) result.get("url"));
            }
            return ResponseEntity.ok(uploadResults);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<?> deleteFile(@PathVariable String publicId) {
        try {
            cloudinaryService.deleteFile(publicId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File deletion failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{publicId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String publicId) {
        try {
            // Lấy URL của file từ Cloudinary
            String url = cloudinaryService.getFileUrl(publicId);

            // Sử dụng RestTemplate để tải file về
            RestTemplate restTemplate = new RestTemplate();
            byte[] fileData = restTemplate.getForObject(url, byte[].class);

            // Chuyển dữ liệu file thành InputStreamResource để gửi trong response
            InputStream inputStream = new ByteArrayInputStream(fileData);
            Resource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + publicId + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
