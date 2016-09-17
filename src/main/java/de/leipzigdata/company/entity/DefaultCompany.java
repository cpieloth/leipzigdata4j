package de.leipzigdata.company.entity;

import de.leipzigdata.address.entity.Address;
import de.leipzigdata.entity.DefaultEntity;

import java.net.URI;
import java.net.URL;

/**
 * Default implementation of a Company.
 *
 * @author cpieloth
 */
public class DefaultCompany extends DefaultEntity implements Company {
    private Address address;
    private URL homepage;

    public DefaultCompany() {
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
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
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("[");
        sb.append("uri=").append( uri.toString());
        sb.append(", label=").append(label);
        sb.append(", address=").append(address);
        sb.append("]");
        return sb.toString();
    }
}
