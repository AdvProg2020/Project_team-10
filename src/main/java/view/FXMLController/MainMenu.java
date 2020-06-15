package view.FXMLController;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static view.FXML.FXML.*;

public class MainMenu implements Initializable {
    public Button btnLogin;
    public AnchorPane test;
    public Button btnExitPopup;
    public FlowPane flowPane;
    public ScrollPane scrollPane;


    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
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

    public void popupLogin(MouseEvent mouseEvent) throws IOException, URISyntaxException {
        final Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        final AnchorPane layout;
        final AnchorPane anchorPane = new AnchorPane();
        URL url = Paths.get(loginURL).toUri().toURL();
        layout = FXMLLoader.load(url);
        final Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        anchorPane.setStyle("-fx-background-color: #1089ff;" +"-fx-background-radius: 50px;");
        anchorPane.setPrefWidth(550);
        anchorPane.setPrefHeight(600);

        Button button = new Button();
        button.setStyle("-fx-background-image: url('../image/exitpopup.png');" +
                "-fx-background-size: 40px 40px;" + "-fx-pref-width: 40px;" +
                "-fx-pref-height: 40px;" + "-fx-background-color: none;" + "   -fx-background-repeat: no-repeat;;");
        button.setLayoutY(10);
        button.setLayoutX(500);

        //fade
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(400));
        fade.setFromValue(10);
        fade.setToValue(0.5);
        fade.setNode(test);
        fade.play();

        button.setOnAction(e -> fadeOutPopup(popupWindow , fade));

        anchorPane.getChildren().add(button);

        layout.setLayoutX(500);
        layout.setLayoutY(150);
        layout.getChildren().add(anchorPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);

        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene1);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);


        popupWindow.showAndWait();
    }

    public void fadeOutPopup(Stage stage , FadeTransition fade){
        stage.close();
        fade.setDuration(Duration.millis(400));
        fade.setFromValue(0.5);
        fade.setToValue(10);
        fade.setNode(test);
        fade.play();

    }

    public void exitPopup(MouseEvent mouseEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




    }

    public void mouse(MouseEvent mouseEvent) throws URISyntaxException {
        for (int i = 0; i < 70; i++) {
            HBox hBox = new HBox();
            hBox.setPrefWidth(200);
            hBox.setPrefHeight(250);
//            Image image = new Image(getClass().getResourceAsStream("../image/logo.png"));
//            ImageView imageView = new ImageView(image);

            Label label = new Label("salam");
            hBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hBox.setStyle("-fx-background-color: #ffff;" + " -fx-effect:  dropshadow(three-pass-box ,rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");

                }
            });
            hBox.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hBox.setStyle("-fx-background-color: none");
                }
            });
            hBox.getChildren().addAll(label);
            flowPane.getChildren().add(hBox);
        }
        scrollPane.setContent(flowPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
