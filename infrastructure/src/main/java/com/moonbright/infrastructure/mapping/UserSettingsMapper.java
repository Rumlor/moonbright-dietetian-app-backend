package com.moonbright.infrastructure.mapping;

import com.moonbright.infrastructure.persistence.entity.UserSettings;
import com.moonbright.infrastructure.record.UserSettingsRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserSettingsMapper {
    UserSettingsMapper INSTANCE = Mappers.getMapper(UserSettingsMapper.class);

    @Mapping(source = "email",target = "email")
    @Mapping(source = "location",target = "location")
    @Mapping(source = "education",target = "education")
    @Mapping(source = "isProfessional",target = "isProfessionalUser")
    @Mapping(source = "district",target = "district")
    UserSettings toUserSettings(UserSettingsRecord record);

    @Mapping(source = "email",target = "email")
    @Mapping(source = "location",target = "location")
    @Mapping(source = "education",target = "education")
    @Mapping(source = "district",target = "district")
    UserSettingsRecord toUserSettingsRecord(UserSettings record);
}
