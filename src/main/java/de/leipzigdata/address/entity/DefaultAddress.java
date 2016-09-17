package de.leipzigdata.address.entity;

import de.leipzigdata.entity.DefaultEntity;

/**
 * Default implementation of an Address.
 *
 * @author cpieloth
 */
public class DefaultAddress extends DefaultEntity implements Address {
    private String street;
    private String housenumber;
    private String postcode;
    private String city;

    public DefaultAddress() {
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
    public String getHousenumber() {
        return housenumber;
    }

    @Override
    public void setHousenumber(String housenumber) {
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("[");
        sb.append("uri=").append(uri.toString());
        sb.append(", label=").append(label);
        sb.append(", street=").append(street);
        sb.append(", housenumber=").append(housenumber);
        sb.append(", postcode=").append(postcode);
        sb.append(", city=").append(city);
        sb.append("]");
        return sb.toString();
    }
}
