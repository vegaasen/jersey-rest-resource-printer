package com.vegaasen.http.rest.jersey.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
@Path("/")
public final class DefaultController {

    @GET
    @Path("simple")
    @Produces(MediaType.TEXT_PLAIN)
    public Response simpleRequest() {
        return Response.ok().entity("Hei").build();
    }

}
