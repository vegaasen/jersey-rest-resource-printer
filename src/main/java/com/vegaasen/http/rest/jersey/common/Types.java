package com.vegaasen.http.rest.jersey.common;

/**
 * This contains all the registered types that exists within a Jersey-context.
 *
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class Types {

    public static final String EMPTY = "";
    public static final String PATH = "Path";
    public static final String CONSUMES = "Consumes";
    public static final String PRODUCES = "Produces";
    public static final String HTTP_METHOD = "HttpMethod";
    public static final String APPLICATION_PATH = "ApplicationPath";
    public static final String DEFAULT_VALUE = "DefaultValue";
    public static final String PATH_PARAM = "PathParam";
    public static final String QUERY_PARAM = "QueryParam";
    public static final String HEADER_PARAM = "HeaderParam";
    public static final String FORM_PARAM = "FormParam";
    public static final String COOKIE_PARAM = "CookieParam";
    public static final String MATRIX_PARAM = "MatrixParam";

    private Types() {
    }

}
