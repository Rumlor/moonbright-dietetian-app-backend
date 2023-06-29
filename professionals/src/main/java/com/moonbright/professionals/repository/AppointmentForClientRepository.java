package com.moonbright.professionals.repository;

import com.moonbright.infrastructure.persistence.entity.Appointment;
import com.moonbright.infrastructure.persistence.entity.UserSettings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@ApplicationScoped
public class AppointmentForClientRepository extends BaseRepository{

    public Boolean existingAppointmentForGivenSessionDate(OffsetDateTime appointmentStart, String appliedProfessional){
        var resultList = entityManager.createQuery("""
                    select a from Appointment a where a.sessionDuration.start =:start
                    and a.appliedProfessionalId =:appliedProfessional
                    """,Appointment.class)
                                .setParameter("start",appointmentStart)
                                .setParameter("appliedProfessional",appliedProfessional)
                                .getResultList();
        return !resultList.isEmpty();
    }

    public List<UserSettings> findProUserSettingsForLocation(String location){
        return entityManager.createQuery("""
                    select us from UserSettings us where us.isProfessionalUser = true and (us.location =:location or :location is null )
                """,UserSettings.class)
                .setParameter("location",location)
                .getResultList().stream().peek(entityManager::refresh).toList();
    }
    @Transactional
    public void saveAppointment(Appointment appointment){
        this.entityManager.persist(appointment);
    }
}
