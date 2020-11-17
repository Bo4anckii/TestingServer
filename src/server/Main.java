package server;

public class Main {

    private static final RequestListener server = new RequestListener();

    public static void main(String[] args) {
        server.start();
    }
}
