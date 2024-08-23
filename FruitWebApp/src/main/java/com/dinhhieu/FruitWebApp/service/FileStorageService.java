package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.config.DocumentProperties;
import com.dinhhieu.FruitWebApp.exception.ServiceException;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    @Autowired
    private DocumentProperties documentProperties;

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private static Path storageDir;

    @PostConstruct
    public void initStorageDirectory(){
        this.storageDir = Paths.get(documentProperties.getStorageDir());
        if (!Files.exists(this.storageDir)) {
            try {
                Files.createDirectories(this.storageDir);
            } catch (IOException e) {
                String msg = "Error creating storage directory";
                logger.error(msg, e);
                throw new ServiceException(msg, e);
            }
        }
    }

    public String storeDocument(MultipartFile file) {
        Path filePath = this.storageDir.resolve(UUID.randomUUID() + File.separator + file.getOriginalFilename());
        String absolutePathStr = filePath.toAbsolutePath().toString();
        try {
            if (file.isEmpty()) {
                String msg = "File can not be empty:" + file.getOriginalFilename();
                logger.error(msg);
                throw new ServiceException(msg);
            }
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            logger.info("Saving the form file to:" + absolutePathStr);
            Files.copy(file.getInputStream(), filePath);
            return absolutePathStr;
        } catch (IOException e) {
            throw new ServiceException("Error saving file to " + absolutePathStr, e);
        }
    }


    public void deleteFile(String path) {
        try {
            File deleteFile = new File(path);
            if(deleteFile.exists()) {
                FileUtils.forceDelete(deleteFile);
            }
        } catch (Throwable t) {
            throw new ServiceException("Error cleaning file " + path, t);
        }
    }


    public InputStream getFileInputStream(String path) {
        try {
            File readFile = new File(path);
            if(readFile.exists()) {
                return new FileInputStream(readFile);
            } else {
                return null;
            }
        } catch (Throwable t) {
            throw new ServiceException("Error reading file " + path, t);
        }
    }


    public static void deleteAllStoredDocuments() {
        try {
            FileUtils.forceDelete(storageDir.toFile());
        } catch (Throwable t) {
            throw new ServiceException("Error cleaning directory " + storageDir, t);
        }
    }
}


