package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RequestListener extends Thread {
    private ServerSocket socket;
    private final ArrayList<RequestHandler> clients;

    public RequestListener() {
        clients = new ArrayList<>();
        try {
            socket = new ServerSocket(3214);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connection = socket.accept();
                long id = makeID();
                RequestHandler handler = new RequestHandler(connection, this, id);
                clients.add(handler);
                handler.start();
                System.out.println("Подключение "+id+" открыто");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Сокет не открылся");
            }
        }
    }

    public void removeClient(RequestHandler client) {
        clients.remove(client);
        System.out.println("Пользователь "+client.getId()+" удалён");
    }

    private long makeID(){
        long id=0;
        for (RequestHandler client: clients) {
            if(client.getId()>=id){
                id = client.getId()+1;
            }
        }
        return id;
    }
}
