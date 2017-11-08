package school.exia.server.controller;

import school.exia.server.controller.ClientsPool;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerController {

    private ClientsPool pool;
    private ServerSocket serverSocket;

    public ServerController(int port) {
        try {
            serverSocket = new ServerSocket(port,100, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pool = new ClientsPool(serverSocket);
    }

    public void start() {
        pool.open();
    }
}
