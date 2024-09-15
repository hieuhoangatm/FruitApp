package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.response.DocumentMetadataResponse;
import com.dinhhieu.FruitWebApp.model.DocumentMetadata;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T11:23:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class DocumentMetadataMapperImpl implements DocumentMetadataMapper {

    @Override
    public DocumentMetadataResponse toDocumentResponse(DocumentMetadata documentMetadata) {
        if ( documentMetadata == null ) {
            return null;
        }

        DocumentMetadataResponse documentMetadataResponse = new DocumentMetadataResponse();

        documentMetadataResponse.setName( documentMetadata.getName() );
        documentMetadataResponse.setDescription( documentMetadata.getDescription() );
        documentMetadataResponse.setContentType( documentMetadata.getContentType() );
        documentMetadataResponse.setSize( documentMetadata.getSize() );
        documentMetadataResponse.setExtension( documentMetadata.getExtension() );
        documentMetadataResponse.setCreateDate( documentMetadata.getCreateDate() );
        documentMetadataResponse.setFilePath( documentMetadata.getFilePath() );

        return documentMetadataResponse;
    }
}
