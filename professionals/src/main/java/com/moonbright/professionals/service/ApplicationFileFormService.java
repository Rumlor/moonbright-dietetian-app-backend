package com.moonbright.professionals.service;

import com.moonbright.infrastructure.service.FileFormService;
import com.moonbright.professionals.record.PaginatedAppFileDocRecord;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface ApplicationFileFormService extends FileFormService {

    default CompletableFuture<Boolean> uploadFileForm(String filename, String fileFormat, InputStream fileInputStream, Boolean isMandatory){
        return this.uploadFileForm(filename,fileFormat,fileInputStream);
    }

    PaginatedAppFileDocRecord findFileDocsForUser(Integer page);

    PaginatedAppFileDocRecord findFileDocsForUser(Integer page,String uid);
}
