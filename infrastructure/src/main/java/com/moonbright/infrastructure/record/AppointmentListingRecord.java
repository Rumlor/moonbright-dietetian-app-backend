package com.moonbright.infrastructure.record;

import com.moonbright.infrastructure.persistence.entity.utils.Status;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;

public record AppointmentListingRecord(OffsetDateTime appointmentCreateDate,
                                       String uid,
                                       String clientName,
                                       String email,
                                       OffsetDateTime sessionStartDate,
                                       List<FileDocRecord> fileDocRecordList,
                                       Status appointmentStatus) {
}
