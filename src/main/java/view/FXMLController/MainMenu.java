package view.FXMLController;

import controller.AccountManager;
import controller.FileHandler;
import controller.GoodsManager;
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
import view.CommandProcessor;
import view.NumberField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.ResourceBundle;

import static view.FXML.FXML.*;

public class MainMenu implements Initializable {
    public static double x;
    public static int y;
    public Button btnLogin;
    public AnchorPane mainPane;
    public FlowPane flowPane;
    public ScrollPane scrollPane;
    public Rectangle header;
    public AnchorPane anchorPane;
    public TextField usernameField;
    public PasswordField passwordFieldForSignIn;
    public Label error;
    public AnchorPane layout;
    public Stage popupWindow;
    private static File selectedFile;
    public Button selectedButton = new Button("The most visited");
    private boolean isBuyer;


    public void exit(MouseEvent mouseEvent) {
//        FileHandler.write();
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
        passwordFieldForSignIn = new PasswordField();
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
        onlineShop.setLayoutY(100);

        layout.setStyle("-fx-background-color: none;");
        anchorPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        anchorPane.setPrefWidth(480);
        anchorPane.setPrefHeight(580);


        //fade
        fade(10, 0.5);


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
        anchorPane.getChildren().addAll(exitButton(), usernameField(), passwordField(), onlineShop, loginButton(),
                newUserLabel(), signUpLink(), error);
        popupWindow.showAndWait();

    }

