package com.moonbright.professionals.record;

import java.util.List;

public record AppointmentCreateRecord(List<AppointmentCreateFileDocRecord> fileDocRecordList,String jsonContent) {
}
