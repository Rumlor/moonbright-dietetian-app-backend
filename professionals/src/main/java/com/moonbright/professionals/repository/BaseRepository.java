package com.moonbright.professionals.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class BaseRepository {
    @PersistenceContext(unitName = "professionalPU")
    protected EntityManager entityManager;
}
