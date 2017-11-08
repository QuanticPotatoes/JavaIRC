package school.exia.server.controller;

import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private BufferedInputStream reader;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("connexion client");
    }
}
