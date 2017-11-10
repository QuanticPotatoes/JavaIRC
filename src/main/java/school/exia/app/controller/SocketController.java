package school.exia.app.controller;

import org.json.JSONObject;
import school.exia.app.model.ClientModel;
import school.exia.main.Cypher;
import school.exia.server.controller.Order;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SocketController implements Cypher {
    private ClientModel model;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private JSONObject json;
    private Thread readListener;
    private boolean isRunning = false;

    public SocketController(ClientModel model) {
        this.model = model;
        json = new JSONObject();
    }

    public void start(String ip, String pseudo) {

        if(isRunning) {
            return;
        }

        isRunning = true;

        try {
            socket = new Socket(ip,2048);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        send(Order.CHANGENAME, pseudo);

        readListener = new Thread(() -> {

            String request = null;
            System.out.println("start");
            while(isRunning) {
                try {
                    request = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (request == null) {
                    break;
                }

                JSONObject jsonRequest = new JSONObject(request);
                switch (jsonRequest.getEnum(Order.class, "order")) {
                    case PUT:
                        model.saveMessage(decode(
                                jsonRequest.getString("message")),
                                jsonRequest.getString("date"),
                                jsonRequest.getString("name"));
                        break;
                    default:
                        break;
                }
            }
            System.out.println("disconnected");
        });

        readListener.start();
    }

    public void send(Order order, String text) {
        JSONObject json = new JSONObject();
        json.put("order", order);
        json.put("message", encode(text));
        writer.println(json.toString());
    }

    public void stop() {
        isRunning = false;
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("interruption");
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
