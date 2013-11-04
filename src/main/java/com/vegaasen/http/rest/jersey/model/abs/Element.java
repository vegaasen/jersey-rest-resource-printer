package com.vegaasen.http.rest.jersey.model.abs;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public abstract class Element {

    public long accessed;

    public long getAccessed() {
        return accessed;
    }

    public void setAccessed(long accessed) {
        this.accessed = accessed;
    }
}
