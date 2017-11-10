package school.exia.server.controller;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import school.exia.main.Cypher;
import school.exia.server.controller.ClientsPool;
import school.exia.server.model.ServerModel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ServerController implements Cypher {

    private ServerModel model;
    private ClientsPool pool;
    private ServerSocket serverSocket;

    public ServerController(int port, ServerModel model) {
        
        this.model = model;

        try {
            serverSocket = new ServerSocket(port,100, InetAddress.getByName("10.154.128.180"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pool = new ClientsPool(serverSocket, this);
    }

    public void start() {
        pool.open();
    }

    public void saveReceive(String str, ClientThread client) {
        JSONObject json = new JSONObject(str);
        Order order = json.getEnum(Order.class,"order");

        switch (order) {
            case PUT:
                broadcast(Order.PUT, decode(json.getString("message")), client.name);
                break;
            case CHANGENAME:
                client.name = decode(json.getString("message"));
            default:
                break;
        }
    }

    public void logConnection(Socket s) {
        model.logConnection(s);
    }

    public void broadcast(Order order, String message, String name) {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        pool.clientsList.forEach((client) -> {
            client.send(order, encode(message), date.format(new Date()), name);
        });
    }

    @Override
    public String encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String decode(String str) {
        return new String(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8)));
    }
}
