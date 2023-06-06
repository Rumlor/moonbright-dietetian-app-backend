package com.moonbright.clients.repository;

import com.moonbright.infrastructure.persistence.entity.UserSettings;
import com.moonbright.infrastructure.repository.UserSettingsRepository;
import jakarta.ejb.Singleton;

import java.util.Optional;

@Singleton(name = "clientRep")
public class UserSettingsClientRepository extends BaseRepository implements UserSettingsRepository {

    @Override
    public Optional<UserSettings> findUserSettings(String uid) {
        return entityManager.createQuery("select us from UserSettings us where us.relatedUserId =:uid",UserSettings.class)
                .setParameter("uid",uid)
                .getResultList().stream().findFirst();
    }

    @Override
    public void saveUserSettings(UserSettings userSettings) {
        var resultList =  entityManager.createQuery("select us from UserSettings us where us.relatedUserId =:uid",UserSettings.class)
                        .setParameter("uid",userSettings.getRelatedUserId())
                                .getResultList();

        if (resultList != null && !resultList.isEmpty()){
            var userSettingsFromDB = resultList.get(0);
            userSettingsFromDB.setEducation(userSettings.getEducation());
            userSettingsFromDB.setLocation(userSettings.getLocation());
            userSettingsFromDB.setEmail(userSettings.getEmail());
            userSettingsFromDB.setDistrict(userSettings.getDistrict());
        }
        else
            entityManager.persist(userSettings);
    }

}
