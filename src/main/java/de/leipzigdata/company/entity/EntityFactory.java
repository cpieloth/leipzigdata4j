package de.leipzigdata.company.entity;

/**
 * An entity factory for company.
 * Creation of a concrete entity types, e.g. for JPA implementation.
 *
 * @author cpieloth
 */
public interface EntityFactory {

    /**
     * Get a new instance of a company implementation.
     *
     * @return instance of a company
     */
    Company newCompany();
}
