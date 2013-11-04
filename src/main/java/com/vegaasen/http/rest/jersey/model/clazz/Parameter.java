package com.vegaasen.http.rest.jersey.model.clazz;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class Parameter extends NamedElement {

    private String key;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
