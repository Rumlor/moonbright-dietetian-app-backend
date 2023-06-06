package com.moonbright.clients.record;

import java.io.InputStream;

public record AppointmentCreateFileDocRecord(String fileName, String fileFormat, InputStream fileContentStream ) {
}
