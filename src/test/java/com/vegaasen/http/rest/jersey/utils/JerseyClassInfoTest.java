package com.vegaasen.http.rest.jersey.utils;

import com.vegaasen.http.rest.jersey.abs.AbstractJerseyResourceTest;
import com.vegaasen.http.rest.jersey.controller.abs.AbstractController;
import com.vegaasen.http.rest.jersey.controller.simple.IHaveAllVerbs;
import com.vegaasen.http.rest.jersey.controller.simple.VerbsWithValuableAnnotations;
import com.vegaasen.http.rest.jersey.model.ClassInfo;
import com.vegaasen.http.rest.jersey.model.clazz.MethodInfo;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class JerseyClassInfoTest extends AbstractJerseyResourceTest {

    @Test
    public void shouldGetClassInfoForClass() {
        final ClassInfo classInfo = JerseyResourceUtils.ByClass.getWrappedInformation(IHaveAllVerbs.class);
        assertNotNull(classInfo);
        assertNotNull(classInfo.getName());
        assertFalse(classInfo.getName().isEmpty());
        assertNotNull(classInfo.getAnnotations());
        assertFalse(classInfo.getAnnotations().isEmpty());
        assertNotNull(classInfo.getMethods());
        assertFalse(classInfo.getMethods().isEmpty());
        for (MethodInfo method : classInfo.getMethods()) {
            assertNotNull(method);
            assertNotNull(method.getName());
            assertFalse(method.getName().isEmpty());
            assertNotNull(method.getAnnotations());
        }
        assertEquals(1, classInfo.getAnnotations().size());
    }

    @Test
    public void shouldNotFindAnythingOnClass() {
        final ClassInfo classInfo = JerseyResourceUtils.ByClass.getWrappedInformation(AbstractController.class);
        assertNull(classInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnNullOnNilledValue() {
        JerseyResourceUtils.ByClass.getWrappedInformation(null);
    }

    @Test
    public void shouldReturnControllerWithMethodsAndParameters() {
        final ClassInfo classInfo = JerseyResourceUtils.ByClass.getWrappedInformation(VerbsWithValuableAnnotations.class);
        assertNotNull(classInfo);
        assertNotNull(classInfo.getName());
        assertFalse(classInfo.getName().isEmpty());
        assertFalse(classInfo.getMethods().isEmpty());
        assertEquals(VerbsWithValuableAnnotations.class.getSimpleName(), classInfo.getName());
        assertEquals(VerbsWithValuableAnnotations.class.getName(), classInfo.getPackageStructure());
        Method[] methods = VerbsWithValuableAnnotations.class.getDeclaredMethods();
        assertNotNull(methods);
        assertEquals(methods.length, classInfo.getMethods().size());
        for (final MethodInfo methodInfo : classInfo.getMethods()) {
            for (Method m : methods) {
                assertNotNull(m);
                if (m.getName().equals(methodInfo.getName())) {
                    assertTrue(true);
                }
            }
        }
    }

}
