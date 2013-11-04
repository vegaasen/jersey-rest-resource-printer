package com.vegaasen.http.rest.jersey.model.clazz;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class MethodInfo extends NamedElement {

    private Set<AnnotationSpec> annotations = new HashSet<>();
    private Set<AnnotationSpec> parameters = new HashSet<>();

    public Set<AnnotationSpec> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<AnnotationSpec> annotations) {
        this.annotations = annotations;
    }

    public void addAnnotations(Set<AnnotationSpec> annotations) {
        this.annotations.addAll(annotations);
    }

    public void addParameters(Set<AnnotationSpec> parameters) {
        this.parameters.addAll(parameters);
    }

    public Set<AnnotationSpec> getParameters() {
        return parameters;
    }

    public void setParameters(Set<AnnotationSpec> parameters) {
        this.parameters = parameters;
    }
}
