package examples;

import de.leipzigdata.address.AddressClient;
import de.leipzigdata.address.entity.Address;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author cpieloth
 */
public class AddressClientExample {
    public static void main(String[] args) {
        AddressClient client = new AddressClient();

        try {
            Address address = client.getAddress(new URI("http://leipzig-data.de/Data/04179.Leipzig.AmKanal.28"));
            System.out.println(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<Address> addresses = client.getAddresses();
        System.out.println("Address count: " + addresses.size());
        addresses.forEach(System.out::println);
    }
}
