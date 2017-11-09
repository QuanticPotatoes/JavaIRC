package school.exia.app.controller;

import org.json.JSONObject;
import school.exia.app.model.ClientModel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketController {
    private ClientModel model;
    private Socket socket;
    private PrintWriter writer;
    private BufferedInputStream reader;
    private JSONObject json;

    public SocketController(ClientModel model) {
        this.model = model;
        json = new JSONObject();
    }

    public void start() {
        try {
            socket = new Socket("127.0.0.1",2048);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        writer.println(text);
    }
}
