package de.leipzigdata.company;

import de.leipzigdata.LeipzigDataHelper;
import de.leipzigdata.company.entity.Company;
import de.leipzigdata.company.entity.EntityFactory;
import de.leipzigdata.company.entity.EntityFactoryBuilder;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
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
            Company company = deserialize(qs, ef);
            if(company != null) {
                companies.add(company);
            }
        });

        return companies;
    }

    private static Company deserialize(QuerySolution qs, EntityFactory ef) {
        Company company = ef.newCompany();
        String varName;

        varName = "unternehmen";
        if (qs.contains(varName)) {
            try {
                company.setUri(new URI(qs.get(varName).asResource().getURI()));
            } catch (URISyntaxException e) {
                log.error("Could not parse URI!", e);
                return null;
            }
        }
        else {
            log.error("Missing variable: " + varName);
            return null;
        }

        varName = "name";
        if (qs.contains(varName)) {
            company.setName(qs.get(varName).asLiteral().getLexicalForm());
        }
        else {
            log.error("Missing variable: " + varName);
            return null;
        }

        varName = "address";
        if (qs.contains(varName)) {
//            company.setName(qs.get(varName).asLiteral().getLexicalForm());
            Resource resource = qs.get(varName).asResource();
            log.debug("{}", resource);
        }
        else {
            log.debug("Missing variable: " + varName);
        }

        varName = "homepage";
        if (qs.contains(varName)) {
            try {
                company.setHomepage(new URL(qs.get(varName).asResource().getURI()));
            } catch (MalformedURLException e) {
                log.error("Could not parse homepage!", e);
            }
        }
        else {
            log.debug("Missing variable: " + varName);
        }

        return company;
    }

    public static void main(String[] args) {
        CompanyClient client = new CompanyClient();
        List<Company> companies = client.getCompanies();

        companies.forEach(System.out::println);
        System.out.println("Company count: " + companies.size());
    }
}
