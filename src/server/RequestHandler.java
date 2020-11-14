package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class RequestHandler extends Thread {

    private final long id;
    private final Socket socket;
    private final RequestListener server;
    private DataInputStream reader;
    private DataOutputStream writer;

    public RequestHandler(Socket socket, RequestListener server, long id) {
        this.socket = socket;
        this.server = server;
        this.id = id;
        try {
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                try {
                    String data = reader.readUTF();
                    switch (data) {
                        //Обработка разных запросов
                    }
                } catch (SocketException ex) {
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                    server.removeClient(this);
                    System.err.println("Подключение " + id + " закрыто");
                }
            }
            System.err.println("Подключение " + id + " закрыто");
        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }
}
