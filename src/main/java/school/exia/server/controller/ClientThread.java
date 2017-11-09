package school.exia.server.controller;


import school.exia.app.controller.SocketController;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientThread implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ServerController controller;

    public ClientThread(Socket socket, ServerController controller) {
        this.socket = socket;
        this.controller = controller;

        try {
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

        System.out.println("disconnected");
    }
}
