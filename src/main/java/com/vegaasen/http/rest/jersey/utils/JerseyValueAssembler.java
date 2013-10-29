package com.vegaasen.http.rest.jersey.utils;

import com.vegaasen.http.rest.jersey.common.Types;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
final class JerseyValueAssembler {

    private static final Logger LOG = Logger.getLogger(JerseyValueAssembler.class.getName());

    private JerseyValueAssembler() {
    }

    /**
     * Must map to constants
     *
     * @param annotation the annotation in question
     * @return the value by valuable annotation
     */
    static String getValueByAnnotation(final Annotation annotation) {
        String value = Types.EMPTY;
        switch (annotation.annotationType().getSimpleName()) {
            case Types.PATH:
                value = ValueUtils.assembleValue(((Path) annotation).value());
                break;
            case Types.CONSUMES:
                value = ValueUtils.assembleValue(((Consumes) annotation).value());
                break;
            case Types.PRODUCES:
                value = ValueUtils.assembleValue(((Produces) annotation).value());
                break;
            case Types.HTTP_METHOD:
                value = ValueUtils.assembleValue(((HttpMethod) annotation).value());
                break;
            case Types.APPLICATION_PATH:
                value = ValueUtils.assembleValue(((ApplicationPath) annotation).value());
                break;
            case Types.DEFAULT_VALUE:
                value = ValueUtils.assembleValue(((DefaultValue) annotation).value());
                break;
            case Types.COOKIE_PARAM:
                value = ValueUtils.assembleValue(((CookieParam) annotation).value());
                break;
            case Types.FORM_PARAM:
                value = ValueUtils.assembleValue(((FormParam) annotation).value());
                break;
            case Types.HEADER_PARAM:
                value = ValueUtils.assembleValue(((HeaderParam) annotation).value());
                break;
            case Types.PATH_PARAM:
                value = ValueUtils.assembleValue(((PathParam) annotation).value());
                break;
            case Types.QUERY_PARAM:
                value = ValueUtils.assembleValue(((QueryParam) annotation).value());
                break;
            case Types.MATRIX_PARAM:
                value = ValueUtils.assembleValue(((MatrixParam) annotation).value());
                break;
            default:
                LOG.info(String.format("Unknown annotation {%s}.", annotation.toString()));
                break;
        }
        return value;
    }

}
