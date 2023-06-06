package com.moonbright.clients.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.SynchronizationType;

public abstract class BaseRepository {
    @PersistenceContext(unitName = "clientPU")
    protected EntityManager entityManager;
}
