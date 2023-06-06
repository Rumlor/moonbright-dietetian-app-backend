package com.moonbright.infrastructure.record;

import java.time.LocalDateTime;

public record BaseUserRecord(String email, String password, String username,
                             String name, String lastname, LocalDateTime registryDate,
                             LocalDateTime lastLoginDate, Boolean emailValidated) {
}
