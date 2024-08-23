package com.dinhhieu.FruitWebApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "document_metadata")
public class DocumentMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String contentType;

    private Long size;

    private String extension;

    private Date createDate;

    private String filePath;

    public DocumentMetadata(MultipartFile file, String description) {
        this.name = file.getOriginalFilename();
        this.size = file.getSize();
        this.extension = FilenameUtils.getExtension(file.getOriginalFilename());
        this.createDate = new Date();
        this.description = description;
        this.contentType = file.getContentType();
    }
}
