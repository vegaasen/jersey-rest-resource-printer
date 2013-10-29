package com.vegaasen.http.rest.jersey.controller.simple;

import com.vegaasen.http.rest.jersey.controller.abs.AbstractController;
import com.vegaasen.http.rest.jersey.pointless.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
@Path("/verbs")
@Consumes("*/*")
public final class VerbsWithValuableAnnotations extends AbstractController {

    @GET
    @Consumes("*/*")
    @Path("days/{days}")
    public Response calculateDaysToMillis(
            @PathParam("days") @DefaultValue("1") final Long days
    ) {
        if (days == null || days < 0) {
            return Response.serverError().entity("Cannot be null or empty or less than 0.").build();
        }
        return Response.ok().entity(TimeUnit.DAYS.toMillis(days)).build();
    }

    @PUT
    @Path("stuff/{thing}")
    public Response modifyThing(
            @PathParam("thing") final String thing,
            @QueryParam("value") final String value
    ) {
        if (Storage.modifyStuff(thing, value)) {
            return Response.ok("Modified.").build();
        }
        return Response.notModified().entity("Seems like there is no such element..or somthing.").build();
    }

}
