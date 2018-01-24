import org.apollon.core.FTPServer;
import org.apollon.entity.User;
import org.apollon.ftp.UserRights;

import java.util.Arrays;
import java.util.Collections;


public class Server {

    public static void main(String[] args) {
        User defaultUser = new User("Botan", "123456", Arrays.asList(UserRights.READ, UserRights.WRITE));

        new FTPServer(2045, Collections.singletonList(defaultUser)).bind();
    }

}
