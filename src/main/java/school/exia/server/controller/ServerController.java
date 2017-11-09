package school.exia.server.controller;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import school.exia.server.controller.ClientsPool;
import school.exia.server.model.ServerModel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerController {

    private ServerModel model;
    private ClientsPool pool;
    private ServerSocket serverSocket;

    public ServerController(int port, ServerModel model) {
        
        this.model = model;

        try {
            serverSocket = new ServerSocket(port,100, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pool = new ClientsPool(serverSocket, this);
    }

    public void start() {
        pool.open();
    }

    public void saveReceive(String str) {
        JSONObject json = new JSONObject(str);
        Order order = json.getEnum(Order.class,"order");

        switch (order) {
            case PUT:
                broadcast(Order.PUT, json.getString("message"));
                break;
            default:
                break;
        }
    }

    public void logConnection(Socket s) {
        model.logConnection(s);
    }

    public void broadcast(Order order,String message) {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        pool.clientsList.forEach((client) -> {
            client.send(order, message, date.format(new Date()));
        });

    }
}
