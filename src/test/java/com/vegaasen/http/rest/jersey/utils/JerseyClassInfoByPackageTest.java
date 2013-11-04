package com.vegaasen.http.rest.jersey.utils;

import com.vegaasen.http.rest.jersey.abs.AbstractJerseyResourceTest;
import com.vegaasen.http.rest.jersey.controller.abs.AbstractController;
import com.vegaasen.http.rest.jersey.model.ClassInfo;
import com.vegaasen.http.rest.jersey.model.PackageInfo;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class JerseyClassInfoByPackageTest extends AbstractJerseyResourceTest {

    private static final String DEFAULT_JERSEY_CNTRLLR_PACKAGE = "com.vegaasen.http.rest.jersey.controller";

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotFindThingsByNilledValue() {
        JerseyResourceUtils.ByPackage.getWrappedInformation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotFindThingsByEmptyValue() {
        JerseyResourceUtils.ByPackage.getWrappedInformation("");
    }

    @Test
    public void shouldNotFindAnythingByWrongPackage() {
        final PackageInfo info = JerseyResourceUtils.ByPackage.getWrappedInformation("com.i.dont.exist");
        assertNotNull(info);
        assertNotNull(info.getClasses());
        assertTrue(info.getClasses().isEmpty());
    }

    @Test
    public void shouldFindMultipleClassInfoByPackage() {
        final PackageInfo info = JerseyResourceUtils.ByPackage.getWrappedInformation(DEFAULT_JERSEY_CNTRLLR_PACKAGE);
        assertNotNull(info);
        assertNotNull(info.getName());
        assertNotNull(info.getClasses());
        assertFalse(info.getClasses().isEmpty());
        assertTrue(info.getClasses().size() > 2);
        boolean contained = false;
        for (ClassInfo classInfo : info.getClasses()) {
            if (classInfo.getName().equals(AbstractController.class.getSimpleName())) {
                contained = true;
            }
        }
        assertFalse(contained);
    }

    @Test
    public void shouldFindNoControllers() {
        final PackageInfo info = JerseyResourceUtils.ByPackage.getWrappedInformation("com.vegaasen.http.rest.jersey.controller.abs");
        assertNotNull(info);
        assertNotNull(info.getClasses());
        assertTrue(info.getClasses().isEmpty());
    }

    @Test
    public void shouldFindTwoControllers() {
        final PackageInfo info = JerseyResourceUtils.ByPackage.getWrappedInformation("com.vegaasen.http.rest.jersey.controller.simple");
        assertNotNull(info);
        assertNotNull(info.getClasses());
        assertFalse(info.getClasses().isEmpty());
        assertEquals(2, info.getClasses().size());
    }

}
