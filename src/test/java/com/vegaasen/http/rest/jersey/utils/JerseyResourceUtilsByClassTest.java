package com.vegaasen.http.rest.jersey.utils;

import com.vegaasen.http.rest.jersey.abs.AbstractJerseyResourceTest;
import com.vegaasen.http.rest.jersey.common.Types;
import com.vegaasen.http.rest.jersey.controller.basic.ControllerWithoutMethods;
import com.vegaasen.http.rest.jersey.controller.basic.VeryBasicController;
import com.vegaasen.http.rest.jersey.controller.simple.IHaveAllVerbs;
import com.vegaasen.http.rest.jersey.controller.simple.VerbsWithValuableAnnotations;
import com.vegaasen.http.rest.jersey.pointless.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class JerseyResourceUtilsByClassTest extends AbstractJerseyResourceTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldReturnEmptyOnNull() {
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> result = JerseyResourceUtils.ByClass.getAnnotations(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotFindAnyRelevantAnnotations() {
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resources = JerseyResourceUtils.ByClass.getAnnotations(this.getClass());
        assertNotNull(resources);
        assertTrue(resources.isEmpty());
    }

    @Test
    public void shouldFindTheSimpleController() {
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resources = JerseyResourceUtils.ByClass.getAnnotations(VeryBasicController.class);
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        assertEquals(1, resources.size());
    }

    @Test
    public void shouldFindSimpleControllerAndSomeAnnotations() {
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resources = JerseyResourceUtils.ByClass.getAnnotations(VeryBasicController.class);
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        assertEquals(1, resources.size());
        //todo: iterate through..
    }

    @Test
    public void shouldFindOnlyClassLevelAnnotation() {
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resources = JerseyResourceUtils.ByClass.getAnnotations(ControllerWithoutMethods.class);
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        for (Map.Entry<String, Map<String, Map<String, Map<String, String>>>> method : resources.get("ControllerWithoutMethods").entrySet()) {
            assertNotNull(method);
            assertNotNull(method.getKey());
            assertNotNull(method.getValue());
            assertFalse(method.getValue().isEmpty());
        }
    }

    @Test
    public void shouldIterateOverClassWithAllVerbs() {
        final Class testClass = IHaveAllVerbs.class;
        testVerbedClass(testClass);
    }

    @Test
    public void shouldIterateOverClassWithValuableAnnotations() {
        final Class testClass = VerbsWithValuableAnnotations.class;
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resources = testVerbedClass(testClass);
        assertNotNull(resources);
        Map<String, Map<String, Map<String, Map<String, String>>>> methods = resources.get(testClass.getSimpleName());
        String methodToTest = "calculateDaysToMillis";
        for (Map.Entry<String, Map<String, Map<String, Map<String, String>>>> method : methods.entrySet()) {
            assertNotNull(method);
            assertNotNull(method.getKey());
            if (method.getKey().equals(methodToTest)) {
                assertNotNull(method.getValue());
                Map<String, String> methodAnnotations = method.getValue().get(methodToTest).get(JerseyResourceUtils.AnnotationLocation.PRESEDENCE.getId());
                assertTrue(methodAnnotations.containsKey("GET"));
                assertEquals("true", methodAnnotations.get("GET"));
                assertTrue(methodAnnotations.containsKey(Types.CONSUMES));
                assertEquals("{*/*}", methodAnnotations.get(Types.CONSUMES));
                assertTrue(methodAnnotations.containsKey(Types.PATH));
                assertEquals("{days/{days}}", methodAnnotations.get(Types.PATH));
            }
        }
    }

    protected Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> testVerbedClass(final Class clazz) {
        Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>> resources = JerseyResourceUtils.ByClass.getAnnotations(clazz);
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        assertEquals(1, resources.size());
        int numOfMethods = TestUtils.numOfMethodsInThisClass(clazz);
        assertNotNull(numOfMethods);
        assertTrue(numOfMethods > 0);
        assertFalse(resources.containsKey(clazz.getName()));
        assertTrue(resources.containsKey(clazz.getSimpleName()));
        Map<String, Map<String, Map<String, Map<String, String>>>> methods = resources.get(clazz.getSimpleName());
        assertNotNull(methods);
        assertFalse(methods.isEmpty());
        assertTrue(String.format("Size didnt match. Expected: {%s} (or more..). Found: {%s}", numOfMethods, methods.size()),
                methods.size() >= numOfMethods);
        return resources;
    }

}
