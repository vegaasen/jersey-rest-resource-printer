package com.vegaasen.http.rest.jersey.pointless;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class Storage {

    private static Map<String, String> coolStuff = new HashMap<>();

    public static Map<String, String> getCoolStuff() {
        return coolStuff;
    }

    public static boolean isNullOrEmpty() {
        return coolStuff == null || coolStuff.isEmpty();
    }

    public static boolean isEmpty() {
        return coolStuff.isEmpty();
    }

    public static void purge() {
        coolStuff.clear();
    }

    public static void reinitialize() {
        purge();
        coolStuff = null;
        coolStuff = new HashMap<>();
    }

    public static void addStuff(String key, String value) {
        coolStuff.put(key, value);
    }

    public static void addLotsOfStuff(Map<String, String> stuff) {
        coolStuff.putAll(stuff);
    }

    public static boolean modifyStuff(String key, String value) {
        if (!isEmpty() && coolStuff.containsKey(key)) {
            addStuff(key, value);
            return true;
        }
        return false;
    }

}
