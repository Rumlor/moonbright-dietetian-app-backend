package com.moonbright.infrastructure.service;

import com.moonbright.infrastructure.mapping.UserSettingsMapper;
import com.moonbright.infrastructure.persistence.entity.UserSettings;
import com.moonbright.infrastructure.record.UserSettingsRecord;
import com.moonbright.infrastructure.repository.UserSettingsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

@ApplicationScoped
@NoArgsConstructor
public class UserSettingsService extends AppService {

    private UserSettingsRepository userSettingsRepository;

    @Inject
    public UserSettingsService(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public void saveSettings(UserSettingsRecord record){
        var userSettings = UserSettingsMapper.INSTANCE.toUserSettings(record);
        userSettings.setRelatedUserId(getUserForRequest());
        userSettingsRepository.saveUserSettings(userSettings);
    }

    public UserSettingsRecord findUserSettings(){
        var userSettings = userSettingsRepository.findUserSettings(getUserForRequest());
        return UserSettingsMapper.INSTANCE.toUserSettingsRecord(userSettings.orElse(new UserSettings()));
    }
}
