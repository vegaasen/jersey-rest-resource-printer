package com.vegaasen.http.rest.jersey.utils;

import java.lang.reflect.Method;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static int numOfMethodsInThisClass(final Class me) {
        int num = 0;
        if (me != null) {
            Method[] methods = me.getDeclaredMethods();
            if (methods != null) {
                num = methods.length;
            }
        }
        return num;
    }

}
