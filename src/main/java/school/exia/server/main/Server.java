package school.exia.server.main;

import school.exia.server.controller.ServerController;

import java.net.ServerSocket;

public class Server
{
    public static void main( String[] args )
    {
        int port = 2048;
        ServerController controller = new ServerController(port);
        controller.start();
    }
}
