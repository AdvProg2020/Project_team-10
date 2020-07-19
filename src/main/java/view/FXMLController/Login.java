package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AccountManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import view.CommandProcessor;
import view.NumberField;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.loginURL;

public class Login {
    private AnchorPane mainPane;
    private AnchorPane loginPane;
    private TextField usernameField;
    private PasswordField passwordFieldForSignIn;
    private Label error;
    private Stage popupWindow;
    private static File selectedFile;
    private boolean isSeller;
    private TextField firstNameText;
    private TextField lastNameText;
    private TextField usernameFieldForSignUp;
    private PasswordField passwordFieldForSignUp;
    private TextField emailText;
    private NumberField phoneNumberText;
    private TextField companyText;
    private Button signUp;
    private FlowPane popupUser;
    private Button user;
    private Button btnLogin;
    private Button btnCartMenu;
    public static Node currentPane;
    private AnchorPane mainMenu;
    private MainMenu main;

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Account onlineAccount;

    public Login(AnchorPane mainPane, Button btnLogin, Button btnCartMenu, AnchorPane mainMenu, MainMenu main,
                 Socket socket, Account onlineAccount) throws IOException {
        this.mainPane = mainPane;
        this.btnLogin = btnLogin;
        this.btnCartMenu = btnCartMenu;
        this.mainMenu = mainMenu;
        this.main = main;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.onlineAccount = onlineAccount;
    }

