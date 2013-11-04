package com.vegaasen.http.rest.jersey.model;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;
import com.vegaasen.http.rest.jersey.model.clazz.AnnotationSpec;
import com.vegaasen.http.rest.jersey.model.clazz.MethodInfo;

import java.util.Set;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class ClassInfo extends NamedElement {

    private String packageStructure;
    private Set<AnnotationSpec> annotations;
    private Set<MethodInfo> methods;

    public String getPackageStructure() {
        return packageStructure;
    }

    public void setPackageStructure(String packageStructure) {
        this.packageStructure = packageStructure;
    }

    public Set<AnnotationSpec> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<AnnotationSpec> annotations) {
        this.annotations = annotations;
    }

    public Set<MethodInfo> getMethods() {
        return methods;
    }

    public void setMethods(Set<MethodInfo> methods) {
        this.methods = methods;
    }
}
