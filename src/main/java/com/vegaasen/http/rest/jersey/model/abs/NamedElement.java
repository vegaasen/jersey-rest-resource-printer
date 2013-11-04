package com.vegaasen.http.rest.jersey.model.abs;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public abstract class NamedElement extends Element {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