    private Button exitButton() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExit");
        exitButton.setLayoutY(30);
        exitButton.setLayoutX(435);
        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
    }

    private Button loginButton() {
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

    private TextField usernameField() {
        usernameField.setPromptText("Username");
        usernameField.setLayoutX(100);
        usernameField.setLayoutY(230);
        usernameField.setPrefHeight(50);
        usernameField.setPrefWidth(290);
        usernameField.getStyleClass().add("username");
        return usernameField;
    }

    private PasswordField passwordField() {
        passwordFieldForSignIn.setPromptText("Password");
        passwordFieldForSignIn.setLayoutX(100);
        passwordFieldForSignIn.setLayoutY(295);
        passwordFieldForSignIn.setPrefHeight(50);
        passwordFieldForSignIn.setPrefWidth(290);
        passwordFieldForSignIn.getStyleClass().add("password-field");
        return passwordFieldForSignIn;
    }

    private Label newUserLabel() {
        Label label = new Label("Are you a new user?");
        label.setLayoutY(432);
        label.setLayoutX(130);
        label.getStyleClass().add("label");
        return label;
    }

    private Hyperlink signUpLink() {
        Hyperlink hyperlink = new Hyperlink("Sign Up");
        hyperlink.setLayoutX(286);
        hyperlink.setLayoutY(428);
        hyperlink.getStyleClass().add("hyperLink");
        hyperlink.setOnMouseClicked(e -> signUp());
        return hyperlink;
    }

    public void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    private void processLogin() {
        String username = usernameField.getText();
        String password = passwordFieldForSignIn.getText();
        System.out.println(username);
        System.out.println(password);
        if (AccountManager.login(username, password)) {
            popupWindow.close();
            fade(0.5, 10);
        } else {
            error.setText("username/password is incorrect");
            error.setLayoutX(120);
            error.setLayoutY(470);
            error.setTextFill(Color.RED);
        }
    }

     public void processRegister() {
         String firstName1 = firstName.getText();
         String lastName1 = lastName.getText();
         String username = usernameFieldForSignUp.getText();
         String password = passwordFieldForSignUp.getText();
         String email1 = email.getText();
         String phoneNumber1 = phoneNumber.getText();
         String type;
         String company1 = company.getText();
         String imagePath = selectedFile.getAbsolutePath();
         if (isBuyer) {
             type = "buyer";
         } else {
             type = "seller";
         }
         System.out.println(imagePath);
         if (CommandProcessor.checkPasswordInvalidation(password)) {
             if (CommandProcessor.checkEmailInvalidation(email1)) {
                 if (AccountManager.canRegister(username)) {
                     AccountManager.register(username, password, type, firstName1, lastName1, email1, phoneNumber1, company1);
                     popupWindow.close();
                     fade(0.5, 10);
                 } else {
                     error.setText("customer exist with this username");
                     error.setLayoutX(100);
                     error.setLayoutY(500);
                     error.setTextFill(Color.DARKRED);
                 }
             } else {
                 error.setText("invalid email");
                 error.setLayoutX(100);
                 error.setLayoutY(500);
                 error.setTextFill(Color.DARKRED);
             }
         } else {
             error.setText("invalid password");
             error.setLayoutX(100);
             error.setLayoutY(500);
             error.setTextFill(Color.DARKRED);
         }
     }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox hBox = new HBox();
        ImageView imageSort = new ImageView(new Image("file:src/main/java/view/image/sorticon.png"));
        imageSort.setFitWidth(25);
        imageSort.setFitHeight(25);
        Label sort = new Label("Sort by:");
        sort.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: black;" + "-fx-font-family: sans-serif;");

        hBox.getChildren().addAll(imageSort, sort, buttonForSort("Time", location, resources), buttonForSort("Score", location, resources),
                buttonForSort("Price(Descending)", location, resources), buttonForSort("The most visited", location, resources));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 580, 10, 15));
        hBox.setSpacing(10);
        flowPane.getChildren().add(hBox);
        Collections.sort(GoodsManager.getFilteredGoods());
        for (Good good : GoodsManager.getFilteredGoods()) {
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
            vBox.setOnMouseEntered(event -> {
                vBox.setStyle("-fx-background-color: #ffffff;" +
                        " -fx-effect:  dropshadow(three-pass-box ,rgba(19,19,19,0.07), 30,0.0015, 0.0, 0.0);"
                        + "-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;");
                fadeEffect(vBox);
            });
            vBox.setOnMouseExited(event -> vBox.setStyle("-fx-background-color: none;" + "-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;"));
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(imageView, name, price, visit);
            flowPane.getChildren().add(vBox);
        }

        flowPane.setStyle("-fx-background-color: white;");

        scrollPane.setContent(flowPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public Button buttonForSort(String input, URL location, ResourceBundle resources) {
        Button button = new Button(input);
        button.getStyleClass().add("buttonSort");
        if (input.equals("Time")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("time");
                sort(location, resources);
            });
            if (selectedButton.getText().equals("Time")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else if (input.equals("Score")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("score");
                sort(location, resources);
            });
            if (selectedButton.getText().equals("Score")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else if (input.startsWith("Price")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("price");
                sort(location, resources);
            });
            if (selectedButton.getText().startsWith("Price")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("visit number");
                sort(location, resources);
            });
            if (selectedButton.getText().startsWith("The")) {
                button.getStyleClass().add("buttonSort-select");
            }
        }
        return button;
    }

    public void sort(URL location, ResourceBundle resources) {
        flowPane.getChildren().clear();
        initialize(location, resources);
    }

    private TextField company;

    public void signUp() {
        anchorPane.getChildren().clear();
        imageViewForSignUp();

        isBuyer = true;
        company = new TextField();
        company.setPromptText("Company");
        company.setLayoutX(110);
        company.setLayoutY(445);
        company.setPrefWidth(200);
        company.setPrefHeight(50);
        company.setVisible(false);
        company.getStyleClass().add("text-fieldForSignUp");
        anchorPane.getChildren().add(company);

        Button sellerType = typeOfSignUp("Seller", 445);
        sellerType.getStyleClass().add("typeField");
        sellerType.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isBuyer = false;
                company.setVisible(true);
            }
        });

        Button buyerType = typeOfSignUp("Buyer", 470);

        buyerType.getStyleClass().add("typeField");
        buyerType.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isBuyer = true;
                company.setVisible(false);
            }
        });

        Button signUp = new Button("Sign Up");
        signUp.setLayoutY(445);
        signUp.setLayoutX(320);
        signUp.setPrefHeight(40);
        signUp.setPrefWidth(120);
        signUp.getStyleClass().add("signUp");
        signUp.setOnMouseClicked(e -> System.out.println(isBuyer));
        firstName = textFieldForSignUp("First name", 40, 140);
        lastName = textFieldForSignUp("Last name", 40, 190);

        anchorPane.getChildren().addAll(exitButton(), firstName,
                lastName, usernameForSignUp(),
                passwordFieldSignUp(), emailFieldSignUp(), phoneNumberFiledSignUp(),
                sellerType, buyerType, signUp, error);

        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                processRegister();
            }
        });
    }

    private TextField firstName;
    private TextField lastName;
    private TextField usernameFieldForSignUp;
    private PasswordField passwordFieldForSignUp;
    private TextField email;
    private NumberField phoneNumber;

    public Button typeOfSignUp(String text, int y) {
        Button type = new Button(text);
        type.setLayoutX(40);
        type.setLayoutY(y);
        type.setPrefWidth(60);
        type.setPrefHeight(20);
        return type;
    }

    public void imageViewForSignUp() {
        Label titleOFSignUp = new Label("+ SIGN UP");
        titleOFSignUp.setLayoutY(90);
        titleOFSignUp.setLayoutX(40);
        titleOFSignUp.getStyleClass().add("labelForLoginTitle");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
                , new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage stage = new Stage();

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(120);
        rectangle.setWidth(100);
        rectangle.setLayoutX(340);
        rectangle.setLayoutY(80);
        rectangle.setStyle("-fx-fill: white;" + "-fx-border-width: 20px");

        Button button = new Button("Select a photo");
        button.setLayoutX(340);
        button.setLayoutY(206);
        button.setPrefWidth(100);
        button.getStyleClass().add("selectPhoto");

        anchorPane.getChildren().addAll(titleOFSignUp, button, rectangle);
        button.setOnAction(e -> {
            selectedFile = fileChooser.showOpenDialog(stage);
            System.out.println(selectedFile);
            ImageView imageView = new ImageView(new Image("file:" + selectedFile));
            imageView.setFitHeight(120);
            imageView.setFitWidth(100);
            imageView.setLayoutX(340);
            imageView.setLayoutY(80);
            imageView.getStyleClass().add("imageView");
            imageView.autosize();
            anchorPane.getChildren().addAll(imageView);
        });


    }

    public TextField textFieldForSignUp(String prompt, int x, int y) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefHeight(40);
        textField.setPrefWidth(290);
        textField.getStyleClass().add("text-fieldForSignUp");
        return textField;
    }

    public TextField usernameForSignUp() {
        usernameFieldForSignUp = new TextField();
        usernameFieldForSignUp.setPromptText("Username");
        usernameFieldForSignUp.setLayoutX(40);
        usernameFieldForSignUp.setLayoutY(240);
        usernameFieldForSignUp.setPrefHeight(40);
        usernameFieldForSignUp.setPrefWidth(400);
        usernameFieldForSignUp.getStyleClass().add("usernameSignUp");
        return usernameFieldForSignUp;
    }


    public PasswordField passwordFieldSignUp() {
        passwordFieldForSignUp = new PasswordField();
        passwordFieldForSignUp.setPromptText("Password");
        passwordFieldForSignUp.setLayoutX(40);
        passwordFieldForSignUp.setLayoutY(290);
        passwordFieldForSignUp.setPrefHeight(40);
        passwordFieldForSignUp.setPrefWidth(400);
        passwordFieldForSignUp.getStyleClass().add("password-fieldSignUp");
        return passwordFieldForSignUp;
    }

    public TextField emailFieldSignUp() {
        email = new TextField();
        email.setPromptText("Email");
        email.setLayoutX(40);
        email.setLayoutY(340);
        email.setPrefHeight(40);
        email.setPrefWidth(400);
        email.getStyleClass().add("emailField");
        return email;
    }

    public NumberField phoneNumberFiledSignUp() {
        phoneNumber = new NumberField();
        phoneNumber.setPromptText("Phone number");
        phoneNumber.setLayoutX(40);
        phoneNumber.setLayoutY(390);
        phoneNumber.setPrefHeight(40);
        phoneNumber.setPrefWidth(400);
        phoneNumber.getStyleClass().add("numberFieldForSignUp");
        return phoneNumber;

    }

    public void cartMenu(MouseEvent mouseEvent) {

    }
}
