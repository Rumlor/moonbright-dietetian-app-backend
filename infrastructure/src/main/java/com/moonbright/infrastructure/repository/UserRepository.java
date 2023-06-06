package com.moonbright.infrastructure.repository;


import com.moonbright.infrastructure.persistence.entity.FileDoc;

import java.util.List;

public interface UserRepository<T> {
    T findUserById(Long id);
    T saveUser(T user);
    T findUserByUsername(String username);
    String userExistsByUsername(String username);
    List<FileDoc> findFileDocsForUser(String username);
}
