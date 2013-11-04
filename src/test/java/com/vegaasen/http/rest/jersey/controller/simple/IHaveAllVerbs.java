package com.vegaasen.http.rest.jersey.controller.simple;

import com.vegaasen.http.rest.jersey.controller.abs.AbstractController;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * "Controller" that exposes all the various verbs current supported in jersey
 *
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 * @version jersey-1.17.1
 */
@Path("/simple")
public final class IHaveAllVerbs extends AbstractController {

    @GET
    public Response get() {
        return Response.ok().build();
    }

    @POST
    public Response post() {
        return Response.ok().build();
    }

    @PUT
    public Response put() {
        return Response.ok().build();
    }

    @DELETE
    public Response delete() {
        return Response.ok().build();
    }

    @HEAD
    public Response head() {
        return Response.ok().build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

}
