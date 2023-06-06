package com.moonbright.professionals.service;

import com.moonbright.infrastructure.record.AppointmentListingRecord;
import com.moonbright.professionals.record.DashboardInfoRecord;
import com.moonbright.professionals.record.PreferencesRecord;

import java.util.List;

public interface AppointmentService {
    PreferencesRecord savePreferences(PreferencesRecord preferencesRecord);
    PreferencesRecord getPreferences();
    PreferencesRecord getPreferences(String uid);
    List<AppointmentListingRecord> findAppointmentsByStatus(String  status);
    DashboardInfoRecord getDashboardInfo();
}
