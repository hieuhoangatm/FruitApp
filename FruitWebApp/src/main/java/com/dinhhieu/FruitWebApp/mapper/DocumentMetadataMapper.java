package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.response.DocumentMetadataResponse;
import com.dinhhieu.FruitWebApp.model.DocumentMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMetadataMapper {
    @Mapping(source = "id", target = "id")
    DocumentMetadataResponse toDocumentResponse(DocumentMetadata documentMetadata);
}
