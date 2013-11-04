package com.vegaasen.http.rest.jersey.utils;

import com.google.common.base.Strings;
import com.vegaasen.http.rest.jersey.common.Types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
final class ValueUtils {

    public static final String CRLY_LEFT = "}", CRLY_RIGHT = "{", SEPARATOR = ",";

    private ValueUtils() {
    }

    public static String assembleValue(final String... values) {
        if (values == null || values.length == 0 || Strings.isNullOrEmpty(values[0])) {
            return Types.EMPTY;
        }
        StringBuilder bldr = new StringBuilder();
        bldr.append(CRLY_RIGHT);
        int pos = 0;
        for (String v : values) {
            bldr.append(v);
            pos++;
            if (pos != values.length) {
                bldr.append(SEPARATOR);
            }
        }
        bldr.append(CRLY_LEFT);
        return bldr.toString();
    }

    public static List<String> disassembleValue(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return Collections.emptyList();
        }
        if (value.startsWith(CRLY_RIGHT)) {
            value = value.substring(1, value.length());
        }
        if (value.endsWith(CRLY_LEFT)) {
            value = value.substring(0, value.length() - 1);
        }
        return Arrays.asList(value.split(SEPARATOR));
    }

}
