package com.vegaasen.http.rest.jersey.utils;

import com.google.common.base.Strings;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
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

        public static Map<String, Map<String, Map<String, String>>> getAnnotationsFromBasePackage(final String packageName) {
            if (Strings.isNullOrEmpty(packageName)) {
                return Collections.emptyMap();
            }
            try {
                final ClassPath classPath = ClassPath.from(JerseyResourceUtils.class.getClassLoader());
                final Set<ClassPath.ClassInfo> info = classPath.getTopLevelClassesRecursive(packageName);
                if (info != null && info.size() > 0) {
                    return detectClasses(info);
                }
            } catch (IOException e) {
                LOG.severe(
                        String.format(
                                "Unable to find classes or similar in the provided package-structure {%s}. Halting.",
                                packageName
                        )
                );
            }
            return Collections.emptyMap();
        }

        private static Map<String, Map<String, Map<String, String>>> detectClasses(Set<ClassPath.ClassInfo> classes) {
            if (classes != null && !classes.isEmpty()) {
                final Map<String, Map<String, Map<String, String>>> detectedClasses = new TreeMap<>();
                for (ClassPath.ClassInfo i : classes) {
                    if (i != null && i.getPackageName() != null) {
                        try {
                            detectedClasses.putAll(ByClass.getAnnotationsForClass(Class.forName(i.getName())));
                        } catch (ClassNotFoundException e) {
                            LOG.severe(String.format("Unable to get annotations for class. Reason: \n%s", e.getMessage()));
                        }
                    }
                }
                return detectedClasses;
            }
            return Collections.emptyMap();
        }

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
                //method-level annotations
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
                    //class-level annotations
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
                        detectedAnnotations.put(clazz.getSimpleName(), detectedMethods);
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

    public enum Variant {
        METHOD, PARAMETERS
    }

}
