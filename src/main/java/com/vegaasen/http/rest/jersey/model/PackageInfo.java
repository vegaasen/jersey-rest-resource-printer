package com.vegaasen.http.rest.jersey.model;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class PackageInfo extends NamedElement {

    private String structure;
    private Set<ClassInfo> classes = new HashSet<>();

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public Set<ClassInfo> getClasses() {
        return classes;
    }

    public void addClass(ClassInfo classInfo) {
        this.classes.add(classInfo);
    }

    public void setClasses(Set<ClassInfo> classes) {
        this.classes = classes;
    }
}
