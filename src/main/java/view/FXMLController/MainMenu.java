package view.FXMLController;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.FXML.FXML;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static view.FXML.FXML.mainMenuURL;
import static view.FXML.FXML.switchScene;

public class MainMenu {
    public Button btnLogin;


    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void minimize(MouseEvent mouseEvent) {
        ((Stage)((Button)mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }
}
