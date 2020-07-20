package view.FXMLController;
import com.jfoenix.controls.*;
import controller.AccountManager;
import controller.AdminManager;
import controller.SellerManager;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.adminPopupURL;
import static view.FXMLController.MainMenu.fadeEffect;

public class SupporterPanel {
    public AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane adminPane;
    private Button selectedButton = new Button("Profile");
    private ImageView imageViewSelectedButton;
    private AnchorPane loginPane;
    private TextField firstNameText;
    private TextField lastNameText;
    private TextField usernameFieldForSignUp;
    private PasswordField passwordFieldForSignUp;
    private TextField emailText;
    private NumberField phoneNumberText;
    private Label error;
    private Stage popupWindow;
    private static File selectedFile;
    private ScrollPane adminScrollPane = new ScrollPane();
    private MainMenu main;
    private AnchorPane mainMenu;
    private Button user;
    private Button btnLogin;
    //discount
    private List<String> selectedBuyers;
    private JFXDatePicker startDate;
    private JFXDatePicker endDate;
    private JFXTimePicker startTime;
    private JFXTimePicker endTime;
    private NumberField percent;
    private NumberField maxPrice;
    private NumberField number;
    //category
    private ArrayList<TextField> attributesTextField;
    private TextField categoryName;

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phoneNumber;
    private PasswordField password;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Account onlineAccount;


