package com.moonbright.infrastructure.record;

public record FileDocDownloadRecord (String fileName, byte[] content, String fileFormat){
}
