package com.moonbright.professionals.rest.resources;

import com.moonbright.infrastructure.record.UserSettingsRecord;
import com.moonbright.infrastructure.service.UserSettingsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.Optional;

@Path("/sttngs/usr")
public class UserSettingsResources {

    private final UserSettingsService service;

    @Inject
    public UserSettingsResources(UserSettingsService service) {
        this.service = service;
    }


    @POST
    public Boolean saveSettings(UserSettingsRecord record){
        service.saveSettings(record);
        return Boolean.TRUE;
    }
    @GET
    public UserSettingsRecord findUserSettings(){
        return Optional.ofNullable(service.findUserSettings())
                .orElse(new UserSettingsRecord(null,null,null,false,null));
    }

}
