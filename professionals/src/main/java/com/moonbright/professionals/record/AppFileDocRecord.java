package com.moonbright.professionals.record;
import com.moonbright.infrastructure.record.FileDocRecord;

public record AppFileDocRecord (boolean isMandatory, FileDocRecord file) {
}
