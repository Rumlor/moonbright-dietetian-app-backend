package com.moonbright.professionals.record;

import java.time.LocalTime;

public record PreferencesRecord (Long sessionDurationInMinutes,
                                 String sessionStart,
                                 String sessionEnd,
                                 Boolean weekendsAvailable){
}
