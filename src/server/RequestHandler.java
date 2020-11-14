package server;

import com.google.gson.Gson;
import models.Test;
import server.db.TestDao;
import server.db.TestDaoImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler extends Thread {

    private final long id;
    private final Socket socket;
    private final RequestListener server;
    private DataInputStream reader;
    private DataOutputStream writer;
    private final TestDao testDao = new TestDaoImpl();
    private final Gson gson = new Gson();

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
                        case "getTests": sendTests();
                    }
                } catch (SocketException ex) {
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                    server.removeClient(this);
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

    private void sendTests() {
        List<Test> tests = testDao.getTests();
        try {
            for (Test test : tests) {
                writer.writeUTF(gson.toJson(test));
            }
            writer.writeUTF("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
