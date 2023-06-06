package com.moonbright.infrastructure.mapping;

import com.moonbright.infrastructure.persistence.entity.Appointment;
import com.moonbright.infrastructure.record.AppointmentCreateRecordWithoutDocInfo;
import com.moonbright.infrastructure.record.AppointmentListingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = FileDocMapper.class)
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(source = "appliedProfessionalId",target = "appliedProfessional")
    @Mapping(source = "sessionDuration.start",target = "appointmentStart")
    @Mapping(source = "sessionDuration.end",target = "appointmentEnd")
    @Mapping(source = "appointmentStatus",target = "status",defaultValue = "PENDING")
    AppointmentCreateRecordWithoutDocInfo toAppointmentCreateRecord(Appointment appointment);

    @Mapping(source = "appliedProfessional",target = "appliedProfessionalId")
    @Mapping(source = "appointmentStart",target = "sessionDuration.start")
    @Mapping(source = "appointmentEnd",target = "sessionDuration.end")
    @Mapping(source = "status",target = "appointmentStatus",defaultValue = "PENDING")
    Appointment toAppointment(AppointmentCreateRecordWithoutDocInfo appointment);

    @Mapping(source = "sessionDuration.start",target = "sessionStartDate")
    @Mapping(source = "appointmentDocuments",target = "fileDocRecordList")
    @Mapping(source = "relatedUserId",target = "uid")
    @Mapping(source = "appointmentCreateDate",target = "appointmentCreateDate")
    @Mapping(source = "appointmentStatus",target = "appointmentStatus",defaultValue = "PENDING")
    AppointmentListingRecord toAppointmentListingRecord(Appointment appointment);
}
