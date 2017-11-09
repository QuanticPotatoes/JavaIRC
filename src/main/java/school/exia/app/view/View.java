package school.exia.app.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import school.exia.app.controller.SocketController;

import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

public class View extends Application implements Observer {

    private SocketController controller;

    @FXML
    public TextField testInput;

    public View(SocketController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Paths.get("src/main/java/school/exia/app/view/ui.fxml").toUri().toURL());
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("javaIRC");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            try {
                stop();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @FXML
    public void sendClick() {
        System.out.println(testInput.getText());
        controller.send(testInput.getText());
    }
}
