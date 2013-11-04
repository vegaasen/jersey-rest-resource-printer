package com.vegaasen.http.rest.jersey.model.clazz;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class Method extends NamedElement {

    public Set<Annotation> annotations;
    public List<Parameter> parameters;

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
