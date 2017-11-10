package school.exia.server.main;

import school.exia.server.controller.ServerController;
import school.exia.server.model.LogWriter;
import school.exia.server.model.ServerModel;

import java.net.ServerSocket;

public class Server
{
    public static void main( String[] args )
    {
        start();
    }

    public static void start() {
        int port = 2048;
        ServerModel model = new ServerModel();
        ServerController controller = new ServerController(port, model);
        controller.start();
    }
}
