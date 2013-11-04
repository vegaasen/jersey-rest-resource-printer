package com.vegaasen.http.rest.jersey.utils;

import com.google.common.base.Strings;
import com.google.common.reflect.ClassPath;
import com.vegaasen.http.rest.jersey.model.ClassInfo;
import com.vegaasen.http.rest.jersey.model.PackageInfo;
import com.vegaasen.http.rest.jersey.model.clazz.AnnotationSpec;
import com.vegaasen.http.rest.jersey.model.clazz.MethodInfo;
import com.vegaasen.http.rest.jersey.model.clazz.Parameter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * C.R.A.Z.Y.S.H.I.T.G.O.I.N.G.O.N
 *
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class JerseyResourceUtils {

    private static final Logger LOG = Logger.getLogger(JerseyResourceUtils.class.getName());
    private static final String
            CLASS_IDENTIFIER = "this",
            CLASS_DD = "class-",
            SEPARATOR = "-",
            CLASS_REF_ID = "self",
            EMPTY = "";

    private JerseyResourceUtils() {
    }

    public static class ByPackage {

        public static PackageInfo getWrappedInformation(final String pckg) {
            return getWrappedInformation(pckg, EMPTY);
        }

        public static PackageInfo getWrappedInformation(final String pckg, final String name) {
            if (Strings.isNullOrEmpty(pckg)) {
                throw new IllegalArgumentException("Package-structure cannot be nilled or empty.");
            }
            final PackageInfo info = new PackageInfo();
            info.setAccessed(System.currentTimeMillis());
            info.setStructure(pckg);
            info.setName(name);
            for (final ClassPath.ClassInfo clazz : getClasses(pckg)) {
                final ClassInfo classInfo = ByClass.getWrappedInformation(getClassByInfo(clazz));
                if (classInfo != null) {
                    info.addClass(classInfo);
                }
            }
            return info;
        }

        public static Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> getAnnotations(
                final String pckg) {
            if (Strings.isNullOrEmpty(pckg)) {
                return Collections.emptyMap();
            }
            return detectClasses(getClasses(pckg));
        }

        private static Set<ClassPath.ClassInfo> getClasses(final String pckg) {
            final ClassPath classPath;
            try {
                classPath = ClassPath.from(JerseyResourceUtils.class.getClassLoader());
                return classPath.getTopLevelClassesRecursive(pckg);
            } catch (final IOException e) {
                LOG.severe(
                        String.format(
                                "Unable to find classes or similar in the provided package-structure {%s}. Halting.",
                                pckg
                        )
                );
            }
            return Collections.emptySet();
        }

        private static Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> detectClasses(
                final Set<ClassPath.ClassInfo> classes
        ) {
            if (classes != null && !classes.isEmpty()) {
                final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> detectedClasses = new TreeMap<>();
                for (final ClassPath.ClassInfo i : classes) {
                    if (i != null && i.getPackageName() != null) {
                        detectedClasses.putAll(ByClass.getAnnotations(getClassByInfo(i)));
                    }
                }
                return detectedClasses;
            }
            return Collections.emptyMap();
        }

        private static Class getClassByInfo(final ClassPath.ClassInfo classInfo) {
            if (classInfo == null) {
                return null;
            }
            try {
                return Class.forName(classInfo.getName());
            } catch (final ClassNotFoundException e) {
                LOG.severe(String.format(
                        "Unable to find class {%s} even though it was supposed to be there. Not defined in classpath?",
                        classInfo.getName()));
            }
            return null;
        }

    }

    public static class ByClass {

        /**
         * [Class, [Method, [AnnotationType, AnnotationValue]]]
         *
         * @param jerseyClass _
         * @return _
         */
        public static Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> getAnnotations(final Class jerseyClass) {
            if (jerseyClass != null) {
                final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> detectedAnnotations = new TreeMap<>();
                if (jerseyClass.getMethods() != null && jerseyClass.getMethods().length > 0) {
                    final Map<String, Map<String, Map<String, Map<String, String>>>> detectedMethods = new LinkedHashMap<>();
                    for (Method method : jerseyClass.getMethods()) {
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
                                mannot.put(AnnotationLocation.PRESEDENCE.getId(), detectedMethodAnnotations);
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
                    if (jerseyClass.getAnnotations() != null && jerseyClass.getAnnotations().length > 0) {
                        final Map<String, Map<String, Map<String, String>>> assembledClazzMethodAnnotations = new HashMap<>();
                        final Map<String, String> clazzMethodAnnotations = new HashMap<>();
                        for (final Annotation a : jerseyClass.getAnnotations()) {
                            if (a != null) {
                                clazzMethodAnnotations.putAll(
                                        JerseyAnnotationAssembler.getJerseyAnnotationInformation(a)
                                );
                            }
                        }
                        if (!clazzMethodAnnotations.isEmpty()) {
                            final Map<String, Map<String, String>> m = new HashMap<>();
                            m.put(AnnotationLocation.PRESEDENCE.getId(), clazzMethodAnnotations);
                            assembledClazzMethodAnnotations.put(getClassIdentifier(jerseyClass), m);
                            detectedMethods.put(CLASS_REF_ID, assembledClazzMethodAnnotations);
                        }
                    }
                    if (!detectedMethods.isEmpty()) {
                        detectedAnnotations.put(jerseyClass.getSimpleName(), detectedMethods);
                    }
                }
                return detectedAnnotations;
            }
            LOG.warning("Provided class was null.");
            return Collections.emptyMap();
        }

        /**
         * Same as getAnnotations, however this thing wraps the maps into an object.
         *
         * @param jerseyClass class containing some jersey-annotation - alà Controllers
         * @return wrapped classinfo object
         */
        public static ClassInfo getWrappedInformation(final Class jerseyClass) {
            if (null == jerseyClass) {
                throw new IllegalArgumentException("Class cannot be null.");
            }
            final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> annotations = getAnnotations(jerseyClass);
            if (annotations == null || annotations.isEmpty()) {
                return null;
            }
            if (!annotations.containsKey(jerseyClass.getSimpleName())) {
                return null;
            }
            final Map<String, Map<String, Map<String, Map<String, String>>>> myClass = annotations.get(jerseyClass.getSimpleName());
            final ClassInfo classInfo = new ClassInfo();
            classInfo.setName(jerseyClass.getSimpleName());
            classInfo.setAccessed(System.currentTimeMillis());
            classInfo.setPackageStructure(jerseyClass.getName());
            classInfo.setAnnotations(
                    getAnnotationsById(
                            myClass.get(CLASS_REF_ID),
                            getClassIdentifier(jerseyClass),
                            AnnotationLocation.PRESEDENCE
                    ));
            classInfo.setMethods(getClassMethods(myClass));
            return classInfo;
        }

        private static Set<MethodInfo> getClassMethods(final Map<String, Map<String, Map<String, Map<String, String>>>> annotations) {
            if (annotations == null || annotations.isEmpty()) {
                return Collections.emptySet();
            }
            final Set<MethodInfo> methods = new HashSet<>();
            for (final Map.Entry<String, Map<String, Map<String, Map<String, String>>>> a : annotations.entrySet()) {
                if (a.getKey().equals(CLASS_REF_ID)) {
                    break;
                }
                final MethodInfo method = new MethodInfo();
                method.setName(a.getKey());
                method.setAccessed(System.currentTimeMillis());
                for (AnnotationLocation loc : AnnotationLocation.values()) {
                    switch (loc) {
                        case PARAMETER:
                            method.addParameters(getAnnotationsById(a.getValue(), a.getKey(), loc));
                            break;
                        case PRESEDENCE:
                            method.addAnnotations(getAnnotationsById(a.getValue(), a.getKey(), loc));
                            break;
                    }
                }
                methods.add(method);
            }
            return methods;
        }

        private static Set<AnnotationSpec> getAnnotationsById(
                final Map<String, Map<String, Map<String, String>>> annotations, final String id, final AnnotationLocation location) {
            if (
                    annotations == null ||
                            annotations.isEmpty() ||
                            !annotations.containsKey(id) ||
                            !annotations.get(id).containsKey(location.getId())
                    ) {
                return Collections.emptySet();
            }
            final Set<AnnotationSpec> specs = new HashSet<>();
            for (final Map.Entry<String, String> annotation : annotations.get(id).get(location.getId()).entrySet()) {
                final AnnotationSpec s = new AnnotationSpec();
                s.setAccessed(System.currentTimeMillis());
                s.setName(getAnnotationName(annotation.getKey()));
                Parameter param = new Parameter();
                param.setKey(annotation.getKey());
                param.setValue(ValueUtils.disassembleValue(annotation.getValue()));
                s.addParameter(param);
                specs.add(s);
            }
            return specs;
        }

        private static String getClassIdentifier(final Class clazz) {
            String id = String.format("%s%s%s", CLASS_DD, SEPARATOR, CLASS_IDENTIFIER);
            if (clazz != null && !Strings.isNullOrEmpty(clazz.getSimpleName())) {
                id = String.format("%s%s%s", CLASS_DD, SEPARATOR, clazz.getSimpleName());
            }
            return id;
        }

        private static String getAnnotationName(final String name) {
            if (Strings.isNullOrEmpty(name)) {
                return null;
            }
            if (name.startsWith("@")) {
                return name;
            }
            return String.format("@%s", name);
        }

    }

    public enum AnnotationLocation {
        PRESEDENCE("prec"), PARAMETER("parameter");

        private String id;

        private AnnotationLocation(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

}
