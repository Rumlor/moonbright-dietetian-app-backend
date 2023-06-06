package com.moonbright.infrastructure.service;

import com.moonbright.infrastructure.record.FileDocDownloadRecord;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface FileFormService {
    CompletableFuture<Boolean> uploadFileForm(String filename, String fileFormat, InputStream fileInputStream);
    void deleteFileForm(Long filename);
    FileDocDownloadRecord downloadFileDOc(Long fileId);
}
