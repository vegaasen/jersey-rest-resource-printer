package com.vegaasen.http.rest.jersey.model;

import com.vegaasen.http.rest.jersey.model.abs.NamedElement;

import java.util.Set;

/**
 * @author <a href="vegaasen@gmail.com">vegardaasen</a>
 */
public final class Package extends NamedElement {

    private String structure;
    private Set<ClassInfo> classes;

}
