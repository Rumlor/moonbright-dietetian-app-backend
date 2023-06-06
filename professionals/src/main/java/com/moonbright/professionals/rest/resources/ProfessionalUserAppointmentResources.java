package com.moonbright.professionals.rest.resources;

import com.moonbright.infrastructure.record.AppointmentListingRecord;
import com.moonbright.professionals.record.DashboardInfoRecord;
import com.moonbright.professionals.record.PreferencesRecord;
import com.moonbright.professionals.service.AppointmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/appores")
public class ProfessionalUserAppointmentResources {

    private AppointmentService appointmentService;

    @Inject
    public ProfessionalUserAppointmentResources(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GET
    @Path("/get/appointmentsByStatus")
    public List<AppointmentListingRecord> findAppointmentsByStatus(@QueryParam(value = "status")String status){
        return this.appointmentService.findAppointmentsByStatus(status);
    }

    @GET
    @Path("/get/dashboardForPro")
    public DashboardInfoRecord getDashboardData(){
        return this.appointmentService.getDashboardInfo();
    }

    @POST
    @Path("/save/preferences")
    public PreferencesRecord savePreferences(PreferencesRecord preferencesRecord){
        return this.appointmentService.savePreferences(preferencesRecord);
    }
    @GET
    @Path("/get/preferences")
    public PreferencesRecord getPreferences(){
        return this.appointmentService.getPreferences();
    }

    @GET
    @Path("/get/preferences/{id}")
    public PreferencesRecord getPreferencesForClient(@PathParam(value = "id") String uid){
        return this.appointmentService.getPreferences(uid);
    }
}
