package com.vegaasen.http.rest.jersey.model.clazz;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class AnnotationSpec extends NamedElement {

    private List<Parameter> parameters = new ArrayList<>();

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void addParameter(final Parameter parameter) {
        parameters.add(parameter);
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
