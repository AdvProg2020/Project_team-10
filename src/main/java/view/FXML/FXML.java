package view.FXML;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class FXML {

    public static String mainMenuURL = "./src/main/java/view/FXML/mainMenu.fxml";
    public static String loginURL = "./src/main/java/view/FXML/loginMenu.fxml";
    public static String goodPageURL = "./src/main/java/view/FXML/goodPage.fxml";

    public static void switchScene(String urlText, Node button) throws IOException {
        Stage stage = ((Stage) button.getScene().getWindow());
        URL url = Paths.get(urlText).toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        DoubleProperty opacity = root.opacityProperty();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(200), new KeyValue(opacity, 1.5))
        );
        fadeIn.play();
    }


}
