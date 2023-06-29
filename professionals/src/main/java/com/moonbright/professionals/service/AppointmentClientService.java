package com.moonbright.professionals.service;

import com.moonbright.infrastructure.service.serviceRequest.LightUserRepresentationList;
import com.moonbright.professionals.record.AppointmentCreateRecord;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AppointmentClientService {
    CompletableFuture<Boolean> saveAppointment(AppointmentCreateRecord record);

    List<LightUserRepresentationList> findProfessionals(String lastName, String firstName, String location);
}
