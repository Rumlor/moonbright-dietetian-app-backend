package com.moonbright.professionals.repository;

import com.moonbright.infrastructure.persistence.entity.Appointment;
import com.moonbright.infrastructure.persistence.entity.utils.Status;
import com.moonbright.professionals.entity.ProfessionalPreference;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AppointmentForProfessionalRepository extends BaseRepository {


    public ProfessionalPreference findPreferenceByUid(String uid){
     return    entityManager
                .createQuery("select p from ProfessionalPreference p where p.relatedUserId =:uid",ProfessionalPreference.class)
                .setParameter("uid",uid)
             .getResultList().stream().findFirst().orElse(null);
    }

    public void savePreferences(ProfessionalPreference professionalPreference){
        var result =
                Optional.ofNullable(this.findPreferenceByUid(professionalPreference.getRelatedUserId()))
                .map(updated->{
                    updated.setWeekendsAvailable(professionalPreference.getWeekendsAvailable());
                    updated.setSessionDurationInMinutes(professionalPreference.getSessionDurationInMinutes());
                    updated.setAvailableDaySessionEndTime(professionalPreference.getAvailableDaySessionEndTime());
                    updated.setAvailableDaySessionStartTime(professionalPreference.getAvailableDaySessionStartTime());
                    return updated;
                })
                .orElse(professionalPreference);
        entityManager.persist(result);
    }

    public Optional<ProfessionalPreference> findPreference(String uid){
        return entityManager.createQuery("select p from ProfessionalPreference p where p.relatedUserId =:uid", ProfessionalPreference.class)
                .setParameter("uid",uid)
                .getResultList().stream().findFirst();
    }

    public List<Appointment> findAppointmentsForProByStatus(Status status, String userForRequest){
        return entityManager.createQuery("select s from Appointment s where s.appointmentStatus = ?1 and s.appliedProfessionalId = ?2",Appointment.class)
                .setParameter(1,status)
                .setParameter(2,userForRequest)
                .getResultList();
    }
    public List<Appointment> findAppointmentsForPro(String userForRequest){
        return entityManager.createQuery("select s from Appointment s where s.appliedProfessionalId = ?1",Appointment.class)
                .setParameter(1,userForRequest)
                .getResultList();
    }
}
