package school.exia.main;

import school.exia.app.main.Client;
import school.exia.server.main.Server;

public class Main {
    public static void main(String[] args) {
        if(args.length != 0 && args[0] == "--server") {
            Server.start();
        }

        Client.start(args);
    }
}
