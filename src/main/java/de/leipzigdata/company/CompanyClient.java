package de.leipzigdata.company;

import de.leipzigdata.LeipzigDataHelper;
import de.leipzigdata.company.entity.Company;
import de.leipzigdata.company.entity.EntityFactory;
import de.leipzigdata.company.entity.EntityFactoryBuilder;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * A client to access data from ld:Unternehmen.
 *
 * @author cpieloth
 */
public class CompanyClient {
    private static final Logger log = LoggerFactory.getLogger(CompanyClient.class);

    public CompanyClient() {
    }

    /**
     * Get all companies.
     *
     * @return List of companies or null.
     */
    public List<Company> getCompanies() {
        final String file_name = "getCompanies.sparql";

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
        List<Company> companies = new LinkedList<>();

        results.forEachRemaining(qs -> {
            String uri = qs.get("unternehmen").asResource().getURI();
            Model model = ModelFactory.createDefaultModel();
            model.read(uri);
            Company company = deserialize(model.getResource(uri), ef);
            if(company != null) {
                companies.add(company);
            }
        });

        return companies;
    }

    /**
     * Get a company.
     *
     * @param uri URI of the company.
     * @return Filled company instance or null.
     */
    public Company getCompany(URI uri) {
        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        Model model = ModelFactory.createDefaultModel();
        model.read(uri.toString());
        Resource resource = model.getResource(uri.toString());
        return deserialize(resource, ef);
    }

    private static Company deserialize(Resource resource, EntityFactory ef) {
        Company company = ef.newCompany();

        try {
            company.setUri(new URI(resource.getURI()));
        } catch (URISyntaxException e) {
            log.error("Could not parse URI!", e);
            return null;
        }

        StmtIterator stmtIterator = resource.listProperties();
        while(stmtIterator.hasNext()) {
            Statement stmt = stmtIterator.next();
            RDFNode node = stmt.getObject();
            Property predicate = stmt.getPredicate();
            if ("http://www.w3.org/2000/01/rdf-schema#label".equals(predicate.getURI())) {
                company.setName(node.asLiteral().getString());
                continue;
            }
            if ("http://xmlns.com/foaf/0.1/homepage".equals(predicate.getURI())) {
                try {
                    company.setHomepage(new URL(node.asResource().getURI()));
                } catch (MalformedURLException e) {
                    log.error("Could not create URL for homepage!", e);
                }
                continue;
            }
            if ("http://leipzig-data.de/Data/Model/hasAddress".equals(predicate.getURI())) {
                final String addressUri = node.asResource().getURI();
                final String addressPart = addressUri.substring(addressUri.lastIndexOf("/") + 1);
                final String addressParts[] = addressPart.split("\\.");
                if (addressParts.length == 4) {
                    company.setPostcode(addressParts[0]);
                    company.setCity(addressParts[1]);
                    company.setStreet(addressParts[2]);
                    company.setStreet(addressParts[3]);
                }
                else {
                    log.error("Could not parse address: {}, {}, {}", addressUri, addressPart, addressParts.length);
                }
            }
        }

        return company;
    }
}
