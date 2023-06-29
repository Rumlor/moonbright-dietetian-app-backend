package com.moonbright.professionals.record;

import java.io.InputStream;

public record AppointmentCreateFileDocRecord(String fileName, String fileFormat, InputStream fileContentStream ) {
}