    public void popupLogin(MouseEvent mouseEvent) throws IOException {
        loginPane = new AnchorPane();
        usernameField = new TextField();
        passwordFieldForSignIn = new PasswordField();
        error = new Label();
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        URL url = Paths.get(loginURL).toUri().toURL();
        AnchorPane layout = FXMLLoader.load(url);
        Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

        Label onlineShop = new Label("Sign in to\n" + "OnlineShop");
        onlineShop.getStyleClass().add("labelForLoginTitle");
        onlineShop.setLayoutX(100);
        onlineShop.setLayoutY(100);

        layout.setStyle("-fx-background-color: none;");
        loginPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        loginPane.setPrefWidth(480);
        loginPane.setPrefHeight(580);

        fade(10, 0.5);

        layout.setLayoutX(500);
        layout.setLayoutY(150);
        layout.getChildren().add(loginPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene1);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);
        loginPane.getChildren().addAll(exitButton(), usernameField(), passwordField(), onlineShop, loginButton(),
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
        button.setOnMouseClicked(event -> {
            try {
                processLogin();
            } catch (IOException e) {
                e.printStackTrace();
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

    private void signUp() {
        error.setText("");
        loginPane.getChildren().clear();
        imageViewForSignUp();

        companyText = new TextField();
        companyText.setPromptText("Company");
        companyText.setLayoutX(110);
        companyText.setLayoutY(445);
        companyText.setPrefWidth(200);
        companyText.setPrefHeight(50);
        companyText.setVisible(false);
        companyText.getStyleClass().add("text-fieldForSignUp");
        loginPane.getChildren().add(companyText);

        Button sellerType = typeOfSignUp("Seller", 445);
        sellerType.getStyleClass().add("typeField");
        sellerType.setOnMouseClicked(event -> {
            signUp.setDisable(false);
            isSeller = true;
            companyText.setVisible(true);
        });

        Button buyerType = typeOfSignUp("Buyer", 470);

        buyerType.getStyleClass().add("typeField");
        buyerType.setOnMouseClicked(event -> {
            signUp.setDisable(false);
            isSeller = false;
            companyText.setVisible(false);
        });
        signUp = new Button("Sign Up");
        signUp.setLayoutY(445);
        signUp.setLayoutX(320);
        signUp.setPrefHeight(40);
        signUp.setPrefWidth(120);
        signUp.setDisable(true);
        signUp.getStyleClass().add("signUp");
        firstNameText = textFieldForSignUp("First name", 40, 140);
        lastNameText = textFieldForSignUp("Last name", 40, 190);

        loginPane.getChildren().addAll(exitButton(), firstNameText,
                lastNameText, usernameForSignUp(),
                passwordFieldSignUp(), emailFieldSignUp(), phoneNumberFiledSignUp(),
                sellerType, buyerType, signUp, error);

        signUp.setOnMouseClicked(event -> {
            try {
                processRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Button typeOfSignUp(String text, int y) {
        Button type = new Button(text);
        type.setLayoutX(40);
        type.setLayoutY(y);
        type.setPrefWidth(60);
        type.setPrefHeight(20);
        return type;
    }

    private void imageViewForSignUp() {
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

        loginPane.getChildren().addAll(titleOFSignUp, button, rectangle);
        button.setOnAction(e -> {
            selectedFile = fileChooser.showOpenDialog(stage);
            ImageView imageView = new ImageView(new Image("file:" + selectedFile));
            imageView.setFitHeight(120);
            imageView.setFitWidth(100);
            imageView.setLayoutX(340);
            imageView.setLayoutY(80);
            imageView.getStyleClass().add("imageView");
            imageView.autosize();
            loginPane.getChildren().add(imageView);
        });


    }

    private TextField textFieldForSignUp(String prompt, int x, int y) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefHeight(40);
        textField.setPrefWidth(290);
        textField.getStyleClass().add("text-fieldForSignUp");
        return textField;
    }

    private TextField usernameForSignUp() {
        usernameFieldForSignUp = new TextField();
        usernameFieldForSignUp.setPromptText("Username");
        usernameFieldForSignUp.setLayoutX(40);
        usernameFieldForSignUp.setLayoutY(240);
        usernameFieldForSignUp.setPrefHeight(40);
        usernameFieldForSignUp.setPrefWidth(400);
        usernameFieldForSignUp.getStyleClass().add("usernameSignUp");
        return usernameFieldForSignUp;
    }

    private PasswordField passwordFieldSignUp() {
        passwordFieldForSignUp = new PasswordField();
        passwordFieldForSignUp.setPromptText("Password");
        passwordFieldForSignUp.setLayoutX(40);
        passwordFieldForSignUp.setLayoutY(290);
        passwordFieldForSignUp.setPrefHeight(40);
        passwordFieldForSignUp.setPrefWidth(400);
        passwordFieldForSignUp.getStyleClass().add("password-fieldSignUp");
        return passwordFieldForSignUp;
    }

    private TextField emailFieldSignUp() {
        emailText = new TextField();
        emailText.setPromptText("Email");
        emailText.setLayoutX(40);
        emailText.setLayoutY(340);
        emailText.setPrefHeight(40);
        emailText.setPrefWidth(400);
        emailText.getStyleClass().add("emailField");
        return emailText;
    }

    private NumberField phoneNumberFiledSignUp() {
        phoneNumberText = new NumberField();
        phoneNumberText.setPromptText("Phone number");
        phoneNumberText.setLayoutX(40);
        phoneNumberText.setLayoutY(390);
        phoneNumberText.setPrefHeight(40);
        phoneNumberText.setPrefWidth(400);
        phoneNumberText.getStyleClass().add("numberFieldForSignUp");
        return phoneNumberText;

    }

    private void processLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordFieldForSignIn.getText();
        Buyer temp = ((Buyer) onlineAccount);
        dataOutputStream.writeUTF("can login " + username + " " + password);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        if (response.startsWith("true")) {
            login(response);
            main.onlineAccount = this.onlineAccount;
            popupWindow.close();
            handleUserBtn();
            if (!(onlineAccount instanceof Buyer)) {
                btnCartMenu.setVisible(false);
            } else {
                btnCartMenu.setVisible(true);
                ((Buyer) onlineAccount).getCart().addAll(temp.getCart());
                for (Good good : temp.getCart()) {
                    int number = good.getGoodsInBuyerCart().get("temp");
                    good.getGoodsInBuyerCart().put(onlineAccount.getUsername(), number);
                    good.getGoodsInBuyerCart().remove("temp");
                }
            }
            fade(0.5, 10);
        } else {
            error.setText("username/password is incorrect");
            error.setLayoutX(120);
            error.setLayoutY(470);
            error.setTextFill(Color.RED);
        }
    }

    private void login(String response) {
        String type = response.split("\\s")[2];
        Type accountType;
        if (type.equals("buyer")) {
            accountType = new TypeToken<Buyer>() {
            }.getType();
        } else if (type.equals("seller")) {
            accountType = new TypeToken<Seller>() {
            }.getType();
        } else {
            accountType = new TypeToken<Admin>() {
            }.getType();
        }
        onlineAccount = new Gson().fromJson(response.split("\\s")[1], accountType);
    }

    private void processRegister() throws IOException {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String username = usernameFieldForSignUp.getText();
        String password = passwordFieldForSignUp.getText();
        String email = emailText.getText();
        String phoneNumber = phoneNumberText.getText();
        String type;
        String company = companyText.getText();
        if (isSeller) {
            type = "seller";
        } else {
            type = "buyer";
        }
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            if (username.length() > 0) {
                if (CommandProcessor.checkPasswordInvalidation(password)) {
                    if (CommandProcessor.checkEmailInvalidation(email)) {
                        dataOutputStream.writeUTF("can register " + username);
                        dataOutputStream.flush();
                        if (dataInputStream.readUTF().equals("true")) {
                            dataOutputStream.writeUTF("register " + username + " " + password + " " + type + " " + firstName
                                    + " " + lastName + " " + email + " " + phoneNumber + " " + company + " " + imagePath);
                            dataOutputStream.flush();
//                            AccountManager.register(username, password, type, firstName, lastName, email, phoneNumber
//                                    , company, imagePath);
                            popupWindow.close();
                            fade(0.5, 10);
                        } else {
                            printErrorForRegister("a user exists with this username");
                        }
                    } else {
                        printErrorForRegister("invalid email");
                    }
                } else {
                    printErrorForRegister("invalid password");
                }

            } else {
                printErrorForRegister("username cannot be empty");
            }
        } else {
            printErrorForRegister("you should select a photo");
        }
    }

    private void printErrorForRegister(String text) {
        error.setText(text);
        error.setLayoutX(100);
        error.setLayoutY(500);
        error.setTextFill(Color.RED);
    }

    public void handleUserBtn() {

        popupUser = new FlowPane();
        popupUser.setAlignment(Pos.CENTER_LEFT);
        popupUser.setHgap(5);
        popupUser.getStyleClass().add("popupUser");
        popupUser.setLayoutX(1280);
        popupUser.setLayoutY(145);
        popupUser.setPrefSize(170, 145);
        btnLogin.setVisible(false);

        user = new Button();
        user.setLayoutX(1380);
        user.setLayoutY(110);
        user.getStyleClass().add("userButton");

        HBox hBox = new HBox();
        Circle circle = new Circle(20);
        ImagePattern pattern = new ImagePattern(new Image("file:" + onlineAccount.getImagePath()));
        circle.setFill(pattern);
        circle.setStrokeWidth(1.5);
        circle.setStroke(Color.rgb(16, 137, 255));

        hBox.setPadding(new Insets(0, 0, 5, 9));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setPrefWidth(170);


        Rectangle rectangle = new Rectangle(120, 1);
        rectangle.getStyleClass().add("shape");
        Rectangle rectangle2 = new Rectangle(120, 1);
        rectangle2.getStyleClass().add("shape");

        popupUser.setAlignment(Pos.CENTER);

        Label username = new Label("Hi " + onlineAccount.getUsername());
        username.getStyleClass().add("labelUsername");

        hBox.getChildren().addAll(circle, username);

        Button accountPage = new Button("Account");
        accountPage.setPrefWidth(170);
        accountPage.setPrefHeight(40);
        accountPage.getStyleClass().add("accountPageBtn");
        accountPage.setAlignment(Pos.BASELINE_LEFT);
        accountPage.setOnMouseClicked(e -> {
            try {
                createAccountPanel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button logout = new Button("Log out");
        logout.getStyleClass().add("logoutBtn");
        logout.setPrefHeight(40);
        logout.setPrefWidth(170);
        logout.setAlignment(Pos.BASELINE_LEFT);
        logout.setOnMouseClicked(event -> logout());

        popupUser.getChildren().addAll(hBox, rectangle2, accountPage, rectangle, logout);

        user.setOnMouseClicked(event -> {
            if (mainPane.getChildren().contains(popupUser)) {
                mainPane.getChildren().remove(popupUser);
            } else {
                mainPane.getChildren().add(popupUser);
            }

        });
        mainPane.getChildren().add(user);

    }

    private void createAccountPanel() throws IOException {
        mainPane.getChildren().remove(popupUser);
        mainPane.getChildren().remove(currentPane);
        if (onlineAccount instanceof Admin) {
            new AdminPanel(mainPane, main, mainMenu, user, btnLogin).changePane();
        } else if (onlineAccount instanceof Buyer) {
            new BuyerPanel(mainPane, main, mainMenu, user, btnLogin).changePane();
        } else if (onlineAccount instanceof Supporter) {
            new SupporterPanel(mainPane, main, mainMenu, user, btnLogin).changePane();
        } else {
            new SellerPanel(mainPane, main, mainMenu, user, btnLogin, socket, onlineAccount).changePane();
        }
    }

    private void logout() {
        onlineAccount = new Buyer("temp");
        main.onlineAccount = this.onlineAccount;
        user.setVisible(false);
        popupUser.getChildren().clear();
        popupUser.setVisible(false);
        btnLogin.setVisible(true);
        btnCartMenu.setVisible(true);
        backToMainMenu();
    }

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    public void backToMainMenu() {
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }


}
