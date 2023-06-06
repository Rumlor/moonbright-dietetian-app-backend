package com.moonbright.professionals.repository;

import com.moonbright.infrastructure.repository.FileFormRepository;
import com.moonbright.professionals.entity.ApplicationFileDoc;
import jakarta.ejb.Singleton;

import java.util.List;

@Singleton
public class AppFileDocRepository extends BaseRepository implements FileFormRepository<ApplicationFileDoc> {



    @Override
    public List<ApplicationFileDoc> findByRelatedUserId(Long uid) {
        return entityManager.createQuery("select a from ApplicationFileDoc a where a.relatedUserId =:uid",ApplicationFileDoc.class)
                .setParameter("uid",uid)
                .getResultList();
    }

    @Override
    public void deleteByFileId(Long fileId) {
        entityManager.createQuery("delete from ApplicationFileDoc a where a.id =:fileId")
                .setParameter("fileId",fileId)
                .executeUpdate();
    }

    @Override
    public ApplicationFileDoc findByFileId(Long fileId) {
        return entityManager.find(ApplicationFileDoc.class,fileId);
    }

    @Override
    public ApplicationFileDoc saveFileForm(ApplicationFileDoc e)  {
        entityManager.persist(e);
        entityManager.flush();
        return e;
    }

    @Override
    public List<ApplicationFileDoc> findWithPagination(Integer page,String uid) {
        return entityManager.createQuery("select a from ApplicationFileDoc a where a.relatedUserId =:uid order by a.id desc", ApplicationFileDoc.class)
                .setParameter("uid",uid)
                .setFirstResult((page-1)*5)
                .getResultList();
    }

    @Override
    public Long countByRelatedUserId(String uid) {
        return entityManager.createQuery("select count (a.id) from ApplicationFileDoc a where a.relatedUserId =:uid",Long.class)
                .setParameter("uid",uid)
                .getSingleResult();
    }

    @Override
    public Boolean fileExistsWithName(String name, String userForRequest) {
        return !entityManager.createQuery("select 1 from ApplicationFileDoc a where a.fileName = ?1 and a.relatedUserId = ?2")
                .setParameter(1,name)
                .setParameter(2,userForRequest)
                .getResultList().isEmpty();
    }
}
