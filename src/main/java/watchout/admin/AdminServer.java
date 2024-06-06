package watchout.admin;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

public class AdminServer {
    private static final String HOST = "localhost";
    private static final int  PORT = 1337;

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServerFactory.create("http://" + HOST + ":" + PORT + "/");
            server.start();

            // TODO: stop server?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
