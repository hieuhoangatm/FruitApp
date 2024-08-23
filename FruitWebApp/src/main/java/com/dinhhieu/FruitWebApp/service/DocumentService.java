package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.config.DocumentProperties;
import com.dinhhieu.FruitWebApp.dto.response.DocumentMetadataResponse;
import com.dinhhieu.FruitWebApp.exception.ServiceException;
import com.dinhhieu.FruitWebApp.mapper.DocumentMetadataMapper;
import com.dinhhieu.FruitWebApp.model.DocumentMetadata;
import com.dinhhieu.FruitWebApp.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import freemarker.template.Configuration;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DocumentService {
    private final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private DocumentProperties documentProperties;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    private EmailServiceDocument emailService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DocumentMetadataMapper documentMetadataMapper;


    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    public List<DocumentMetadataResponse> getDocumentMetadataList() {
        return this.documentRepository.findAll().stream().map(documentMetadataMapper::toDocumentResponse).toList();
    }

    public List<DocumentMetadata> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search name must not be null or empty");
        }
        return documentRepository.findByName(name);
    }

    public List<DocumentMetadata> findByExtension(String extension) {
        return documentRepository.findByExtension(extension);
    }

    public InputStream getDocumentStream(DocumentMetadata metadata) {
        if(metadata != null && metadata.getFilePath() != null) {
            return fileStorageService.getFileInputStream(metadata.getFilePath());
        } else {
            return null;
        }
    }

    public Optional<DocumentMetadata> findById(Long id) {
        return documentRepository.findById(id);
    }


    public void deleteDocument(Long id) {
        DocumentMetadata metadata = findById(id).get();
        if(metadata != null) {
            fileStorageService.deleteFile(metadata.getFilePath());
            documentRepository.deleteById(id);
        }
    }

//    public DocumentMetadata addDocument(MultipartFile file, String description) {
//        DocumentMetadata documentMetadata = new DocumentMetadata(file, description);
//        documentMetadata.setFilePath(fileStorageService.storeDocument(file));
//        return documentRepository.save(documentMetadata);
//    }
    public List<DocumentMetadata> addDocuments(List<MultipartFile> files, String description) {
        List<DocumentMetadata> documentMetadatas = new ArrayList<>();
        for (MultipartFile file : files) {
            DocumentMetadata documentMetadata = new DocumentMetadata(file, description);
            documentMetadata.setFilePath(fileStorageService.storeDocument(file));
            documentMetadatas.add(documentRepository.save(documentMetadata));
        }
        return documentMetadatas;
    }

    public List<DocumentMetadata> findByDescriptionContaining(String description) {
        return documentRepository.findByDescription(description);
    }


    @Scheduled(fixedDelayString = "${document.fixedDelay}")
    public void lastHourUploadsNotification() {
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        Date startDate = calendar.getTime();
        logger.info("send lastHourUploadsNotification Job ran at " + dateFormat.format(endDate));
        List<DocumentMetadata> documentMetadataList = documentRepository.findByCreateDateBetween(startDate, endDate);
        if(documentMetadataList != null && documentMetadataList.size() > 0) {
            emailService.send(
                    documentProperties.getTo(),
                    documentProperties.getFrom(),
                    "Uploaded Files",
                    formatDocumentsListing(documentMetadataList, startDate, endDate),
                    true);
        }
    }


    private String formatDocumentsListing(List<DocumentMetadata> documentMetadataList, Date startDate, Date endDate) {
        try {
            Map data = new HashMap();
            data.put("documentMetadataList", documentMetadataList);
            data.put("startDate", startDate);
            data.put("endDate", endDate);
            return FreeMarkerTemplateUtils
                    .processTemplateIntoString(freemarkerConfiguration.getTemplate("emailBody.ftl"), data);
        } catch (Throwable t) {
            String msg = "Error parsing the email template";
            logger.error(msg, t);
            throw new ServiceException(msg, t);
        }
    }


}
