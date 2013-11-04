package com.vegaasen.http.rest.jersey.utils;

import com.vegaasen.http.rest.jersey.common.Types;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Todo: Improve massively
 *
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
final class JerseyAnnotationAssembler {

    private static final Logger LOG = Logger.getLogger(JerseyAnnotationAssembler.class.getName());
    private static final String DEFAULT_NON_VALUABLE_ANNOTATION = "true";

    private JerseyAnnotationAssembler() {
    }

    public static Map<String, String> getJerseyAnnotationInformation(final Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            Map<String, String> annotationInformation = new HashMap<>();
            for (Annotation annotation : annotations) {
                annotationInformation.putAll(getJerseyAnnotationInformation(annotation));
            }
            return annotationInformation;
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> getJerseyAnnotationInformation(final Annotation annotation) {
        if (annotation != null) {
            Map<String, String> information = new HashMap<>();
            if (isValuableAnnotation(annotation)) {
                information.put(annotation.annotationType().getSimpleName(),
                        JerseyValueAssembler.getValueByAnnotation(annotation));
            } else if (isNonValuableAnnotation(annotation)) {
                information.put(annotation.annotationType().getSimpleName(), DEFAULT_NON_VALUABLE_ANNOTATION);
            } else {
                LOG.info("Unhandled annotation: " + annotation.toString());
            }
            return information;
        }
        return Collections.emptyMap();
    }

    public static boolean isValidJerseyAnnotation(final Annotation... annotations) {
        boolean valid = false;
        for (Annotation a : annotations) {
            if (isValuableAnnotation(a) || isNonValuableAnnotation(a)) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    private static boolean isValuableAnnotation(final Annotation annotation) {
        return (
                annotation instanceof Path ||
                        annotation instanceof PathParam ||
                        annotation instanceof ApplicationPath ||
                        annotation instanceof QueryParam ||
                        annotation instanceof HeaderParam ||
                        annotation instanceof CookieParam ||
                        annotation instanceof HttpMethod ||
                        annotation instanceof FormParam ||
                        annotation instanceof MatrixParam ||
                        annotation instanceof Consumes ||
                        annotation instanceof Produces ||
                        annotation instanceof DefaultValue
        );
    }

    private static boolean isNonValuableAnnotation(final Annotation annotation) {
        return (
                annotation instanceof GET ||
                        annotation instanceof POST ||
                        annotation instanceof PUT ||
                        annotation instanceof DELETE ||
                        annotation instanceof HEAD ||
                        annotation instanceof OPTIONS
        );
    }

    private static String getSimpleNameByAnnotation(final Annotation annotation) {
        return Types.EMPTY;
    }

}
