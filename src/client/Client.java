package client;

import com.google.gson.Gson;
import models.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {

    private Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;
    private final Gson gson = new Gson();

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
            while (!socket.isClosed()) {}
            reader.close();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Test> getTests() {
        ArrayList<Test> tests = new ArrayList<>();
        String data;
        try {
            writer.writeUTF("getTests");
            while (!(data=reader.readUTF()).equals("end")){
                System.out.println("while: "+data);
                tests.add(gson.fromJson(data, Test.class));
            }
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tests;
    }

}
