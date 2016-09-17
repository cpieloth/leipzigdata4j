package de.leipzigdata.address.entity;

/**
 * A builder/factory for an EntityFactory.
 * A library user can set a custom implementation of EntityFactory.
 *
 * @author cpieloth
 */
public final class EntityFactoryBuilder {

    private static EntityFactory instance = new DefaultEntityFactory();

    /**
     * Get an instance of a EntityFactory implementation.
     *
     * @return instance of a EntityFactory
     */
    public static EntityFactory newEntityFactory() {
        return instance;
    }

    /**
     * Set a custom implementation of EntityFactory.
     *
     * @param ef Custom implementation of EntityFactory
     */
    public static void setEntityFactory(EntityFactory ef) {
        instance = ef;
    }
}
