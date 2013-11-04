package com.vegaasen.http.rest.jersey.utils;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class JerseyAnnotationAssemblerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldNotReturnAnyMatches() throws NoSuchMethodException {
        Annotation[] annotations = this.getClass().getMethod("shouldNotReturnAnyMatches").getAnnotations();
        assertNotNull(annotations);
        for(Annotation annotation : annotations) {
            Map<String, String> ss = JerseyAnnotationAssembler.getJerseyAnnotationInformation(annotation);
            assertNotNull(ss);
            assertTrue(ss.isEmpty());
        }
    }

}
