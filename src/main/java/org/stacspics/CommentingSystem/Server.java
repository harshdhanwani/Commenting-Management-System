package org.stacspics.CommentingSystem;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Server {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";
    private HttpServer server;

    /**
     * Method to start the server.
     */
    public void startServer() {

        // create a resource config that scans for JAX-RS resources and providers
        // in org.stacspics.CommentingSystem package
        final ResourceConfig rc = new ResourceConfig().packages("org.stacspics.CommentingSystem");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl.", BASE_URI));
    }

    public void stopServer() {
        server.stop();
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.startServer();
    }

}
