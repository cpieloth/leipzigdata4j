package de.leipzigdata.entity;

import java.net.URI;

/**
 * Basic entity entity interface.
 *
 * @author cpieloth
 */
public interface Entity {
    URI getUri();
    void setUri(URI uri);

    String getLabel();
    void setLabel(String label);
}
