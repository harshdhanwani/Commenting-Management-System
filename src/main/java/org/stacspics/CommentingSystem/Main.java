package org.stacspics.CommentingSystem;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "\napplication.wadl\nHit enter to stop it...", server.BASE_URI));
    }
}
