package com.vegaasen.http.rest.jersey.controller.basic;

import com.vegaasen.http.rest.jersey.controller.abs.AbstractController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
@Path("/basic")
public final class VeryBasicController extends AbstractController {

    public static final int the_answer_is = 42;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response simpleRequest() {
        return Response.ok().entity(the_answer_is).build();
    }

}
