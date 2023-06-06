package com.moonbright.infrastructure.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public class FileReaderSupplier implements Supplier<byte[]> {

    private final InputStream inputStream;

    public FileReaderSupplier(InputStream inputStream){
        this.inputStream = inputStream;
    }
    @Override
    public byte[] get() {
        try(inputStream) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
