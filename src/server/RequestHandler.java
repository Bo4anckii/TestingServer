package server;

import com.google.gson.Gson;
import models.Test;
import models.TestResult;
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
                        case "getTests":
                            sendTests();
                            break;
                        case "getResults":
                            sendResults();
                            break;
                        case "test":
                            getTest();
                            break;
                        case "result":
                            getResult();
                            break;
                        case "deleteTest":
                            deleteTest();
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
        System.out.println("Received: getTests");
        List<Test> tests = testDao.getTests();
        try {
            for (Test test : tests) {
                writer.writeUTF(gson.toJson(test));
                System.out.println("Sent: "+gson.toJson(test));
            }
            writer.writeUTF("end");
            System.out.println("Sent: end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResults() {
        System.out.println("Received: getResults");
        List<TestResult> results = testDao.getResults();
        try {
            for (TestResult result : results) {
                writer.writeUTF(gson.toJson(result));
                System.out.println("Sent: "+gson.toJson(result));
            }
            writer.writeUTF("end");
            System.out.println("Sent: end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTest() {
        System.out.println("Received: test");
        try {
            Test test = gson.fromJson(reader.readUTF(), Test.class);
            testDao.postTest(test);
            System.out.println("Test has been written to the DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getResult() {
        System.out.println("Received: result");
        try {
            TestResult result = gson.fromJson(reader.readUTF(), TestResult.class);
            testDao.postResult(result);
            System.out.println("Result has been written to the DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteTest() {
        System.out.println("Received: deleteTest");
        try {
            testDao.deleteTest(reader.readUTF());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
