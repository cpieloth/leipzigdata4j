package de.leipzigdata.address;

import de.leipzigdata.LeipzigDataHelper;
import de.leipzigdata.address.entity.Address;
import de.leipzigdata.address.entity.EntityFactory;
import de.leipzigdata.address.entity.EntityFactoryBuilder;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO doc
 *
 * @author cpieloth
 */
public class AddressClient {
    private static final Logger log = LoggerFactory.getLogger(AddressClient.class);

    public AddressClient() {
    }

    /**
     * Get all addresses.
     *
     * @return List of address or null.
     */
    public List<Address> getAddresses() {
        final String file_name = "getAddresses.rq";

        ResultSet results;
        try {
            Query query = QueryFactory.read(getClass().getResource(file_name).getFile());
            QueryEngineHTTP httpQuery = new QueryEngineHTTP(LeipzigDataHelper.SPARQL_ENDPOINT, query);
            results = httpQuery.execSelect();
        } catch (Exception e) {
            log.error("Error processing SPARQL: {}", file_name, e);
            return null;
        }

        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        List<Address> addresses = new LinkedList<>();

        results.forEachRemaining(qs -> {
            String uri = qs.getResource("resource").getURI();
            Model model = ModelFactory.createDefaultModel();
            model.read(uri);
            Address address = deserialize(model.getResource(uri), ef);
            if(address != null) {
                addresses.add(address);
            }
        });

        return addresses;
    }

    /**
     * Get an address.
     *
     * @param uri URI of the address.
     * @return Filled address instance or null.
     */
    public Address getAddress(URI uri) {
        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        Model model = ModelFactory.createDefaultModel();
        model.read(uri.toString());
        Resource resource = model.getResource(uri.toString());
        return deserialize(resource, ef);
    }

    private static Address deserialize(Resource resource, EntityFactory ef) {
        Address address = ef.newAddress();

        try {
            address.setUri(new URI(resource.getURI()));
        } catch (URISyntaxException e) {
            log.error("Could not parse URI!", e);
            return null;
        }
        if(resource.getURI().toLowerCase().contains("leipzig")) {
            address.setCity("Leipzig");
        } else {
            log.warn("Unknown city for resource: {}", resource.getURI());
        }

        StmtIterator stmtIterator = resource.listProperties();
        while(stmtIterator.hasNext()) {
            Statement stmt = stmtIterator.next();
            RDFNode node = stmt.getObject();
            Property predicate = stmt.getPredicate();
            final String uri = predicate.getURI();
            if ("http://www.w3.org/2000/01/rdf-schema#label".equals(uri)) {
                address.setLabel(node.asLiteral().getString());
                continue;
            }
            if ("http://leipzig-data.de/Data/Model/hasHouseNumber".equals(uri)) {
                address.setHousenumber(node.asLiteral().getLexicalForm());
                continue;
            }
            if ("http://leipzig-data.de/Data/Model/hasPostCode".equals(uri)) {
                address.setPostcode(node.asLiteral().getLexicalForm());
                continue;
            }
            if ("http://leipzig-data.de/Data/Model/inStreet".equals(uri)) {
                final String uriStreet = node.asResource().getURI();
                Model model = ModelFactory.createDefaultModel();
                model.read(uriStreet);
                Resource rStreet = model.getResource(uriStreet);
                Statement sStreet = rStreet.getProperty(model.createProperty("http://www.w3.org/2000/01/rdf-schema#",
                    "label"));
                address.setStreet(sStreet.getString());
                continue;
            }
        }
        return address;
    }
}
