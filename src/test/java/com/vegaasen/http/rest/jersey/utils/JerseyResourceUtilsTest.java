package com.vegaasen.http.rest.jersey.utils;

import com.vegaasen.http.rest.jersey.abs.AbstractJerseyResourceTest;
import com.vegaasen.http.rest.jersey.controller.basic.VeryBasicController;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JerseyResourceUtilsTest extends AbstractJerseyResourceTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldReturnEmptyOnNull() {
        Map<String, Map<String, Map<String, String>>> result = JerseyResourceUtils.ByClass.getAnnotationsForClass(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotFindAnyRelevantAnnotations() {
        Map<String, Map<String, Map<String, String>>> resources = JerseyResourceUtils.ByClass.getAnnotationsForClass(this.getClass());
        assertNotNull(resources);
        assertTrue(resources.isEmpty());
    }

    @Test
    public void shouldFindTheSimpleController() {
        Map<String, Map<String, Map<String, String>>> resources = JerseyResourceUtils.ByClass.getAnnotationsForClass(VeryBasicController.class);
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        assertEquals(1, resources.size());
    }

    @Test
    public void shouldFindSimpleControllerAndSomeAnnotations() {
        Map<String, Map<String, Map<String, String>>> resources = JerseyResourceUtils.ByClass.getAnnotationsForClass(VeryBasicController.class);
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        assertEquals(1, resources.size());
        //todo: iterate through..
    }

}
