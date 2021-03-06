package school.exia.app.main;

import javafx.application.Application;
import javafx.stage.Stage;
import school.exia.app.controller.SocketController;
import school.exia.app.model.ClientModel;
import school.exia.app.view.View;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    private static View view;

    public static void main(String[] args) {
        start(args);
    }

    public static void start(String[] args) {
        ClientModel model = new ClientModel();
        SocketController controller = new SocketController(model);
        view = new View(controller);
        model.addObserver(view);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        view.start(primaryStage);
    }
}
