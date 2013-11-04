package com.vegaasen.http.rest.jersey.utils;

import com.google.common.base.Strings;
import com.google.common.reflect.ClassPath;
import com.vegaasen.http.rest.jersey.model.ClassInfo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * The cccccccccrazyness!
 *
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
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

        public static Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> getAnnotationsFromBasePackage(
                final String pckg) {
            if (Strings.isNullOrEmpty(pckg)) {
                return Collections.emptyMap();
            }
            try {
                final ClassPath classPath = ClassPath.from(JerseyResourceUtils.class.getClassLoader());
                final Set<ClassPath.ClassInfo> info = classPath.getTopLevelClassesRecursive(pckg);
                if (info != null && info.size() > 0) {
                    return detectClasses(info);
                }
            } catch (IOException e) {
                LOG.severe(
                        String.format(
                                "Unable to find classes or similar in the provided package-structure {%s}. Halting.",
                                pckg
                        )
                );
            }
            return Collections.emptyMap();
        }

        private static Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> detectClasses(final Set<ClassPath.ClassInfo> classes) {
            if (classes != null && !classes.isEmpty()) {
                final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> detectedClasses = new TreeMap<>();
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
        public static Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> getAnnotationsForClass(final Class clazz) {
            if (clazz != null) {
                ClassInfo classInfo = new ClassInfo();
                classInfo.setName(clazz.getSimpleName());
                classInfo.setAccessed(System.currentTimeMillis());
                classInfo.setPath(clazz.getName());
                final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> detectedAnnotations = new TreeMap<>();
                if (clazz.getMethods() != null && clazz.getMethods().length > 0) {
                    final Map<String, Map<String, Map<String, Map<String, String>>>> detectedMethods = new LinkedHashMap<>();
                    for (Method method : clazz.getMethods()) {
                        boolean valid = false;
                        final Map<String, Map<String, Map<String, String>>> methodAnnotations = new HashMap<>();
                        final Map<String, Map<String, String>> mannot = new HashMap<>();
                        if (method.getAnnotations() != null && method.getAnnotations().length > 0) {
                            final Map<String, String> detectedMethodAnnotations = new HashMap<>();
                            for (final Annotation a : method.getAnnotations()) {
                                if (a != null && JerseyAnnotationAssembler.isValidJerseyAnnotation(a)) {
                                    valid = true;
                                    detectedMethodAnnotations.putAll(
                                            JerseyAnnotationAssembler.getJerseyAnnotationInformation(a)
                                    );
                                }
                            }
                            if (!detectedMethodAnnotations.isEmpty()) {
                                mannot.put(AnnotationLocation.METHOD.getId(), detectedMethodAnnotations);
                            }
                        }
                        if (method.getParameterAnnotations() != null && method.getParameterAnnotations().length > 0) {
                            final Map<String, String> detectedParameterAnnotations = new HashMap<>();
                            for (final Annotation[] a : method.getParameterAnnotations()) {
                                if (a != null && JerseyAnnotationAssembler.isValidJerseyAnnotation(a)) {
                                    detectedParameterAnnotations.putAll(
                                            JerseyAnnotationAssembler.getJerseyAnnotationInformation(a)
                                    );
                                }
                            }
                            if (!detectedParameterAnnotations.isEmpty()) {
                                mannot.put(AnnotationLocation.PARAMETER.getId(), detectedParameterAnnotations);
                            }
                        }
                        if (valid) {
                            methodAnnotations.put(method.getName(), mannot);
                            detectedMethods.put(method.getName(), methodAnnotations);
                        }
                    }
                    //class-level annotations
                    if (clazz.getAnnotations() != null && clazz.getAnnotations().length > 0) {
                        final Map<String, Map<String, Map<String, String>>> assembledClazzMethodAnnotations = new HashMap<>();
                        final Map<String, String> clazzMethodAnnotations = new HashMap<>();
                        for (Annotation a : clazz.getAnnotations()) {
                            if (a != null) {
                                clazzMethodAnnotations.putAll(
                                        JerseyAnnotationAssembler.getJerseyAnnotationInformation(a)
                                );
                            }
                        }
                        if (!clazzMethodAnnotations.isEmpty()) {
                            final Map<String, Map<String, String>> m = new HashMap<>();
                            m.put(AnnotationLocation.METHOD.getId(), clazzMethodAnnotations);
                            assembledClazzMethodAnnotations.put(getClassIdentifier(clazz), m);
                            detectedMethods.put("self", assembledClazzMethodAnnotations);
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

    public enum AnnotationLocation {
        METHOD("method"), PARAMETER("parameter");

        private String id;

        private AnnotationLocation(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

}
