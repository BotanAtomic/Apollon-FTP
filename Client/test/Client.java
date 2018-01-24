import org.apollon.entity.ClientUser;
import org.apollon.network.core.FTPClient;

import java.net.InetSocketAddress;


public class Client {

    public static void main(String[] args) throws Exception {
       FTPClient client = new FTPClient(new InetSocketAddress(2045));

       client.setEventHandler(event -> System.err.println("New event " + event.getEventType().name() + " / code " + event.getCode()));

       client.onConnect(() -> {

       });

       client.connect(new ClientUser("Botan", "123456"));

    }

}
