package school.exia.app.model;

import java.util.ArrayList;
import java.util.Observable;

public class ClientModel extends Observable {

    private ArrayList<String> messageList;

    public ClientModel() {
        messageList = new ArrayList<>();
    }

    public void saveMessage(String message, String date) {
        messageList.add("[" +date + "] -> " + message);
        this.setChanged();
        notifyObservers(messageList);
    }
}
