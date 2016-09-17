package de.leipzigdata.address.entity;

/**
 * An entity factory for address.
 * Creation of a concrete entity types, e.g. for JPA implementation.
 *
 * @author cpieloth
 */
public interface EntityFactory {

    /**
     * Get a new instance of an address implementation.
     *
     * @return instance of an address
     */
    Address newAddress();
}
