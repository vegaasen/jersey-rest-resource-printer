package com.vegaasen.http.rest.jersey.utils;

import com.google.common.reflect.ClassPath;
import com.vegaasen.http.rest.jersey.abs.AbstractJerseyResourceTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JerseyResourceUtilsByPackageTest extends AbstractJerseyResourceTest {

    private static final String DEFAULT_JERSEY_CNTRLLR_PACKAGE = "com.vegaasen.http.rest.jersey.controller";

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void findResultsByPackage() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage(DEFAULT_JERSEY_CNTRLLR_PACKAGE);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.size() > 1);
        assertEquals(numOfClassesInPackage(DEFAULT_JERSEY_CNTRLLR_PACKAGE), result.size());
    }

    @Test
    public void shouldNotFindAnyPackageWithinPackageScope() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage("god.knows.where");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldFindNoControllersAsClassContainsNoAnnotation() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage(DEFAULT_JERSEY_CNTRLLR_PACKAGE + ".abs");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotTriggerOnNilledObject() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotTriggerOnEmptyObject() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage("");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldFindSameAmountAsContollerView() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage("com.vegaasen");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resultByDefinedClasspath = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage(DEFAULT_JERSEY_CNTRLLR_PACKAGE);
        assertNotNull(resultByDefinedClasspath);
        assertFalse(resultByDefinedClasspath.isEmpty());
        assertTrue(resultByDefinedClasspath.size() > 1);
        assertEquals(resultByDefinedClasspath.size(), result.size());
    }

    @Test
    public void shouldContainIHaveAllVerbs() {
        final Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByPackage.getAnnotationsFromBasePackage(DEFAULT_JERSEY_CNTRLLR_PACKAGE);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.size() > 1);
        Map<String, Map<String, Map<String, Map<String, String>>>> method = result.get("IHaveAllVerbs");
        assertNotNull(method);
        assertTrue(method.containsKey("delete"));
        assertTrue(method.containsKey("post"));
        assertTrue(method.containsKey("self"));
        assertNotNull(method.get("head").get("head").get("method"));
        Map<String, String> methodForHead = method.get("head").get("head").get("method");
        assertNotNull(methodForHead);
        assertFalse(methodForHead.isEmpty());
        assertTrue(methodForHead.containsKey("HEAD"));
        assertTrue(methodForHead.containsValue("true"));
        assertEquals(1, methodForHead.size());
    }

    private int numOfClassesInPackage(final String pcgk) {
        assertNotNull(pcgk);
        assertFalse(pcgk.isEmpty());
        final ClassPath classPath;
        try {
            classPath = ClassPath.from(JerseyResourceUtilsByPackageTest.class.getClassLoader());
            assertNotNull(classPath);
            Set<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive(pcgk);
            assertNotNull(classes);
            assertFalse(classes.isEmpty());
            assertTrue(classes.size() > 1);
            final int REM_ABS_IN_CLASSPATH = 1; // remove the abstract-controller as its not a valid controller (no annotation of any kind..)
            return classes.size() - REM_ABS_IN_CLASSPATH;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
