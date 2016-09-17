package de.leipzigdata.entity;

import java.net.URI;

/**
 * Default implementation of an Entity.
 *
 * @author cpieloth
 */
public abstract class DefaultEntity implements Entity {
    protected String label;
    protected URI uri;

    protected DefaultEntity() {
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
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }
}
