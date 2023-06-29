package com.moonbright.professionals.rest.resources;

import com.moonbright.infrastructure.service.serviceRequest.LightUserRepresentationList;
import com.moonbright.professionals.record.AppointmentCreateFileDocRecord;
import com.moonbright.professionals.record.AppointmentCreateRecord;
import com.moonbright.professionals.service.AppointmentClientService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Path("/clients/appores")
public class ClientUserAppointmentResources {

    private final AppointmentClientService appointmentClientService;

    @Inject
    public ClientUserAppointmentResources(AppointmentClientService appointmentClientService) {
        this.appointmentClientService = appointmentClientService;
    }


    @GET
    @Path("/pros")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<LightUserRepresentationList> userRepresentationList(@QueryParam("firstName") String firstName,
                                                                    @QueryParam("lastName")String lastName,
                                                                    @QueryParam("location")String location){
        return  appointmentClientService.findProfessionals(lastName,firstName,location);
    }


    @POST
    @Path("/save/appo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public CompletableFuture<Boolean> saveAppointment(FormDataMultiPart multiPart){
       AppointmentCreateRecord appointmentCreateRecord =  parseMultipartBodyParts(multiPart);
       return appointmentClientService.saveAppointment(appointmentCreateRecord);
    }

    private AppointmentCreateRecord parseMultipartBodyParts(FormDataMultiPart multiPart) {
       var docsAndJsonContentGrouped = multiPart.getBodyParts().stream().collect(Collectors.groupingBy(part->part.getMediaType().equals(MediaType.TEXT_PLAIN_TYPE)));
        List<BodyPart> docListBody  = docsAndJsonContentGrouped.get(Boolean.FALSE);
        List<AppointmentCreateFileDocRecord> appointmentCreateFileDocRecordList = new ArrayList<>();
        if (docListBody != null) {
            appointmentCreateFileDocRecordList = docListBody.stream()
                    .map(part -> new AppointmentCreateFileDocRecord(
                            part.getContentDisposition().getFileName(),
                            part.getContentDisposition().getType(),
                            part.getEntityAs(InputStream.class))).toList();
        }
       return new AppointmentCreateRecord(appointmentCreateFileDocRecordList,docsAndJsonContentGrouped.get(Boolean.TRUE).get(0).getEntityAs(String.class));
    }

}
