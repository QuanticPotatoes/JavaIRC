package school.exia.app.controller;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import school.exia.app.model.ClientModel;
import school.exia.server.controller.Order;

import java.io.*;
import java.net.Socket;

public class SocketController {
    private ClientModel model;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private JSONObject json;
    private Thread readListener;
    private boolean isRunning = true;

    public SocketController(ClientModel model) {
        this.model = model;
        json = new JSONObject();
    }

    public void start() {
        try {
            socket = new Socket("127.0.0.1",2048);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                        model.saveMessage(jsonRequest.getString("message"), jsonRequest.getString("date"));
                        break;
                    default:
                        break;
                }
            }
            System.out.println("disconnected");
        });

        readListener.start();
    }

    public void send(String text) {
        JSONObject json = new JSONObject();
        json.put("order", Order.PUT);
        json.put("message", text);
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
}
