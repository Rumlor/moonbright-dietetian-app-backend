package com.moonbright.infrastructure.record;

import com.moonbright.infrastructure.persistence.entity.utils.Status;

import java.time.OffsetDateTime;

public record AppointmentCreateRecordWithoutDocInfo(String appliedProfessional,
                                                    OffsetDateTime appointmentStart,
                                                    OffsetDateTime appointmentEnd, Status status) {
}
