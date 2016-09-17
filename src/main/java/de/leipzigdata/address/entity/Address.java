package de.leipzigdata.address.entity;

import de.leipzigdata.entity.Entity;

/**
 * A company of the company.
 *
 * @author cpieloth
 */
public interface Address extends Entity {
    String getStreet();
    void setStreet(String street);

    String getHousenumber();
    void setHousenumber(String housenumber);

    String getPostcode();
    void setPostcode(String postcode);

    String getCity();
    void setCity(String city);
}
