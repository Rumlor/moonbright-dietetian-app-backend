package com.moonbright.professionals.service;

import com.moonbright.infrastructure.mapping.AppointmentMapper;
import com.moonbright.infrastructure.persistence.entity.utils.Status;
import com.moonbright.infrastructure.record.AppointmentListingRecord;
import com.moonbright.infrastructure.service.AppService;
import com.moonbright.infrastructure.service.KeycloakRestService;
import com.moonbright.professionals.entity.ProfessionalPreference;
import com.moonbright.professionals.record.DashboardInfoRecord;
import com.moonbright.professionals.record.PreferencesRecord;
import com.moonbright.professionals.repository.AppointmentForProfessionalRepository;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.time.LocalTime;
import java.util.List;

@Singleton
public class ProfessionalAppointmentService extends AppService implements AppointmentService{

    private final AppointmentForProfessionalRepository appointmentForProfessionalRepository;
    private final KeycloakRestService keycloakRestService;

    @Inject
    public ProfessionalAppointmentService(AppointmentForProfessionalRepository appointmentForProfessionalRepository, KeycloakRestService keycloakRestService){
        this.appointmentForProfessionalRepository = appointmentForProfessionalRepository;
        this.keycloakRestService = keycloakRestService;
    }
    @Override
    public PreferencesRecord savePreferences(PreferencesRecord preferencesRecord) {
        String user = getUserForRequest();
       var preference =  ProfessionalPreference.professionalPreferencesBuilder()
                                .relatedUserId(user)
               .availableDaySessionStartTime(LocalTime.parse(preferencesRecord.sessionStart()))
               .availableDaySessionEndTime(LocalTime.parse(preferencesRecord.sessionEnd()))
                                .sessionDurationInMinutes(preferencesRecord.sessionDurationInMinutes())
                                .weekendsAvailable(preferencesRecord.weekendsAvailable()).build();
       this.appointmentForProfessionalRepository.savePreferences(preference);
       return preference.toPreferenceRecord();
    }

    @Override
    public PreferencesRecord getPreferences() {
        String user = getUserForRequest();
        return this.appointmentForProfessionalRepository.findPreference(user)
                .map(ProfessionalPreference::toPreferenceRecord)
                .orElse(new PreferencesRecord(null,null,null,false));
    }

    @Override
    public PreferencesRecord getPreferences(String uid) {
        return this.appointmentForProfessionalRepository.findPreference(uid)
                .map(ProfessionalPreference::toPreferenceRecord)
                .orElse(new PreferencesRecord(null,null,null,false));
    }

    @Override
    public List<AppointmentListingRecord> findAppointmentsByStatus(String status) {
        Status appointmentStatus = null;
        List<AppointmentListingRecord> records = null;
        if (status != null) {
            appointmentStatus = Status.valueOf(status);
            records =  appointmentForProfessionalRepository
                    .findAppointmentsForProByStatus(appointmentStatus,getUserForRequest()).stream()
                    .map(AppointmentMapper.INSTANCE::toAppointmentListingRecord).toList();
        }
        records =   appointmentForProfessionalRepository
                .findAppointmentsForPro(getUserForRequest()).stream()
                .map(AppointmentMapper.INSTANCE::toAppointmentListingRecord).toList();


        for( var usr : keycloakRestService.getRealmUserList(null,null)){

        }

        return  null;
    }

    @Override
    public DashboardInfoRecord getDashboardInfo() {
        Integer pendingAppointment  = appointmentForProfessionalRepository.findAppointmentsForProByStatus(Status.PENDING, getUserForRequest()).size();
        return new DashboardInfoRecord(pendingAppointment,0);
    }
}
