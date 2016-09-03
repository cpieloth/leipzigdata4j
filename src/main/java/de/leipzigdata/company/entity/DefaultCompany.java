package de.leipzigdata.company.entity;

import java.net.URI;
import java.net.URL;

/**
 * Default implementation of a Company.
 *
 * @author cpieloth
 */
public class DefaultCompany implements Company {
    private URI uri;
    private String name;
    private String street;
    private int housenumber;
    private String postcode;
    private String city;
    private URL homepage;

    public DefaultCompany() {
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public int getHousenumber() {
        return housenumber;
    }

    @Override
    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    @Override
    public String getPostcode() {
        return postcode;
    }

    @Override
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public URL getHomepage() {
        return homepage;
    }

    @Override
    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "" + this.getClass().getSimpleName() + "[uri=" + uri.toString() + ", name=" + name + ", ...]";
    }
}