    private VBox boxForEdit(String name) {
        VBox boxFirstName = new VBox(2);
        boxFirstName.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label();
        TextField field = new TextField();
        field.setPrefSize(350, 40);

        if (name.equals("First name: ")) {
            label.setText(" First name: ");
            field.setText(AccountManager.getOnlineAccount().getFirstName());
            firstName = field;
        } else if (name.equals("Last name: ")) {
            label.setText(" Last name: ");
            field.setText(AccountManager.getOnlineAccount().getLastName());
            lastName = field;
        } else if (name.equals("Email: ")) {
            label.setText(" Email: ");
            field.setText(AccountManager.getOnlineAccount().getEmail());
            email = field;
        } else if (name.equals("Phone: ")) {
            NumberField numberField = new NumberField();
            numberField.setPrefSize(350, 40);
            numberField.setText(AccountManager.getOnlineAccount().getPhoneNumber());
            label.setText(" Phone number: ");
            field = numberField;
            phoneNumber = numberField;
        } else if (name.equals("password")) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Current password");
            passwordField.setPrefSize(350, 40);
            field = passwordField;
        } else if (name.equals("newPass")) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("New password");
            passwordField.setPrefSize(350, 40);
            field = passwordField;
            password = passwordField;
        }
        label.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 15;-fx-text-fill:rgb(16,137,255)");
        field.getStyleClass().add("text-fieldForEdit");
        boxFirstName.getChildren().addAll(label, field);

        return boxFirstName;

    }

    private void processEdit() {
//        AccountManager.editPersonalInfo(password.getText(), firstName.getText(), lastName.getText(), phoneNumber.getText(), email.getText());
        handelButtonOnMouseClick();
    }

    private void editProfilePain(FlowPane flowPane) {
        flowPane.getChildren().clear();
        flowPane.setHgap(80);
        flowPane.setVgap(12);

        VBox backBox = new VBox();
        backBox.setPrefSize(800 , 40);
        ImageView back = new ImageView();
        back.getStyleClass().add("backStyle");
        back.setFitWidth(30);
        back.setFitHeight(30);
        backBox.getChildren().add(back);
        back.setOnMouseClicked(event -> handelButtonOnMouseClick());

        VBox currentPass = boxForEdit("password");
        currentPass.setDisable(true);
        VBox newPass = boxForEdit("newPass");

        VBox submitBox = new VBox();
        submitBox.setPadding(new Insets(20, 0 ,0,0));
        Button submit = new Button("Submit");
        submit.getStyleClass().add("buttonSubmit");
        submit.setPrefSize(780 , 40);
        submit.setOnMouseClicked(event -> {
            processEdit();
            handelButtonOnMouseClick();
        });
        submitBox.getChildren().add(submit);

        flowPane.getChildren().addAll(backBox, boxForEdit("First name: "), boxForEdit("Last name: "),
                boxForEdit("Email: "), boxForEdit("Phone: "), newPass ,submitBox);


    }

    public SupporterPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu, Button user, Button btnLogin, Socket socket, Account onlineAccount) throws IOException {
        this.main = main;
        this.mainMenu = mainMenu;
        this.mainPane = mainPane;
        this.btnLogin = btnLogin;
        this.user = user;
        adminPane = new AnchorPane();
        optionsPane = new AnchorPane();
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.onlineAccount = onlineAccount;
        handelButtonOnMouseClick();
    }

    public void changePane() {
        adminPane.setLayoutY(165);
        optionsPane.setLayoutY(35);
        optionsPane.setLayoutX(30);
        optionsPane.setPrefSize(250, 520);

        HBox hBox = new HBox();
        Circle circle = new Circle(40);
        ImagePattern pattern = new ImagePattern(new Image("file:" + AccountManager.getOnlineAccount().getImagePath()));
        circle.setFill(pattern);
        circle.setStrokeWidth(4);
        circle.setStroke(Color.rgb(16, 137, 255));

        hBox.setPadding(new Insets(0, 0, 5, 9));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setPrefWidth(170);

        ImageView credit = new ImageView(new Image("file:src/main/java/view/image/AdminPanel/credit.png"));
        credit.setFitHeight(20);
        credit.setFitWidth(25);
        Label creditLabel = new Label("$" + AccountManager.getOnlineAccount().getCredit());
        creditLabel.getStyleClass().add("labelUsername");
        creditLabel.setStyle("-fx-text-fill: #00ff30");

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(credit, creditLabel);
        hBox.setSpacing(10);


        VBox vBoxP = new VBox();
        Label username = new Label("Hi " + AccountManager.getOnlineAccount().getUsername());
        vBoxP.setAlignment(Pos.CENTER_LEFT);
        vBoxP.setSpacing(8);
        vBoxP.getChildren().addAll(username, hBox1);
        username.getStyleClass().add("labelUsername");

        hBox.getChildren().addAll(circle, vBoxP);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_LEFT);

        Rectangle rectangleTop = new Rectangle(240, 2);
        rectangleTop.setFill(Color.rgb(225, 225, 225));
        Rectangle rectangleDown = new Rectangle(240, 2);
        rectangleDown.setFill(Color.rgb(225, 225, 225));
        vBox.getChildren().addAll(hBox, rectangleTop, createButton("Profile", "src/main/java/view/image/AdminPanel/userAdmin"),
                createButton("Manage users", "src/main/java/view/image/AdminPanel/users"),
                createButton("Manage products", "src/main/java/view/image/AdminPanel/product"),
                createButton("Manage requests", "src/main/java/view/image/AdminPanel/request"),
                createButton("Discounts", "src/main/java/view/image/AdminPanel/discount"),
                createButton("Category", "src/main/java/view/image/AdminPanel/category"),
                rectangleDown,
                createButton("Log out", "src/main/java/view/image/AdminPanel/logout"));
        vBox.setStyle("-fx-background-color: none;");
        vBox.setPadding(new Insets(10, 10, 10, 8));


        optionsPane.setStyle("-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: #e1e1e1;"
                + "-fx-border-radius: 10;" + "-fx-background-radius: 10");
        optionsPane.getChildren().addAll(vBox);
        adminPane.getChildren().add(optionsPane);
        optionsPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        Login.currentPane = adminPane;
        mainPane.getChildren().add(adminPane);
    }

    public void popup(String input) throws IOException {
        loginPane = new AnchorPane();
        error = new Label();
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        URL url = Paths.get(adminPopupURL).toUri().toURL();
        AnchorPane layout = FXMLLoader.load(url);
        Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

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

        switch (input) {
            case "signUp":
                signUpAdmin();
                break;
            case "discount":
                addDiscount();
                break;
            case "category":
                addCategory();
                break;
        }
        popupWindow.showAndWait();

    }

    private void addCategory() {

        attributesTextField = new ArrayList<>();
        error.setText("");
        loginPane.getChildren().clear();

        Label titleOFSignUp = new Label("+ ADD CATEGORY");
        titleOFSignUp.setLayoutY(80);
        titleOFSignUp.setLayoutX(40);
        titleOFSignUp.getStyleClass().add("labelForLoginTitle");

        JFXButton submit = new JFXButton("Submit");
        submit.setLayoutY(445);
        submit.setLayoutX(40);
        submit.setPrefHeight(40);
        submit.setPrefWidth(400);
        submit.getStyleClass().add("signUp");
        submit.setOnMouseClicked(event -> processAddCategory());

        ScrollPane containAttribute = new ScrollPane();
        containAttribute.setLayoutX(40);
        containAttribute.setLayoutY(135);
        containAttribute.setPrefSize(400 , 300);
        containAttribute.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        containAttribute.getStyleClass().add("scroll-barInDiscount");
        containAttribute.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox categoryPane = new VBox(10);
        categoryPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        categoryPane.setPrefSize(380, 290);
        categoryPane.setAlignment(Pos.TOP_CENTER);


        categoryName = new TextField();
        categoryName.setPromptText("Category name");
        HBox hBox = new HBox(categoryName);
        hBox.setAlignment(Pos.CENTER);
        categoryName.setPrefSize(300, 40);
        categoryName.getStyleClass().add("text-fieldForCategory");

        HBox att = new HBox(10);
        att.setAlignment(Pos.CENTER);
        ImageView plus = new ImageView();
        plus.getStyleClass().add("imageViewPlus");
        plus.setFitHeight(30);
        plus.setFitWidth(30);

        plus.setOnMouseClicked(e -> {
            ImageView minus = new ImageView();
            minus.getStyleClass().add("imageViewMines");
            minus.setFitHeight(30);
            minus.setFitWidth(30);

            TextField attributeTextField = textFieldForAddCategory();
            attributesTextField.add(attributeTextField);

            HBox attributePack = new HBox(attributeTextField, minus);
            attributePack.setSpacing(10);
            attributePack.setAlignment(Pos.CENTER);
            minus.setOnMouseClicked(event -> {
                attributesTextField.remove(attributeTextField);
                categoryPane.getChildren().remove(attributePack);
            });

            categoryPane.getChildren().add(attributePack);
        });
        TextField firstAttribute = textFieldForAddCategory();
        attributesTextField.add(firstAttribute);
        att.getChildren().addAll(firstAttribute, plus);

        categoryPane.getChildren().addAll(categoryName, att);
        containAttribute.setContent(categoryPane);


        loginPane.getChildren().addAll(exitButton(), titleOFSignUp, submit,containAttribute , error);
    }

    private TextField textFieldForAddCategory() {
        TextField attribute = new TextField();
        attribute.setPromptText("Attribute");
        attribute.setPrefSize(350, 30);
        attribute.getStyleClass().add("text-fieldForCategory");
        return attribute;
    }

    private void processAddCategory() {
        ArrayList<String> attributes = new ArrayList<>();
        for (TextField textField : attributesTextField) {
            attributes.add(textField.getText());
        }
        AdminManager.addCategory(categoryName.getText(), attributes);
        popupWindow.close();
        fade(0.5, 10);
        adminScrollPane.setContent(null);
        adminScrollPane.setContent(handelCategory());

    }

    public Button createButton(String text, String style) {
        ImageView imageView = new ImageView(new Image("file:" + style + ".png"));
        ImageView imageViewHover = new ImageView(new Image("file:" + style + "Hover.png"));
        imageViewHover.setFitWidth(30);
        imageViewHover.setFitHeight(30);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Button button = new Button(text);
        button.setPrefSize(240, 25);
//        button.getStyleClass().add("button");
        button.setGraphic(imageView);
        button.setAlignment(Pos.CENTER_LEFT);
        if (text.equals("Profile")) {
            button.setGraphic(imageViewHover);
            button.getStyleClass().add("selectedButton");
            selectedButton = button;
            imageViewSelectedButton = imageView;
        }

        button.setOnMouseClicked(e -> {
            selectedButton.setGraphic(imageViewSelectedButton);
            imageViewSelectedButton = imageView;
//            selectedButton.setStyle("-fx-text-fill: black");
//            selectedButton.getStyleClass().add("button");
            selectedButton.getStyleClass().remove("selectedButton");
            selectedButton = button;
            selectedButton.getStyleClass().add("selectedButton");
            selectedButton.setGraphic(imageViewHover);
            handelButtonOnMouseClick();
        });
        return button;
    }

    private void handelButtonOnMouseClick() {

        adminPane.getChildren().remove(adminScrollPane);
        adminScrollPane.setPrefSize(1150, 620);
        adminScrollPane.getStyleClass().add("scroll-bar");
        adminScrollPane.setLayoutX(330);
        adminScrollPane.setLayoutY(35);
        adminPane.getChildren().remove(adminScrollPane);
        adminScrollPane.setPrefSize(1150, 620);
        adminScrollPane.getStyleClass().add("scroll-bar");
        adminScrollPane.setLayoutX(330);
        adminScrollPane.setLayoutY(35);
//        adminPaneScroll.;
        Account account = AccountManager.getOnlineAccount();

        switch (selectedButton.getText()) {
            case "Profile":
                FlowPane flowPane = new FlowPane();
                flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
                flowPane.setPrefWidth(1200);
                flowPane.setPrefHeight(420);

                VBox hyperLink = new VBox();
                hyperLink.setPadding(new Insets(20, 0, 0, 0));
                Hyperlink editProfile = new Hyperlink("Edit profile");
                editProfile.setOnMouseClicked(event -> editProfilePain(flowPane));
                editProfile.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 14;");
                ImageView edit = new ImageView(new Image("file:src/main/java/view/image/edit.png"));
                edit.setFitHeight(18);
                edit.setFitWidth(18);
                hyperLink.setPrefWidth(960);
                hyperLink.setAlignment(Pos.CENTER);
                editProfile.setGraphic(edit);
                hyperLink.getChildren().add(editProfile);

                flowPane.setPadding(new Insets(50, 0, 10, 70));
                flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
                flowPane.getChildren().addAll(createItemOfProfile("Username:", account.getUsername()),
                        createItemOfProfile("Full name:", account.getFirstName() + " " + account.getLastName()),
                        createItemOfProfile("Phone number:", account.getPhoneNumber()),
                        createItemOfProfile("Email:", account.getEmail()) ,hyperLink);
                adminScrollPane.setContent(flowPane);
                adminPane.getChildren().add(adminScrollPane);
                adminScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                break;
            case "Manage users":
                adminScrollPane.setContent(handelManageUsers());
                adminPane.getChildren().add(adminScrollPane);
                break;
            case "Manage products":
                adminScrollPane.setContent(handelManageProduct());
                adminPane.getChildren().add(adminScrollPane);
                break;
            case "Discounts":
                adminScrollPane.setContent(handelDiscounts());
                adminPane.getChildren().add(adminScrollPane);
                break;
            case "Category":
                adminScrollPane.setContent(handelCategory());
                adminPane.getChildren().add(adminScrollPane);
                break;
            case "Log out":
                onlineAccount = (new Buyer("temp"));
                main.onlineAccount = onlineAccount;
                user.setVisible(false);
                btnLogin.setVisible(true);
                backToMainMenu();
                break;
        }
        adminScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private FlowPane handelCategory() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        HBox hBoxTitle = new HBox(0);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(60);

        Label categoryName = new Label("Name");
        categoryName.setPrefWidth(255);
        categoryName.getStyleClass().add("labelForDiscount");

        Label attributesTitle = new Label("  " + "Attributes");
        attributesTitle.setGraphic(line());
        attributesTitle.setPrefWidth(700);
        attributesTitle.getStyleClass().add("labelForDiscount");

        hBoxTitle.getChildren().addAll(categoryName, attributesTitle);
        flowPane.getChildren().add(hBoxTitle);


        for (Category category : Shop.getShop().getAllCategories()) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(60);

            Label name = new Label("" + category.getName());
            name.setPrefWidth(255);
            name.getStyleClass().add("labelForDiscount");


            Label attributes = new Label("  " + category.getAttributes());
            attributes.setGraphic(line());
            attributes.setPrefWidth(680);
            attributes.getStyleClass().add("labelForDiscount");

            ImageView edit = new ImageView();
            edit.getStyleClass().add("editImage");
            edit.setFitWidth(25);
            edit.setFitHeight(25);

            ImageView bin = new ImageView();
            bin.getStyleClass().add("binImage");
            bin.setFitWidth(31);
            bin.setFitHeight(25);

            hBox.getChildren().addAll(name, attributes, edit, bin);
            flowPane.getChildren().add(hBox);
            bin.setOnMouseClicked(e -> {
                AdminManager.removeCategory(category);
                flowPane.getChildren().remove(hBox);
            });
        }

        return flowPane;

    }

    private FlowPane handelManageProduct() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        for (Good good : Shop.getShop().getAllGoods()) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(225);
            vBox.setPrefHeight(350);
            vBox.getStyleClass().add("vBoxInMainMenu");
            ImageView productImage = new ImageView(new Image("file:" + good.getImagePath()));
            productImage.setFitHeight(170);
            productImage.setFitWidth(170);
            Label name = new Label(good.getName());
            Label price = new Label("$" + good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            vBox.setOnMouseEntered(event -> fadeEffect(vBox));
//            logoImage.setOnMouseClicked(event -> {
//                GoodsManager.setCurrentGood(good);
//                new GoodMenu(mainPane).changePane();
//            });
            vBox.setAlignment(Pos.CENTER);
            ImageView bin = new ImageView();
            bin.getStyleClass().add("imageViewRecy");
            bin.setFitWidth(31);
            bin.setFitHeight(31);
            HBox hBox = new HBox(bin);
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPrefWidth(230);
            hBox.setPadding(new Insets(0, 20, 0, 0));
            vBox.getChildren().addAll(productImage, name, price, visit, hBox);
            bin.setOnMouseClicked(e -> {
                SellerManager.removeProduct(good.getId());
                flowPane.getChildren().remove(vBox);
            });
            flowPane.getChildren().add(vBox);
        }

        return flowPane;
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

    private void signUpAdmin() {
        error.setText("");
        loginPane.getChildren().clear();
        imageViewForSignUp();

        JFXButton signUp = new JFXButton("Sign Up");
        signUp.setLayoutY(445);
        signUp.setLayoutX(40);
        signUp.setPrefHeight(40);
        signUp.setPrefWidth(400);
        signUp.getStyleClass().add("signUp");
        firstNameText = textFieldForSignUp("First name", 40, 140);
        lastNameText = textFieldForSignUp("Last name", 40, 190);

        loginPane.getChildren().addAll(exitButton(), firstNameText,
                lastNameText, usernameForSignUp(),
                passwordFieldSignUp(), emailFieldSignUp(), phoneNumberFiledSignUp(), signUp, error);

        signUp.setOnMouseClicked(event -> processRegister());
    }

    private void addDiscount() {

        error.setText("");
        loginPane.getChildren().clear();

        Label titleAddDiscount = new Label("+ Add discount");
        titleAddDiscount.setLayoutY(80);
        titleAddDiscount.setLayoutX(40);
        titleAddDiscount.getStyleClass().add("labelForLoginTitle");

        JFXButton submit = new JFXButton("Submit");
        submit.setLayoutY(445);
        submit.setLayoutX(40);
        submit.setPrefSize(400, 40);
        submit.getStyleClass().add("signUp");
        submit.setOnMouseClicked(event -> processAddDiscount());

        startDate = new JFXDatePicker();
        startDate.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        startDate.setDefaultColor(Color.rgb(244, 218, 0));
        startDate.setLayoutY(150);
        startDate.setLayoutX(40);
        startDate.setPrefSize(240, 40);
        startDate.setOnAction(event -> {
            LocalDate date = startDate.getValue();
            System.out.println("Selected date: " + date);
        });

        startTime = new JFXTimePicker();
        startTime.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        startTime.setDefaultColor(Color.rgb(244, 218, 0));
        startTime.setPrefSize(140, 40);
        startTime.setLayoutY(150);
        startTime.setLayoutX(300);
        startTime.setOnAction(event -> {
            LocalTime date = startTime.getValue();
            System.out.println("Selected date: " + date);
        });

        endDate = new JFXDatePicker();
        endDate.setDefaultColor(Color.rgb(244, 218, 0));
        endDate.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        endDate.setPrefSize(240, 40);
        endDate.setLayoutY(220);
        endDate.setLayoutX(40);
        endDate.setOnAction(event -> {
            LocalDate date = endDate.getValue();
            System.out.println("Selected date: " + date);
        });

        endTime = new JFXTimePicker();
        endTime.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        endTime.setDefaultColor(Color.rgb(244, 218, 0));
        endTime.setPrefSize(140, 40);
        endTime.setLayoutY(220);
        endTime.setLayoutX(300);
        endTime.setOnAction(event -> {
            LocalTime date = endTime.getValue();
            System.out.println("Selected date: " + date);
        });

        percent = new NumberField();
        percent.setPromptText("Percent");
        percent.setLayoutY(290);
        percent.setLayoutX(40);
        percent.setPrefSize(100, 40);
        percent.getStyleClass().add("text-fieldForSignUp");

        maxPrice = new NumberField();
        maxPrice.setPromptText("Maximum price");
        maxPrice.setLayoutY(290);
        maxPrice.setLayoutX(150);
        maxPrice.setPrefSize(180, 40);
        maxPrice.getStyleClass().add("text-fieldForSignUp");

        number = new NumberField();
        number.setPromptText("Number");
        number.setPrefSize(100, 40);
        number.setLayoutX(340);
        number.setLayoutY(290);
        number.getStyleClass().add("text-fieldForSignUp");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutY(350);
        scrollPane.setLayoutX(40);
        scrollPane.setPrefSize(400, 80);

        FlowPane flowPane = new FlowPane();
        scrollPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setLayoutX(40);
        flowPane.setLayoutY(350);
        flowPane.setPrefSize(400, 80);
        flowPane.setStyle("-fx-background-color: none");
        scrollPane.setContent(flowPane);
        scrollPane.getStyleClass().add("scroll-barInDiscount");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        selectedBuyers = new ArrayList<>();
        for (Buyer buyer : Shop.getShop().getAllBuyers()) {
            HBox hBox = new HBox();
            hBox.setPrefSize(100, 40);
            hBox.setPadding(new Insets(8, 5, 8, 5));

            JFXCheckBox username = new JFXCheckBox(buyer.getUsername());
            username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';");
            username.setOnAction(event -> {
                if (username.isSelected()) {
                    selectedBuyers.add(buyer.getUsername());
                } else {
                    selectedBuyers.remove(buyer.getUsername());
                }
            });
            hBox.getChildren().add(username);
            hBox.setStyle("-fx-background-color: none");

            flowPane.getChildren().add(hBox);
        }


        loginPane.getChildren().addAll(exitButton(), titleAddDiscount, startDate,
                startTime, endDate, endTime, percent, maxPrice, number, submit, scrollPane, error);
    }

    private void processAddDiscount() {
        String startYear = "" + startDate.getValue().getYear();
        String endYear = "" + endDate.getValue().getYear();
        String startMonth = "" + startDate.getValue().getMonthValue();
        if (startMonth.length() == 1) {
            startMonth = "0" + startMonth;
        }
        String endMonth = "" + endDate.getValue().getMonthValue();
        if (endMonth.length() == 1) {
            endMonth = "0" + endMonth;
        }
        String startDay = "" + startDate.getValue().getDayOfMonth();
        if (startDay.length() == 1) {
            startDay = "0" + startDay;
        }
        String endDay = "" + endDate.getValue().getDayOfMonth();
        if (endDay.length() == 1) {
            endDay = "0" + endDay;
        }
        String startDate = (startMonth + "/" + startDay + "/" + startYear + " " + this.startTime.getValue());
        String endDate = (endMonth + "/" + endDay + "/" + endYear + " " + this.endTime.getValue());
        int percent = Integer.parseInt(this.percent.getText());
        long maxAmount = Long.parseLong(this.maxPrice.getText());
        int number = Integer.parseInt(this.number.getText());
        AdminManager.createDiscount(getDateByString(startDate), getDateByString(endDate), percent, maxAmount, number, selectedBuyers);
        popupWindow.close();
        fade(0.5, 10);
        adminScrollPane.setContent(null);
        adminScrollPane.setContent(handelDiscounts());

    }

    public static Date getDateByString(String dateInput) {
        Calendar calendar = Calendar.getInstance();
        String regex = "(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d) (\\d\\d):(\\d\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateInput);
        int[] dateSplit = new int[5];
        if (getMatcher(dateInput, regex).matches()) {
            while (matcher.find()) {
                for (int i = 0; i < 5; i++) {
                    dateSplit[i] = Integer.parseInt(matcher.group(i + 1));
                }
            }
            calendar.set(Calendar.MONTH, dateSplit[0] - 1);
            calendar.set(Calendar.DAY_OF_MONTH, dateSplit[1] - 1);
            calendar.set(Calendar.YEAR, dateSplit[2]);
            calendar.set(Calendar.HOUR, dateSplit[3]);
            calendar.set(Calendar.MINUTE, dateSplit[4]);
            return calendar.getTime();
        }
        return null;
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }

    private void processRegister() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String username = usernameFieldForSignUp.getText();
        String password = passwordFieldForSignUp.getText();
        String email = emailText.getText();
        String phoneNumber = phoneNumberText.getText();
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            if (username.length() > 0) {
                if (CommandProcessor.checkPasswordInvalidation(password)) {
                    if (CommandProcessor.checkEmailInvalidation(email)) {
//                        if (AccountManager.canRegister(username)) {
//                            AccountManager.register(username, password, "admin", firstName, lastName, email, phoneNumber
//                                    , " ", imagePath);
//                            adminScrollPane.setContent(handelManageUsers());
//                            popupWindow.close();
//                            fade(0.5, 10);
//                        } else {
//                            printErrorForRegister("a user exists with this username");
//                        }
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

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    private FlowPane handelManageUsers() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        HBox hBoxTitle = new HBox(100);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(60);
        Label labelUser = new Label("Username");
        labelUser.setPrefWidth(150);
        labelUser.getStyleClass().add("labelUsernameInProfile");
        Label labelEmail = new Label("  " + "Email");
        Rectangle rectangleTitle = new Rectangle(2, 60);
        rectangleTitle.setStyle("-fx-fill: #d5d5d5");
        labelEmail.setGraphic(rectangleTitle);
        labelEmail.setPrefWidth(596);
        labelEmail.getStyleClass().add("labelUsernameInProfile");

        hBoxTitle.getChildren().addAll(labelUser, rectangleTitle, labelEmail);
        flowPane.getChildren().add(hBoxTitle);


        for (Account account : Shop.getShop().getAllAccounts()) {
            HBox hBox = new HBox(100);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(60);
            Label usernameLabel = new Label(account.getUsername());
            usernameLabel.setPrefWidth(150);
            usernameLabel.getStyleClass().add("labelUsernameInProfile");
            Label emailLabel = new Label("  " + account.getEmail());
            Rectangle rectangle = new Rectangle(2, 60);
            rectangle.setStyle("-fx-fill: #d5d5d5");
            emailLabel.setGraphic(rectangle);
            emailLabel.setPrefWidth(600);
            emailLabel.getStyleClass().add("labelUsernameInProfile");
            ImageView deleteAccountImage = new ImageView();
            deleteAccountImage.getStyleClass().add("binImage");
            deleteAccountImage.setFitWidth(31);
            deleteAccountImage.setFitHeight(25);

            hBox.getChildren().addAll(usernameLabel, emailLabel, deleteAccountImage);
            flowPane.getChildren().add(hBox);
            deleteAccountImage.setOnMouseClicked(e -> {
                AdminManager.deleteAccount(account);
                flowPane.getChildren().remove(hBox);
            });
        }

        return flowPane;
    }

    private Rectangle line() {
        Rectangle line = new Rectangle(2, 60);
        line.setStyle("-fx-fill: #d5d5d5");
        return line;
    }

    private FlowPane handelDiscounts() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        HBox hBoxTitle = new HBox(0);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(60);

        Label discountCode = new Label("Code");
        discountCode.setPrefWidth(55);
        discountCode.getStyleClass().add("labelForDiscount");

        Label startDate = new Label("  " + "Start date");
        startDate.setGraphic(line());
        startDate.setPrefWidth(255);
        startDate.getStyleClass().add("labelForDiscount");

        Label endDate = new Label("  " + "End date");
        endDate.setGraphic(line());
        endDate.setPrefWidth(255);
        endDate.getStyleClass().add("labelForDiscount");

        Label percent = new Label("  " + "Percent");
        percent.setGraphic(line());
        percent.setPrefWidth(100);
        percent.getStyleClass().add("labelForDiscount");

        Label people = new Label("  " + "People");
        people.setGraphic(line());
        people.setPrefWidth(274);
        people.getStyleClass().add("labelForDiscount");

        hBoxTitle.getChildren().addAll(discountCode, startDate, endDate, percent, people);
        flowPane.getChildren().add(hBoxTitle);

        for (Discount discount : Shop.getShop().getAllDiscounts()) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(60);

            Label code = new Label("" + discount.getCode());
            code.setPrefWidth(55);
            code.getStyleClass().add("labelForDiscount");


            Label start = new Label("  " + discount.getStartDate());
            start.setGraphic(line());
            start.setPrefWidth(255);
            start.getStyleClass().add("labelForDiscount");

            Label end = new Label("  " + discount.getEndDate());
            end.setGraphic(line());
            end.setPrefWidth(255);
            end.getStyleClass().add("labelForDiscount");

            Label percentNum = new Label("  " + discount.getPercent());
            percentNum.setGraphic(line());
            percentNum.setPrefWidth(100);
            percentNum.getStyleClass().add("labelForDiscount");


            Label peopleName = new Label("  " + discount.getUserNames());
            peopleName.setGraphic(line());
            peopleName.setPrefWidth(254);
            peopleName.getStyleClass().add("labelForDiscount");

            ImageView edit = new ImageView();
            edit.getStyleClass().add("editImage");
            edit.setFitWidth(25);
            edit.setFitHeight(25);

            ImageView bin = new ImageView();
            bin.getStyleClass().add("binImage");
            bin.setFitWidth(31);
            bin.setFitHeight(25);

            hBox.getChildren().addAll(code, start, end, percentNum, peopleName, edit, bin);
            flowPane.getChildren().add(hBox);
            bin.setOnMouseClicked(e -> {
                Shop.getShop().getAllDiscounts().remove(discount);
                flowPane.getChildren().remove(hBox);
            });
        }

        return flowPane;
    }

    private VBox createItemOfProfile(String text, String account) {
        VBox vBox = new VBox(2);
        vBox.setPrefSize(500, 80);
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setStyle("-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;");
        Label label = new Label(text);
        label.getStyleClass().add("labelUser");
        Label labelUsername = new Label(account);
        labelUsername.getStyleClass().add("labelUsernameInProfile");
        vBox.getChildren().addAll(label, labelUsername);
        return vBox;
    }

    private void imageViewForSignUp() {
        Label titleOFSignUp = new Label("+ SIGN UP Admin");
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

        Button selectAPhoto = new Button("Select a photo");
        selectAPhoto.setLayoutX(340);
        selectAPhoto.setLayoutY(206);
        selectAPhoto.setPrefWidth(100);
        selectAPhoto.getStyleClass().add("selectPhoto");

        loginPane.getChildren().addAll(titleOFSignUp, selectAPhoto, rectangle);
        selectAPhoto.setOnAction(e -> {
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

    private void printErrorForRegister(String text) {
        error.setText(text);
        error.setLayoutX(100);
        error.setLayoutY(500);
        error.setTextFill(Color.RED);
    }

    public void backToMainMenu() {
        main.updateFilters = true;
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }


//    private void loadDialog() {
////        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
////        jfxDialogLayout.setHeading(new Text("Remove"));
////        jfxDialogLayout.setBody(new Text("Is it safe to delete this user?"));
//        JFXDialog jfxDialog = new JFXDialog(stackPane, new Label("holelsnvdsjvlsdjlsjdljvlds"), JFXDialog.DialogTransition.CENTER);
//
//        jfxDialog.show();
//    }
}
