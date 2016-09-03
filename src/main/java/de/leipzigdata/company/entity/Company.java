package de.leipzigdata.company.entity;

import java.net.URI;
import java.net.URL;

/**
 * A company of the company.
 *
 * @author cpieloth
 */
public interface Company {
    URI getUri();
    void setUri(URI uri);

    String getName();
    void setName(String name);

    String getStreet();
    void setStreet(String street);

    int getHousenumber();
    void setHousenumber(int housenumber);

    String getPostcode();
    void setPostcode(String postcode);

    String getCity();
    void setCity(String city);

    URL getHomepage();
    void setHomepage(URL homepage);
}
