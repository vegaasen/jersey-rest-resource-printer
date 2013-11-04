package com.vegaasen.http.rest.jersey.model;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;
import com.vegaasen.http.rest.jersey.model.clazz.Annotation;
import com.vegaasen.http.rest.jersey.model.clazz.Method;

import java.util.Set;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class ClassInfo extends NamedElement {

    private String path;
    private Set<Annotation> annotations;
    private Set<Method> methods;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public void setMethods(Set<Method> methods) {
        this.methods = methods;
    }
}
