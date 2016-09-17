package examples;

import de.leipzigdata.company.CompanyClient;
import de.leipzigdata.company.entity.Company;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author cpieloth
 */
public class CompanyClientExample {
    public static void main(String[] args) {
        CompanyClient client = new CompanyClient();

        try {
            Company company = client.getCompany(new URI("http://leipzig-data.de/Data/Firma/Autohaus_Auto_Saxe"));
            System.out.println(company);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<Company> companies = client.getCompanies();
        companies.forEach(System.out::println);
        System.out.println("Company count: " + companies.size());
    }
}
