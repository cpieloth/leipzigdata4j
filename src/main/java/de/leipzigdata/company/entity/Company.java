package de.leipzigdata.company.entity;

import de.leipzigdata.address.entity.Address;
import de.leipzigdata.entity.Entity;

import java.net.URI;
import java.net.URL;

/**
 * A company of the company.
 *
 * @author cpieloth
 */
public interface Company extends Entity {
    Address getAddress();
    void setAddress(Address address);

    URL getHomepage();
    void setHomepage(URL homepage);
}
