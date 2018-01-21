import org.apollon.core.FTPClient;
import org.apollon.entity.User;

import java.net.InetSocketAddress;


public class Client {

    public static void main(String[] args) throws Exception {
        new FTPClient(new InetSocketAddress(2045), new User("Botan", "Ahmad"));
    }

}
