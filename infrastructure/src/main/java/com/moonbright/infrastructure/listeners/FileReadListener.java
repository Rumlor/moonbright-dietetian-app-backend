package com.moonbright.infrastructure.listeners;

import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.concurrent.ManagedTaskListener;

import java.util.concurrent.Future;

public class FileReadListener implements ManagedTaskListener {

    @Override
    public void taskSubmitted(Future<?> future, ManagedExecutorService executor, Object task) {

    }

    @Override
    public void taskAborted(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {

    }

    @Override
    public void taskDone(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {

    }

    @Override
    public void taskStarting(Future<?> future, ManagedExecutorService executor, Object task) {

    }
}
