package com.vegaasen.http.rest.jersey.utils;

import com.google.common.base.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JerseyResourceUtils {

    private static final Logger LOG = Logger.getLogger(JerseyResourceUtils.class.getName());
    public static final String
            CLASS_IDENTIFIER = "this",
            CLASS_DD = "class-",
            SEPARATOR = "-";

    private JerseyResourceUtils() {
    }

    public static class ByPackage {

    }

    public static class ByClass {

        /**
         * [Class, [Method, [AnnotationType, AnnotationValue]]]
         *
         * @param clazz _
         * @return _
         */
        public static Map<String, Map<String, Map<String, String>>> getAnnotationsForClass(final Class clazz) {
            if (clazz != null) {
                final Map<String, Map<String, Map<String, String>>> detectedAnnotations = new TreeMap<>();
                if (clazz.getMethods() != null && clazz.getMethods().length > 0) {
                    final Map<String, Map<String, String>> detectedMethods = new LinkedHashMap<>();
                    for (Method method : clazz.getMethods()) {
                        if (method.getAnnotations() != null && method.getAnnotations().length > 0) {
                            Map<String, String> detectedMethodAnnotations = new HashMap<>();
                            for (Annotation a : method.getAnnotations()) {
                                if (a != null) {
                                    detectedMethodAnnotations.putAll(
                                            JerseyAnnotationAssembler.getJerseyAnnotationInformation(a)
                                    );
                                }
                            }
                            if (!detectedMethodAnnotations.isEmpty()) {
                                detectedMethods.put(method.getName(), detectedMethodAnnotations);
                            }
                        }
                    }
                    if (clazz.getAnnotations() != null && clazz.getAnnotations().length > 0) {
                        final Map<String, String> clazzMethodAnnotations = new HashMap<>();
                        for (Annotation a : clazz.getAnnotations()) {
                            if (a != null) {
                                clazzMethodAnnotations.putAll(
                                        JerseyAnnotationAssembler.getJerseyAnnotationInformation(a)
                                );
                            }
                        }
                        if (!clazzMethodAnnotations.isEmpty()) {
                            detectedMethods.put(getClassIdentifier(clazz), clazzMethodAnnotations);
                        }
                    }
                    if (!detectedMethods.isEmpty()) {
                        detectedAnnotations.put(clazz.getName(), detectedMethods);
                    }
                }
                return detectedAnnotations;
            }
            LOG.warning("Provided class was null.");
            return Collections.emptyMap();
        }

        private static String getClassIdentifier(final Class clazz) {
            String id = String.format("%s%s%s", CLASS_DD, SEPARATOR, CLASS_IDENTIFIER);
            if (clazz != null && !Strings.isNullOrEmpty(clazz.getSimpleName())) {
                id = String.format("%s%s%s", CLASS_DD, SEPARATOR, clazz.getSimpleName());
            }
            return id;
        }

    }

}
