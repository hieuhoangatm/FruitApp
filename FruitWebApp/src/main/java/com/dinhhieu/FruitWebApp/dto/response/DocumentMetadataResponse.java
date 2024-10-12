package com.dinhhieu.FruitWebApp.dto.response;



import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DocumentMetadataResponse {
    private Long id;

    private String name;

    private String description;

    private String contentType;

    private Long size;

    private String extension;

    private Date createDate;

    private String filePath;
}
