package com.vegaasen.http.rest.jersey.model.clazz;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

import java.util.List;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class Parameter extends NamedElement {

    private String key;
    private List<String> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
