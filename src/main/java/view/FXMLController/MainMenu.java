package view.FXMLController;

import controller.AccountManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Good;
import model.Shop;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static view.FXML.FXML.*;

public class MainMenu implements Initializable {
    public static double x;
    public static int y;
    public Button btnLogin;
    public AnchorPane mainPane;
    public Button btnExitPopup;
    public FlowPane flowPane;
    public ScrollPane scrollPane;
    public Rectangle header;
    public AnchorPane anchorPane;
    public TextField usernameField;
    public PasswordField passwordField;
    public Label error;
    public AnchorPane layout;
    public Stage popupWindow;





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
        anchorPane = new AnchorPane();
        usernameField = new TextField();
        passwordField = new PasswordField();
        error = new Label();
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        URL url = Paths.get(loginURL).toUri().toURL();
        layout = FXMLLoader.load(url);
        Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

        Label onlineShop = new Label("Sign in to\n" +
                "OnlineShop");
        onlineShop.getStyleClass().add("labelForLoginTitle");
        onlineShop.setLayoutX(100);
        onlineShop.setLayoutY(130);

        layout.setStyle("-fx-background-color: none;");
        anchorPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        anchorPane.setPrefWidth(480);
        anchorPane.setPrefHeight(600);

        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExit");
        exitButton.setLayoutY(30);
        exitButton.setLayoutX(435);


        //fade
        fade(10, 0.5);

        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        anchorPane.setPrefHeight(550);

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
        anchorPane.getChildren().addAll(exitButton, usernameField(), passwordField(), loginButton(), newUserLabel(), signUpLink(), error);
        anchorPane.getChildren().addAll(exit(popupWindow), onlineShop, usernameField(), passwordField(),
                loginButton(), newUserLabel(), signUpLink());
        popupWindow.showAndWait();



    }

    public Button loginButton() {
        Button button = new Button();
        button.setText("Sign In");
        button.setPrefSize(290, 55);
        button.setLayoutX(100);
        button.setLayoutY(370);
        button.getStyleClass().add("login");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                processLogin();
            }
        });
        return button;
    }

    private void processLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username);
        System.out.println(password);
        if (AccountManager.login(username, password)) {
            System.out.println("ok");
            popupWindow.close();
            fade(0.5, 10);
        } else {
            error.setText("username/password is incorrect");
            error.setLayoutX(150);
            error.setLayoutY(500);
        }
    }

    public TextField usernameField() {
        usernameField.setPromptText("Username");
        usernameField.setLayoutX(100);
        usernameField.setLayoutY(230);
        usernameField.setPrefHeight(50);
        usernameField.setPrefWidth(290);
        usernameField.getStyleClass().add("username");
        return usernameField;
    }

    public Label newUserLabel() {
        Label label = new Label("Are you a new user?");
        label.setLayoutY(432);
        label.setLayoutX(130);
        label.getStyleClass().add("label");
        return label;
    }

    public Hyperlink signUpLink() {
        Hyperlink hyperlink = new Hyperlink("Sign Up");
        hyperlink.setLayoutX(286);
        hyperlink.setLayoutY(428);
        hyperlink.getStyleClass().add("hyperLink");
        hyperlink.setOnMouseClicked(e -> signUp());
        return hyperlink;
    }


    public PasswordField passwordField() {
        passwordField.setPromptText("Password");
        passwordField.setLayoutX(100);
        passwordField.setLayoutY(295);
        passwordField.setPrefHeight(50);
        passwordField.setPrefWidth(290);
        passwordField.getStyleClass().add("password-field");
        return passwordField;
    }

    public void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox hBox = new HBox();
        ImageView imageSort = new ImageView(new Image("file:src/main/java/view/image/sorticon.png"));
        imageSort.setFitWidth(25);
        imageSort.setFitHeight(25);
        Label sort = new Label("Sort by:");
        sort.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: black;" + "-fx-font-family: sans-serif;");

        hBox.getChildren().addAll(imageSort, sort, buttonForSort("Time"), buttonForSort("Score"),
                buttonForSort("Price(Descending)"), buttonForSort("The most visited"));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 580, 10, 15));
        hBox.setSpacing(10);
        flowPane.getChildren().add(hBox);
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

    public Button buttonForSort(String input) {
        Button button = new Button(input);
        button.getStyleClass().add("buttonSort");
        return button;
    }


    private static File selectedFile;

    public void signUp() {
        anchorPaneForLogin.getChildren().clear();
        imageViewForSignUp();
        anchorPaneForLogin.getChildren().addAll(exit(stage) ,
                textFieldForSignUp("First name" , 40 , 140),
                textFieldForSignUp("Last name" , 40 , 190),
                usernameFieldForSignUp());

    }

    public void imageViewForSignUp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
                , new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage stage = new Stage();

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(120);
        rectangle.setWidth(100);
        rectangle.setLayoutX(320);
        rectangle.setLayoutY(80);
        rectangle.setStyle("-fx-fill: white;" + "-fx-border-width: 20px");
        Button button = new Button("Select a photo");
        button.setLayoutX(320);
        button.setLayoutY(210);
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color: white;" + " -fx-background-radius: 10;"+"-fx-fill: black;"+"-fx-font-family: sans-serif;");
        anchorPaneForLogin.getChildren().addAll(button, rectangle);
        button.setOnAction(e -> {
            selectedFile = fileChooser.showOpenDialog(stage);
            System.out.println(selectedFile);
            ImageView imageView = new ImageView(new Image("file:" + selectedFile));
            imageView.setFitHeight(110);
            imageView.setFitWidth(100);
            imageView.setLayoutX(320);
            imageView.setLayoutY(80);
            imageView.setStyle("-fx-background-radius: 10;" + "-fx-border-width: 2px;"+"-fx-border-radius: 10;"
                    + "-fx-border-color: white;" + "-fx-border-image-width: 2px;" + "-fx-background-color: white;"
            + "-fx-fill: white;" + "-fx-fill-height: white");
            imageView.autosize();
            anchorPaneForLogin.getChildren().addAll(imageView);
        });


    }

    public TextField textFieldForSignUp(String prompt , int x , int y){
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefHeight(40);
        textField.setPrefWidth(250);
        textField.getStyleClass().add("text-fieldForSignUp");
        return textField;
    }

    public TextField usernameFieldForSignUp(){
        TextField textField = new TextField();
        textField.setPromptText("username");
        textField.setLayoutX(40);
        textField.setLayoutY(240);
        textField.setPrefHeight(40);
        textField.setPrefWidth(380);
        textField.getStyleClass().add("usernameSignUp");
        return textField;
    }


    public PasswordField passwordFieldForSignUp(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setLayoutX(40);
        passwordField.setLayoutY(100);
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(230);
        passwordField.getStyleClass().add("password-fieldSignUp");
        return passwordField;
    }

    public void cartMenu(MouseEvent mouseEvent) {

    }
}
