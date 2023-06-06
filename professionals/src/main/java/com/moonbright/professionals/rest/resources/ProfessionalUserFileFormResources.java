package com.moonbright.professionals.rest.resources;

import com.moonbright.professionals.record.PaginatedAppFileDocRecord;
import com.moonbright.professionals.service.ApplicationFileFormService;
import com.moonbright.professionals.service.ProfessionalUserFileFormService;
import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.util.concurrent.CompletionStage;

@Path("/fifo")
public class ProfessionalUserFileFormResources {

    @Inject
    private ApplicationFileFormService fileFormService;


    @Resource
    ManagedExecutorService mes;

    @GET
    @Path("/appfiledoc/download/{fileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadAppFileDoc(@PathParam("fileId") Long fileID){
        var result = fileFormService.downloadFileDOc(fileID);
        return Response.ok(result.content())
                .header("Content-Disposition","attachment; filename="+result.fileName())
                .build();
    }

    @GET
    @Path("/appfiledoc/{page}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedAppFileDocRecord getAppFileDocs(@PathParam("page") Integer page){
        return fileFormService.findFileDocsForUser(page);
    }

    @GET
    @Path("/appfiledoc/{page}/{uid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedAppFileDocRecord getAppFileDocsForPro(@PathParam("page") Integer page,@PathParam(value = "uid") String uid){
        return fileFormService.findFileDocsForUser(page,uid);
    }
    @POST
    @Path("/appfiledoc")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Boolean> postAppFileDoc(@FormDataParam("appfiledoc") FormDataBodyPart formDataBodyPart,
                                          @FormParam("isMandatory")Boolean isMandatory){
           return    fileFormService.uploadFileForm(
                            formDataBodyPart.getFileName().orElse("doc"),
                            formDataBodyPart.getMediaType().getSubtype(),
                            formDataBodyPart.getContent(),
                            isMandatory);

    }

    @GET
    @Path("/appfiledoc/remove/{fileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean deleteAppFileDoc(@PathParam("fileId") Integer fileId){
        fileFormService.deleteFileForm((long) fileId);
        return Boolean.TRUE;
    }
}
