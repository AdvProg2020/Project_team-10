package view.FXMLController;

import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static view.FXML.FXML.loginURL;

public class GoodPage implements Initializable {
    public AnchorPane mainPane;
    public ScrollPane scrollPane;
    public AnchorPane innerPane;


    public void exit(MouseEvent mouseEvent) {
        new MainMenu().exit(mouseEvent);
    }

    public void minimize(MouseEvent mouseEvent) {
        ((Stage) ((Button) mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    public static void fadeEffect(Node object) {
        DoubleProperty opacity = object.opacityProperty();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(200), new KeyValue(opacity, 1.5))
        );
        fadeIn.play();
    }

    public void popupLogin(MouseEvent mouseEvent) throws IOException {
        new MainMenu().popupLogin(mouseEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView goodImage = new ImageView(new Image("file:" + GoodsManager.getCurrentGood().getImagePath()));
        goodImage.setFitWidth(500);
        goodImage.setFitHeight(500);
        goodImage.setLayoutX(50);
        goodImage.setLayoutY(230);
    }
}

