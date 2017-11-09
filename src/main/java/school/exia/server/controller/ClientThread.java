package school.exia.server.controller;


import org.json.JSONObject;
import school.exia.app.controller.SocketController;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientThread implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ServerController controller;
    private ClientsPool pool;

    public ClientThread(Socket socket, ServerController controller, ClientsPool pool) {
        this.socket = socket;
        this.controller = controller;
        this.pool = pool;

        try {
            writer = new PrintWriter(socket.getOutputStream(),true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        String s = null;

        while(true) {
            try {
                s = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (s == null) {
                break;
            }

            controller.saveReceive(s);
            System.out.println(s);
        }

        System.out.println("user " + pool.clientsList.indexOf(this) + " was disconnected");

        pool.clientsList.remove(this);
    }

    public void send(Order order, String message, String date) {
        JSONObject json = new JSONObject();
        json.put("order", order);
        json.put("message", message);
        json.put("date", date);
        writer.println(json.toString());
    }
}
