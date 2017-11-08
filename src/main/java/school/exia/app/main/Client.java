package school.exia.app.main;

import javafx.application.Application;
import javafx.stage.Stage;
import school.exia.app.view.View;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    private static View view;

    public static void main(String[] args) {
/*        int port = 2048;
        try {
            Socket test = new Socket("127.0.0.1",port);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        view = new View();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        view.start(primaryStage);
    }
}
