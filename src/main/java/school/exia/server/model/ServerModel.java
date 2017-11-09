package school.exia.server.model;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerModel {

    public ServerModel() {
    }

    public void logConnection(Socket socket) {
        System.out.println(socket.getInetAddress().getHostAddress());
        LogWriter.getInstance().log((socket.isClosed())? "CLOSE ":"OPEN "
                + socket.getInetAddress().getHostAddress()
                + " - "
                + new SimpleDateFormat("d H:mm:ss").format(new Date()));
    }
}
