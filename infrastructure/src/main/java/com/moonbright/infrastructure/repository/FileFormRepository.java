package com.moonbright.infrastructure.repository;

import com.moonbright.infrastructure.persistence.entity.FileDoc;

import java.util.List;

public interface FileFormRepository<T extends FileDoc> {
    List<T> findByRelatedUserId(Long uid);
    void deleteByFileId(Long fileId);
    T findByFileId(Long fileId);
    T saveFileForm(T e);
    List<T> findWithPagination(Integer page,String uid);
    Long countByRelatedUserId(String uid);
    Boolean fileExistsWithName(String name, String userForRequest);
}
