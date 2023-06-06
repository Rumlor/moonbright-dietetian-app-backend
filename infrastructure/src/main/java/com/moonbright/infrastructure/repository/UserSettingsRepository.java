package com.moonbright.infrastructure.repository;

import com.moonbright.infrastructure.persistence.entity.UserSettings;

import java.util.Optional;

public interface UserSettingsRepository {
    Optional<UserSettings> findUserSettings(String uid);
    void saveUserSettings(UserSettings userSettings);
}
