package school.exia.app.view;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import school.exia.app.controller.SocketController;
import school.exia.server.controller.Order;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View extends Application implements Observer {

    private SocketController controller;

    @FXML
    public TextField testInput;
    @FXML
    public ListView<String> listView;
    @FXML
    public JFXTextField serverInput;
    @FXML
    public JFXTextField pseudoInput;

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
                controller.stop();
                stop();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<String> list = (ArrayList<String>) arg;
        ObservableList<String> items = FXCollections.observableArrayList(list);
        Platform.runLater(() -> listView.setItems(items));
    }

    @FXML
    public void sendClick() {

        if(testInput.getText().length() == 0) {
            return;
        }
        controller.send(Order.PUT, testInput.getText());
        testInput.clear();
    }

    @FXML
    public void sendServerIP() {
        controller.start(serverInput.getText(), pseudoInput.getText());
    }

    @FXML
    public void onKeyPress(KeyEvent e) {
        if(e.getCode().equals(KeyCode.ENTER)) {
            sendClick();
        }
    }
}
