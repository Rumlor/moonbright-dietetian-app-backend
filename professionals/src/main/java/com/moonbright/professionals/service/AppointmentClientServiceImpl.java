package com.moonbright.professionals.service;


import com.moonbright.infrastructure.error.ErrorCodeAndDescription;
import com.moonbright.infrastructure.error.exception.BaseErrorResponse;
import com.moonbright.infrastructure.listeners.FileReaderSupplier;
import com.moonbright.infrastructure.mapping.AppointmentMapper;
import com.moonbright.infrastructure.persistence.entity.Appointment;
import com.moonbright.infrastructure.persistence.entity.FileDoc;
import com.moonbright.infrastructure.record.AppointmentCreateRecordWithoutDocInfo;
import com.moonbright.infrastructure.record.UserSettingsRelatedIdAndLocationRecord;
import com.moonbright.infrastructure.service.AppService;
import com.moonbright.infrastructure.service.KeycloakRestService;
import com.moonbright.infrastructure.service.serviceRequest.LightUserRepresentationList;
import com.moonbright.infrastructure.util.JsonUtility;
import com.moonbright.professionals.record.AppointmentCreateRecord;
import com.moonbright.professionals.repository.AppointmentForClientRepository;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Singleton
public class AppointmentClientServiceImpl extends AppService implements AppointmentClientService {

    private final AppointmentForClientRepository appointmentForClientRepository;
    private final KeycloakRestService keycloakDefaultRestService;

    private final JsonUtility jsonUtility;
    @Resource
    private ManagedExecutorService managedExecutor;

    @Inject
    public AppointmentClientServiceImpl(AppointmentForClientRepository appointmentForClientRepository, KeycloakRestService keycloakDefaultRestService, JsonUtility jsonUtility){
        this.appointmentForClientRepository = appointmentForClientRepository;
        this.keycloakDefaultRestService = keycloakDefaultRestService;
        this.jsonUtility = jsonUtility;
    }


    private void checkExistingAppointment(AppointmentCreateRecordWithoutDocInfo appointmentCreateRecordWithoutDocInfo) {
        Boolean existing = this.appointmentForClientRepository
                .existingAppointmentForGivenSessionDate(appointmentCreateRecordWithoutDocInfo.appointmentStart(),
                        appointmentCreateRecordWithoutDocInfo.appliedProfessional());
        if (existing)
            throw new WebApplicationException(Response.status(500).entity(
                    BaseErrorResponse
                            .fromErrorCode(ErrorCodeAndDescription.CLIENT_APPOINTMENT_ALREADY_EXISTS_ERROR_CODE)).build());
    }

    @Override
    public CompletableFuture<Boolean> saveAppointment(AppointmentCreateRecord record) {
        AppointmentCreateRecordWithoutDocInfo appointmentCreateRecordWithoutDocInfo = parseJsonContent(record.jsonContent());
        checkExistingAppointment(appointmentCreateRecordWithoutDocInfo);
        String uid = getUserForRequest();
        List<FileDoc> fileDocList = new ArrayList<>();
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        readFileStreamsAsync(record, fileDocList,uid, completableFutures);
        return CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[record.fileDocRecordList().size()]))
                .thenRun(()->prepareAppointmentAndPersist(appointmentCreateRecordWithoutDocInfo, fileDocList,uid))
                .thenApply(res->Boolean.TRUE);
    }

    @Override
    public List<LightUserRepresentationList> findProfessionals(String lastName, String firstName, String location) {

        List<UserSettingsRelatedIdAndLocationRecord> userSettings = new ArrayList<>(filterUserSettings(location));
        if (userSettings.isEmpty()){
            return Collections.emptyList();
        }else {
            var list = new ArrayList<>(filterUsersFromKeycloakServer(lastName, firstName, userSettings));
            list.sort(Comparator.comparing(UserRepresentation::getId));
            userSettings.sort(Comparator.comparing(UserSettingsRelatedIdAndLocationRecord::relatedUserId));
            return list.stream().map(userRepresentation ->
                    createLightUserRepresentationList(userSettings, list, userRepresentation)).toList();
        }

    }

    private static LightUserRepresentationList createLightUserRepresentationList(List<UserSettingsRelatedIdAndLocationRecord> userSettings, ArrayList<UserRepresentation> list, UserRepresentation userRepresentation) {
        return new LightUserRepresentationList(userRepresentation.getFirstName(),
                userRepresentation.getLastName(),
                userRepresentation.getEmail(),
                userSettings.get(list.indexOf(userRepresentation)).location(),
                userSettings.get(list.indexOf(userRepresentation)).district(),
                userRepresentation.getId(),
                userRepresentation.getCreatedTimestamp());
    }

    private List<UserSettingsRelatedIdAndLocationRecord> filterUserSettings(String location) {
        return appointmentForClientRepository.findProUserSettingsForLocation(location)
                .stream().map(usr -> new UserSettingsRelatedIdAndLocationRecord(usr.getRelatedUserId(), usr.getLocation(), usr.getDistrict())).toList();
    }

    private List<UserRepresentation> filterUsersFromKeycloakServer(String lastName, String firstName, List<UserSettingsRelatedIdAndLocationRecord> userSettings) {
        return keycloakDefaultRestService.getRealmUserList(firstName, lastName)
                .stream().filter(usr ->
                        userSettings.stream().map(UserSettingsRelatedIdAndLocationRecord::relatedUserId).toList()
                                .contains(usr.getId())).toList();
    }

    private void readFileStreamsAsync(AppointmentCreateRecord record, List<FileDoc> fileDocList, String uid, List<CompletableFuture<Void>> completableFutures) {
        for (var fileRecord : record.fileDocRecordList()){

            FileDoc fileDoc = new FileDoc(fileRecord.fileFormat(),fileRecord.fileName(), uid);
            fileDocList.add(fileDoc);
            completableFutures.add(
                    CompletableFuture
                    .supplyAsync(new FileReaderSupplier(fileRecord.fileContentStream()))
                    .thenAccept(fileDoc::setContent));

        }
    }

    private void prepareAppointmentAndPersist(AppointmentCreateRecordWithoutDocInfo appointmentCreateRecordWithoutDocInfo, List<FileDoc> fileDocList, String userForRequest) {
        Appointment appointment = AppointmentMapper.INSTANCE.toAppointment(appointmentCreateRecordWithoutDocInfo);
        appointment.setAppointmentDocuments(fileDocList);
        appointment.setRelatedUserId(userForRequest);
        appointmentForClientRepository.saveAppointment(appointment);
    }


    private  AppointmentCreateRecordWithoutDocInfo parseJsonContent(String content) {
        AppointmentCreateRecordWithoutDocInfo appointmentCreateRecordWithoutDocInfo;
        try {
            appointmentCreateRecordWithoutDocInfo =
                    jsonUtility.getJsonb().fromJson(content,AppointmentCreateRecordWithoutDocInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appointmentCreateRecordWithoutDocInfo;
    }

}
