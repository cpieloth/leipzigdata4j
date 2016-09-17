package de.leipzigdata.entity;

import java.net.URI;

/**
 * TODO doc
 *
 * @author cpieloth
 */
public interface Entity {
    URI getUri();
    void setUri(URI uri);

    String getLabel();
    void setLabel(String label);
}
