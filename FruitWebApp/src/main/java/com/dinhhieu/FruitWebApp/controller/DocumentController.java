package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.response.DocumentMetadataResponse;
import com.dinhhieu.FruitWebApp.exception.ServiceException;
import com.dinhhieu.FruitWebApp.model.DocumentMetadata;
import com.dinhhieu.FruitWebApp.service.DocumentService;
import com.dinhhieu.FruitWebApp.service.FileConversionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/documents")
@Slf4j
public class DocumentController {
    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FileConversionService fileConversionService;

    @GetMapping("/findAll")
    public List<DocumentMetadataResponse> getDocumentMetadataList() {
        return documentService.getDocumentMetadataList();
    }

    @GetMapping("/findById/{id}")
    public DocumentMetadata findById(@PathVariable Long id) {
        return documentService.findById(id).get();
    }

    @GetMapping("/findByName")
    public List<DocumentMetadata> findByName(@RequestParam(required = false) String name) {
        return documentService.findByName(name);
    }


//    @PostMapping("/upload")
//    public DocumentMetadata uploadDocument(@RequestParam(value="file", required=true) MultipartFile file ,
//                                           @RequestParam(value="description", required=true) String description) {
//        return documentService.addDocument(file, description);
//    }

    @PostMapping("/upload")
    public List<DocumentMetadata> uploadDocuments(@RequestParam(value="files", required=true) List<MultipartFile> files,
                                                  @RequestParam(value="description", required=true) String description) {
        return documentService.addDocuments(files, description);
    }



    @GetMapping("/download/{id}")
    public void getFile(@PathVariable Long id, HttpServletResponse response) {
        InputStream is = null;
        try {
            DocumentMetadata metadata = documentService.findById(id).get();
            is = documentService.getDocumentStream(metadata);
            if(is != null) {
                IOUtils.copy(is, response.getOutputStream());
                response.setContentType(metadata.getContentType());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getName() + "\"");
                response.setHeader("test header", "hello test header");
                response.flushBuffer();
            } else {
                throw new ServiceException("Document with id:" + id + " not found.");
            }
        } catch (IOException ex) {
            String msg = "Error writing file to output stream. Document id: " + id;
            logger.info(msg, ex);
            throw new ServiceException(msg);
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponData<?> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "delete file with id: "+id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> readFile(@PathVariable Long id) {
        DocumentMetadata document = documentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id " + id));
        Path filePath = Path.of(document.getFilePath());

        try {

            String mediaType = Files.probeContentType(filePath);
//            String mediaType = document.getContentType();
            if (mediaType == null) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }


            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while loading file " + document.getFilePath(), e);
        }
    }

}
