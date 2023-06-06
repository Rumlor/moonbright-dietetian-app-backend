package com.moonbright.clients.record;

import java.util.List;

public record AppointmentCreateRecord(List<AppointmentCreateFileDocRecord> fileDocRecordList,String jsonContent) {
}
