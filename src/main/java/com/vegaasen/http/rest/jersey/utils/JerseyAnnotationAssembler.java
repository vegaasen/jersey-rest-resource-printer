package com.vegaasen.http.rest.jersey.utils;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
final class JerseyAnnotationAssembler {

    private static final Logger LOG = Logger.getLogger(JerseyAnnotationAssembler.class.getName());

    private JerseyAnnotationAssembler() {
    }

    public static Map<String, String> getJerseyAnnotationInformation(final Annotation annotation) {
        if (annotation != null) {
            Map<String, String> information = new HashMap<>();
            if (annotation instanceof Path) {
                information.put("Path", ((Path) annotation).value());
            } else if (annotation instanceof GET) {
                information.put("GET", "true");
            } else if (annotation instanceof POST) {
                information.put("POST", "true");
            } else {
                LOG.info("Unhandled annotation: " + annotation.toString());
            }
            return information;
        }
        return Collections.emptyMap();
    }

}
