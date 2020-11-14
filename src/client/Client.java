package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;

    public Client() {
        try {
            socket = new Socket("localhost", 3214);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Сокет не открылся");
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                switch (reader.readUTF()) {
                    //Обработка разных ответов сервера
                }
                reader.close();
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void testConnection() {
        try {
            writer.writeUTF("test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
