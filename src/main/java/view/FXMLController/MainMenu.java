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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Good;
import model.Shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public Rectangle header;


    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void minimize(MouseEvent mouseEvent) {
        ((Stage) ((Button) mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    public static void fadeEffect(Node object) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(200));
        fadeTransition.setFromValue(8);
        fadeTransition.setToValue(10);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setNode(object);
        fadeTransition.play();
    }

    public void popupLogin(MouseEvent mouseEvent) throws IOException {
        final Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        final AnchorPane layout;
        final AnchorPane anchorPane = new AnchorPane();
        URL url = Paths.get(loginURL).toUri().toURL();
        layout = FXMLLoader.load(url);
        final Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        anchorPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        anchorPane.setPrefWidth(480);
        anchorPane.setPrefHeight(600);

        Button button = new Button();
        button.getStyleClass().add("btnExit");
        button.setLayoutY(30);
        button.setLayoutX(435);


        //fade
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(400));
        fade.setFromValue(10);
        fade.setToValue(0.5);
        fade.setNode(test);
        fade.play();

        button.setOnAction(e -> fadeOutPopup(popupWindow, fade));


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

        anchorPane.getChildren().addAll(button , usernameField() , passwordField() ,login() ,newUserLabel() , signUpLink());
        popupWindow.showAndWait();


    }

    public Button login(){
        Button button = new Button();
        button.setText("Sign In");
        button.setPrefSize(290 , 55);
        button.setLayoutX(100);
        button.setLayoutY(330);
        button.getStyleClass().add("login");
        return button;
    }

    public TextField usernameField(){
        TextField textField = new TextField();
        textField.setPromptText("Username");
        textField.setLayoutX(100);
        textField.setLayoutY(190);
        textField.setPrefHeight(50);
        textField.setPrefWidth(290);
        textField.getStyleClass().add("username");

        return textField;
    }

    public Label newUserLabel(){
        Label label = new Label("Are you a new user?");
        label.setLayoutY(400);
        label.setLayoutX(130);
        label.getStyleClass().add("label");

        return label;
    }

    public Hyperlink signUpLink(){
    Hyperlink hyperlink = new Hyperlink("Sign Up");
    hyperlink.setLayoutX(286);
    hyperlink.setLayoutY(396);
    hyperlink.getStyleClass().add("hyperLink");
    return hyperlink;
    }

    public PasswordField passwordField(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setLayoutX(100);
        passwordField.setLayoutY(255);
        passwordField.setPrefHeight(50);
        passwordField.setPrefWidth(290);
        passwordField.getStyleClass().add("password-field");
        return passwordField;
    }

    public void fadeOutPopup(Stage stage, FadeTransition fade) {
        stage.close();
        fade.setDuration(Duration.millis(400));
        fade.setFromValue(0.5);
        fade.setToValue(10);
        fade.setNode(test);
        fade.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label sort = new Label("sorted by");
        sort.setStyle("-fx-graphic: url('../image/sorticon.png');" + "-fx-font-size: 25px");
        scrollPane.setContent(sort);


        for (Good good : Shop.getShop().getAllGoods()) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(297);
            vBox.setPrefHeight(350);
            vBox.setStyle("-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;");

            ImageView imageView = new ImageView(new Image("file:src/main/java/view/image/logo.png"));
            imageView.setFitHeight(190);
            imageView.setFitWidth(190);

            Label name = new Label(good.getName());
            Label price = new Label(good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            vBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    vBox.setStyle("-fx-background-color: #ffffff;" +
                            " -fx-effect:  dropshadow(three-pass-box ,rgba(19,19,19,0.07), 30,0.0015, 0.0, 0.0);"
                            + "-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;");
                    fadeEffect(vBox);
                }
            });
            vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    vBox.setStyle("-fx-background-color: none;" + "-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;");
                }
            });
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(imageView, name, price, visit);
            flowPane.getChildren().add(vBox);
        }

        flowPane.setStyle("-fx-background-color: white;");

        scrollPane.setContent(flowPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void mouse(MouseEvent mouseEvent) throws URISyntaxException {

    }
}
