package school.exia.server.controller;


import org.json.JSONObject;
import school.exia.app.controller.SocketController;
import school.exia.main.Cypher;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ClientThread implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ServerController controller;
    private ClientsPool pool;
    public String name;

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

    /**
     * ReadListener
     */
    public void run() {

        String s = null;

        while(true) {
            try {
                /* waiting receive information from socket */
                s = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (s == null) {
                break;
            }

            controller.saveReceive(s, this);
            System.out.println(s);
        }

        System.out.println("user " + pool.clientsList.indexOf(this) + " was disconnected");

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.logConnection(socket);
        pool.clientsList.remove(this);
    }

    public void send(Order order, String message, String date, String name) {
        JSONObject json = new JSONObject();
        json.put("order", order);
        json.put("message", message);
        json.put("date", date);
        json.put("name", name);
        writer.println(json.toString());
    }
}
