package school.exia.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsPool {
    private ServerSocket serverSocket;
    private Thread poolThread;
    public boolean isRunning = true;
    public List<ClientThread> clientsList;

    public ClientsPool(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        clientsList = new ArrayList<>();
    }

    public void open() {
        poolThread = new Thread(()-> {
            while (isRunning) {
                Socket socketClient;
                ClientThread threadClient = null;

                try {
                    socketClient = serverSocket.accept();
                    threadClient = new ClientThread(socketClient);
                    clientsList.add(threadClient);
                    threadClient.run();

                    System.out.println("Nouvelle connexion");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        poolThread.start();
    }
}
