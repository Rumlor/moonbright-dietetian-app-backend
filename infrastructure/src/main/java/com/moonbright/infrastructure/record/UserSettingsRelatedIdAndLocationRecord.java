package com.moonbright.infrastructure.record;


import java.util.Objects;

public record UserSettingsRelatedIdAndLocationRecord(String relatedUserId, String location, String district) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSettingsRelatedIdAndLocationRecord that = (UserSettingsRelatedIdAndLocationRecord) o;
        return Objects.equals(relatedUserId, that.relatedUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relatedUserId);
    }
}
