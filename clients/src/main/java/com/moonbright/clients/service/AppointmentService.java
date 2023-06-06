package com.moonbright.clients.service;

import com.moonbright.clients.record.AppointmentCreateRecord;
import com.moonbright.infrastructure.service.serviceRequest.LightUserRepresentationList;
import com.moonbright.infrastructure.service.serviceRequest.UserRepresentationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AppointmentService {
    CompletableFuture<Boolean> saveAppointment(AppointmentCreateRecord record);

    List<LightUserRepresentationList> findProfessionals(String lastName, String firstName, String location);
}
