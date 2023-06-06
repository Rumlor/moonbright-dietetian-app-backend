package com.moonbright.professionals.entity;

import com.moonbright.infrastructure.persistence.BaseEntity;
import com.moonbright.professionals.record.PreferencesRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "professional_preference")
@Getter
@Setter
@NoArgsConstructor
public class ProfessionalPreference extends BaseEntity {

    @Column(name = "session_duration_in_minutes")
    private Long sessionDurationInMinutes;

    @Column(name = "weekends_available",columnDefinition = "boolean not null default 0")
    private Boolean weekendsAvailable;

    @Column(name = "available_day_session_start_time")
    private LocalTime availableDaySessionStartTime;

    @Column(name = "available_day_session_end_time")
    private LocalTime availableDaySessionEndTime;


    @Builder(builderMethodName = "professionalPreferencesBuilder")
    public ProfessionalPreference(Long sessionDurationInMinutes,
                                  Boolean weekendsAvailable,
                                  String relatedUserId,
                                  LocalTime availableDaySessionStartTime,
                                  LocalTime availableDaySessionEndTime) {
        super(relatedUserId);
        this.sessionDurationInMinutes = sessionDurationInMinutes;
        this.weekendsAvailable = weekendsAvailable;
        this.availableDaySessionEndTime = availableDaySessionEndTime;
        this.availableDaySessionStartTime = availableDaySessionStartTime;
    }

    public PreferencesRecord toPreferenceRecord(){
        return new PreferencesRecord(this.sessionDurationInMinutes,
                this.availableDaySessionStartTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                this.availableDaySessionEndTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                this.weekendsAvailable);
    }
}
