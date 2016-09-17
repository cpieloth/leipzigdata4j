package de.leipzigdata.address.entity;

/**
 * Default implementation of a EntityFactory.
 *
 * @author cpieloth
 */
public class DefaultEntityFactory implements EntityFactory {
    @Override
    public DefaultAddress newAddress() {
        return new DefaultAddress();
    }
}
