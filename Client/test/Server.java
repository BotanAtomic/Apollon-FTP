import org.apollon.core.FTPServer;


public class Server {

    public static void main(String[] args) throws Exception {

        new FTPServer(2045).bind();
    }

}
