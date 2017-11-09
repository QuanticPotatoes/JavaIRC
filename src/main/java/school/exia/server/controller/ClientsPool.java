package school.exia.server.controller;

import school.exia.app.controller.SocketController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ClientsPool {
    private ServerSocket serverSocket;
    private Thread poolThread;
    private ServerController controller;

    private final ThreadPoolExecutor executor;

    public boolean isRunning = true;
    public List<ClientThread> clientsList;

    public ClientsPool(ServerSocket serverSocket, ServerController controller) {
        this.serverSocket = serverSocket;
        this.controller = controller;

        clientsList = new ArrayList<>();
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void open() {
        poolThread = new Thread(()-> {
            while (isRunning) {
                Socket socketClient;
                ClientThread threadClient = null;
                try {
                    socketClient = serverSocket.accept();
                    addClient(socketClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        poolThread.start();
    }

    public void addClient(Socket socketClient) {
        ClientThread clientThread = new ClientThread(socketClient, controller, this);
        clientsList.add(clientThread);
        executor.execute(clientThread);
        System.out.println("New connection: " + clientsList.size() + " users");
        controller.logConnection(socketClient);
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
