package com.moonbright.professionals.service;

import com.moonbright.infrastructure.error.ErrorCodeAndDescription;
import com.moonbright.infrastructure.error.exception.BaseErrorResponse;
import com.moonbright.infrastructure.listeners.FileReaderSupplier;
import com.moonbright.infrastructure.record.FileDocDownloadRecord;
import com.moonbright.infrastructure.repository.FileFormRepository;
import com.moonbright.infrastructure.service.AppService;
import com.moonbright.professionals.entity.ApplicationFileDoc;
import com.moonbright.professionals.record.PaginatedAppFileDocRecord;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class ProfessionalUserFileFormService extends AppService implements ApplicationFileFormService {

    private final FileFormRepository<ApplicationFileDoc> fileFormRepository;

    @Resource
    ManagedExecutorService managedExecutorService;


    @Inject
    public ProfessionalUserFileFormService(FileFormRepository<ApplicationFileDoc> applicationFileDocFileFormRepository){
        this.fileFormRepository = applicationFileDocFileFormRepository;
    }

    @Override
    public CompletableFuture<Boolean> uploadFileForm(String filename, String fileFormat, InputStream fileInputStream, Boolean isMandatory) {
          if (this.fileFormRepository.fileExistsWithName(filename,getUserForRequest()))
                throwIOException();

          String uid = getUserForRequest();
          return CompletableFuture.supplyAsync(new FileReaderSupplier(fileInputStream),managedExecutorService)
                            .thenApply(bytes->{
                                    var appFileDoc = ApplicationFileDoc.applicationFileDocBuilder()
                                            .fileName(filename)
                                            .isMandatory(isMandatory)
                                            .fileFormat(fileFormat)
                                            .relatedUserId(uid)
                                            .build();
                                    appFileDoc.setContent(bytes);
                                    return appFileDoc;
                    })
                            .thenAccept(fileFormRepository::saveFileForm).thenApply(res->Boolean.TRUE);
    }

    private void throwIOException(){
        var baseErrorResponse = BaseErrorResponse.fromErrorCode(ErrorCodeAndDescription.ALREADY_EXISTING_FILE_DOC_ERROR_CODE);
        throw new WebApplicationException(Response.status(500).entity(baseErrorResponse).build());
    }
    @Override
    public CompletableFuture<Boolean> uploadFileForm(String filename, String fileFormat, InputStream fileInputStream) {
        return this.uploadFileForm(filename,fileFormat,fileInputStream,false);
    }

    @Override
    public void deleteFileForm(Long fileId) {
        fileFormRepository.deleteByFileId(fileId);
    }

    @Override
    public FileDocDownloadRecord downloadFileDOc(Long fileId) {
       var fileForm = fileFormRepository.findByFileId(fileId);
        return new FileDocDownloadRecord(fileForm.getFileName(),fileForm.getContent(),fileForm.getFileFormat());
    }

    @Override
    public PaginatedAppFileDocRecord findFileDocsForUser(Integer page) {
        String uid = getUserForRequest();
        return getPaginatedAppFileDocRecord(page, uid);
    }

    @Override
    public PaginatedAppFileDocRecord findFileDocsForUser(Integer page, String uid) {
        return getPaginatedAppFileDocRecord(page, uid);
    }

    private PaginatedAppFileDocRecord getPaginatedAppFileDocRecord(Integer page, String uid) {
        var docRecords = fileFormRepository.findWithPagination(page,uid).stream().map(ApplicationFileDoc::toAppFileDocRecord).collect(Collectors.toList());
        Long count = fileFormRepository.countByRelatedUserId(uid);
        Long pageCount = (count % 5 == 0 ? (count / 5) :((count / 5) + 1));
        return new PaginatedAppFileDocRecord(pageCount,docRecords);
    }
}
