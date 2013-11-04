package com.vegaasen.http.rest.jersey.controller.abs;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public abstract class AbstractController {

    @Context
    protected HttpServletRequest httpServletRequest;
    @Context
    protected ServletContext context;

}
