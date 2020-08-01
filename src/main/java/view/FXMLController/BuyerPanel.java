package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import view.NumberField;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;

import static javafx.scene.paint.Color.color;

public class BuyerPanel {
    public AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane buyerPane;
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
    private ScrollPane buyerPaneScroll = new ScrollPane();
    private MainMenu main;
    private AnchorPane mainMenu;
    private Button user;
    private Button btnLogin;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Account onlineAccount;
    public Button btnOnlineSupport;
    public Button btnAuction;

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phoneNumber;
    private PasswordField password;

    private NumberField moneyField;
    private FlowPane moneyPane;
    private Label creditLabel;

    public BuyerPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu, Button user, Button btnAuction, Button btnSupporter, Button btnLogin, Socket socket, Account onlineAccount) throws IOException {
        this.main = main;
        this.mainMenu = mainMenu;
        this.mainPane = mainPane;
        this.user = user;
        this.btnLogin = btnLogin;
        buyerPane = new AnchorPane();
        optionsPane = new AnchorPane();
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.onlineAccount = onlineAccount;
        this.btnOnlineSupport = btnSupporter;
        this.btnAuction = btnAuction;
        handelButtonOnMouseClick();
    }

    public void changePane() throws IOException {

        buyerPane.setLayoutY(165);
        optionsPane.setLayoutY(35);
        optionsPane.setLayoutX(30);
        optionsPane.setPrefSize(250, 520);

        //image profile
        HBox hBox = new HBox();
        Circle circle = new Circle(40);
        ImagePattern pattern = new ImagePattern(new Image("file:" + onlineAccount.getImagePath()));
        circle.setFill(pattern);
        circle.setStrokeWidth(4);
        circle.setStroke(Color.rgb(16, 137, 255));

        hBox.setPadding(new Insets(0, 0, 5, 9));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setPrefWidth(170);

        //credit
        ImageView credit = new ImageView(new Image("file:src/main/java/view/image/AdminPanel/credit.png"));
        credit.setFitHeight(20);
        credit.setStyle("-fx-background-color: blue");
        credit.setFitWidth(25);

        dataOutputStream.writeUTF("get_credit");
        dataOutputStream.flush();
        creditLabel = new Label(" $" + dataInputStream.readUTF());
        creditLabel.getStyleClass().add("labelUsername");
        creditLabel.setStyle("-fx-text-fill: #00e429");

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(credit, creditLabel);
        hBox.setSpacing(10);

        Label increase = new Label(" ➕ Credit ");
        increase.getStyleClass().add("creditStyle");
        increase.setPadding(new Insets(5, 5, 5, 5));
        increase.setOnMouseClicked(event -> popupMoney());

        VBox vBoxP = new VBox();
        Label username = new Label("Hi " + onlineAccount.getUsername());
        vBoxP.setAlignment(Pos.CENTER_LEFT);
        vBoxP.setSpacing(5);
        vBoxP.getChildren().addAll(username, hBox1, increase);
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
                createButton("Orders", "src/main/java/view/image/purchase-order"),
                createButton("Discounts", "src/main/java/view/image/AdminPanel/discount"),
                rectangleDown,
                createButton("Log out", "src/main/java/view/image/AdminPanel/logout"));
        vBox.setStyle("-fx-background-color: none;");
        vBox.setPadding(new Insets(10, 10, 10, 8));


        optionsPane.setStyle("-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: #e1e1e1;"
                + "-fx-border-radius: 10;" + "-fx-background-radius: 10");
        optionsPane.getChildren().addAll(vBox);
        buyerPane.getChildren().add(optionsPane);
        optionsPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        Login.currentPane = buyerPane;
        mainPane.getChildren().add(buyerPane);
    }


    private void popupMoney() {
        moneyPane = new FlowPane(8, 8);

        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        layout.getStylesheets().add("file:src/main/java/view/css/loginMenu.css");
        moneyPane.setStyle("-fx-background-color: #eceff1;" + "-fx-background-radius: 30px;");
        moneyPane.setPrefWidth(370);
        moneyPane.setPrefHeight(250);
        moneyPane.setAlignment(Pos.CENTER);

        fade(10, 0.5);

        layout.setLayoutX(580);
        layout.setLayoutY(300);
        layout.getChildren().add(moneyPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);
        layout.getChildren().add(exitPopupMoney());
        moneyPane.getChildren().addAll(moneyField(), confirmMoney());
        popupWindow.showAndWait();
    }

    private NumberField moneyField() {
        moneyField = new NumberField();
        moneyField.setPromptText("$ Money");
        moneyField.setPrefHeight(30);
        moneyField.setPrefWidth(220);
        moneyField.getStyleClass().add("MoneyField");
        return moneyField;
    }

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    private Button confirmMoney() {
        Button submit = new Button();
        submit.setText("Increase");
        submit.setPrefSize(220, 40);
        submit.getStyleClass().add("increase");
        submit.setOnMouseClicked(event -> processIncreaseCredit());
        return submit;
    }

    private void processIncreaseCredit() {
        try {
            dataOutputStream.writeUTF("increase_credit_" + moneyField.getText());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        moneyPane.getChildren().clear();
        ImageView gif = new ImageView(new Image("file:src/main/java/view/image/checkgif.gif"));
        gif.setFitWidth(170);
        gif.setFitHeight(170);
        moneyPane.setVgap(5);
        Button ok = new Button();
        ok.setText("Ok");
        ok.setPrefSize(200, 25);
        ok.getStyleClass().add("ok");
        ok.setOnMouseClicked(event1 -> {
            creditLabel.setText(" $" + moneyField.getText());
            popupWindow.close();
            fade(0.5, 10);
        });
        moneyPane.getChildren().addAll(gif, ok);
    }

    private Button exitPopupMoney() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExit");
        exitButton.setLayoutY(25);
        exitButton.setLayoutX(335);
        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
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
        button.getStyleClass().add("button");
        button.setGraphic(imageView);
        button.setAlignment(Pos.CENTER_LEFT);
        if (text.equals("Profile")) {
            button.setGraphic(imageViewHover);
//            button.setStyle("-fx-text-fill: red");
            selectedButton = button;
            imageViewSelectedButton = imageView;
        }

        button.setOnMouseClicked(e -> {
            selectedButton.setGraphic(imageViewSelectedButton);
            imageViewSelectedButton = imageView;
            selectedButton = button;
            button.setGraphic(imageViewHover);
            try {
                handelButtonOnMouseClick();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    private void handelButtonOnMouseClick() throws IOException {
        btnOnlineSupport.setVisible(true);
        btnAuction.setVisible(true);
        buyerPane.getChildren().remove(buyerPaneScroll);
        buyerPaneScroll.setPrefSize(1150, 620);
        buyerPaneScroll.getStyleClass().add("scroll-bar");
        buyerPaneScroll.setLayoutX(330);
        buyerPaneScroll.setLayoutY(35);

        dataOutputStream.writeUTF("get_buyer_" + onlineAccount.getUsername());
        dataOutputStream.flush();
        Type buyerType = new TypeToken<Buyer>() {
        }.getType();
        onlineAccount = new Gson().<Buyer>fromJson(dataInputStream.readUTF(), buyerType);

        switch (selectedButton.getText()) {
            case "Profile":
                FlowPane flowPane = new FlowPane();
                flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
                flowPane.setPrefWidth(1200);
                flowPane.setPrefHeight(420);
                flowPane.setPadding(new Insets(50, 0, 10, 70));
                flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

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

                flowPane.getChildren().addAll(createItemOfProfile("Username:", onlineAccount.getUsername()),
                        createItemOfProfile("Full name:", onlineAccount.getFirstName() + " " + onlineAccount.getLastName()),
                        createItemOfProfile("Phone number:", onlineAccount.getPhoneNumber()),
                        createItemOfProfile("Email:", onlineAccount.getEmail()),
                        hyperLink);
                buyerPaneScroll.setContent(flowPane);
                buyerPane.getChildren().add(buyerPaneScroll);
                buyerPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                break;
            case "Log out":
                dataOutputStream.writeUTF("logout");
                dataOutputStream.flush();
                onlineAccount = new Buyer("temp");
                main.onlineAccount = onlineAccount;
                user.setVisible(false);
                btnLogin.setVisible(true);
                backToMainMenu();
                break;
            case "Discounts":
                buyerPaneScroll.setContent(showDiscounts());
                buyerPane.getChildren().add(buyerPaneScroll);
                break;
            case "Orders":
                buyerPaneScroll.setContent(showOrders());
                buyerPane.getChildren().add(buyerPaneScroll);
                break;
        }
        buyerPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private VBox boxForEdit(String name) {
        VBox boxFirstName = new VBox(2);
        boxFirstName.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label();
        TextField field = new TextField();
        field.setPrefSize(350, 40);

        if (name.equals("First name: ")) {
            label.setText(" First name: ");
            field.setText(onlineAccount.getFirstName());
            firstName = field;
        } else if (name.equals("Last name: ")) {
            label.setText(" Last name: ");
            lastName = field;
            field.setText(onlineAccount.getLastName());
        } else if (name.equals("Email: ")) {
            label.setText(" Email: ");
            email = field;
            field.setText(onlineAccount.getEmail());
        } else if (name.equals("Phone: ")) {
            NumberField numberField = new NumberField();
            numberField.setPrefSize(350, 40);
            numberField.setText(onlineAccount.getPhoneNumber());
            label.setText(" Phone number: ");
            phoneNumber = numberField;
            field = numberField;
        } else if (name.equals("password")) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Current password");
            passwordField.setPrefSize(350, 40);
            password = passwordField;
            field = passwordField;
        } else if (name.equals("newPass")) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("New password");
            passwordField.setPrefSize(350, 40);
            field = passwordField;
        }
        label.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 15;-fx-text-fill:rgb(16,137,255)");
        field.getStyleClass().add("text-fieldForEdit");
        boxFirstName.getChildren().addAll(label, field);

        return boxFirstName;

    }

    private void editProfilePain(FlowPane flowPane) {
        flowPane.getChildren().clear();
        flowPane.setHgap(80);
        flowPane.setVgap(12);

        VBox backBox = new VBox();
        backBox.setPrefSize(800, 40);
        ImageView back = new ImageView();
        back.getStyleClass().add("backStyle");
        back.setFitWidth(30);
        back.setFitHeight(30);
        backBox.getChildren().add(back);
        back.setOnMouseClicked(event -> {
            try {
                handelButtonOnMouseClick();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox currentPass = boxForEdit("password");
        currentPass.setDisable(true);
        VBox newPass = boxForEdit("newPass");

        VBox submitBox = new VBox();
        submitBox.setPadding(new Insets(20, 0, 0, 0));
        Button submit = new Button("Submit");
        submit.getStyleClass().add("buttonSubmit");
        submit.setPrefSize(780, 40);
        submit.setOnMouseClicked(event -> {
            try {
                processEdit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        submitBox.getChildren().add(submit);

        flowPane.getChildren().addAll(backBox, boxForEdit("First name: "), boxForEdit("Last name: "),
                boxForEdit("Email: "), boxForEdit("Phone: "), newPass, submitBox);


    }

    private void processEdit() throws IOException {
        dataOutputStream.writeUTF("edit_profile_" + password.getText() + "_" + firstName.getText() + "_" +
                lastName.getText() + "_" + phoneNumber.getText() + "_" + email.getText());
        dataOutputStream.flush();
        handelButtonOnMouseClick();
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

    private void backToMainMenu() {
        main.updateFilters = true;
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }

    private FlowPane showDiscounts() throws IOException {
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
        startDate.setPrefWidth(270);
        startDate.getStyleClass().add("labelForDiscount");

        Label endDate = new Label("  " + "End date");
        endDate.setGraphic(line());
        endDate.setPrefWidth(270);
        endDate.getStyleClass().add("labelForDiscount");

        Label percent = new Label("  " + "Percent");
        percent.setGraphic(line());
        percent.setPrefWidth(100);
        percent.getStyleClass().add("labelForDiscount");

        Label amount = new Label("  " + "Maximum Amount");
        amount.setGraphic(line());
        amount.setPrefWidth(274);
        amount.getStyleClass().add("labelForDiscount");


        hBoxTitle.getChildren().addAll(discountCode, startDate, endDate, percent, amount);
        flowPane.getChildren().add(hBoxTitle);

        dataOutputStream.writeUTF("get_buyer_" + onlineAccount.getUsername());
        dataOutputStream.flush();
        Type buyerType = new TypeToken<Buyer>() {
        }.getType();
        onlineAccount = new Gson().fromJson(dataInputStream.readUTF(), buyerType);
        main.onlineAccount = this.onlineAccount;

        for (Discount discount : ((Buyer) onlineAccount).getDiscounts()) {
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
            start.setPrefWidth(270);
            start.getStyleClass().add("labelForDiscount");

            Label end = new Label("  " + discount.getEndDate());
            end.setGraphic(line());
            end.setPrefWidth(270);
            end.getStyleClass().add("labelForDiscount");

            Label percentNum = new Label("  " + discount.getPercent());
            percentNum.setGraphic(line());
            percentNum.setPrefWidth(100);
            percentNum.getStyleClass().add("labelForDiscount");


            Label maxAmount = new Label("  " + discount.getMaxAmountOfDiscount());
            maxAmount.setGraphic(line());
            maxAmount.setPrefWidth(274);
            maxAmount.getStyleClass().add("labelForDiscount");


            hBox.getChildren().addAll(code, start, end, percentNum, maxAmount);
            flowPane.getChildren().add(hBox);
        }

        return flowPane;
    }

    private FlowPane showOrders() throws IOException {

        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1165);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        HBox hBoxTitle = new HBox(0);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(60);

        Label code = new Label("Code");
        code.setPrefWidth(55);
        code.getStyleClass().add("labelForDiscount");

        Label date = new Label("  " + "Date");
        date.setGraphic(line());
        date.setPrefWidth(270);
        date.getStyleClass().add("labelForDiscount");

        Label paid = new Label("  " + "Paid");
        paid.setGraphic(line());
        paid.setPrefWidth(150);
        paid.getStyleClass().add("labelForDiscount");

        Label discount = new Label("  " + "discount");
        discount.setGraphic(line());
        discount.setPrefWidth(100);
        discount.getStyleClass().add("labelForDiscount");

        Label status = new Label("  " + "Status");
        status.setGraphic(line());
        status.setPrefWidth(374);
        status.getStyleClass().add("labelForDiscount");

        hBoxTitle.getChildren().addAll(code, date, paid, discount, status);
        flowPane.getChildren().add(hBoxTitle);

        dataOutputStream.writeUTF("get_buyer_" + onlineAccount.getUsername());
        dataOutputStream.flush();
        Type buyerType = new TypeToken<Buyer>() {
        }.getType();
        String res = dataInputStream.readUTF();
        System.out.println(res);
        onlineAccount = new Gson().fromJson(res, buyerType);
        main.onlineAccount = this.onlineAccount;

        for (BuyerLog log : ((Buyer) onlineAccount).getBuyerLogs()) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(60);

            Label labelCode = new Label("" + log.getId());
            labelCode.setPrefWidth(55);
            labelCode.getStyleClass().add("labelForDiscount");


            Label labelDate = new Label("  " + log.getDate());
            labelDate.setGraphic(line());
            labelDate.setPrefWidth(270);
            labelDate.getStyleClass().add("labelForDiscount");

            Label labelPaid = new Label("  " + log.getPaidAmount());
            labelPaid.setGraphic(line());
            labelPaid.setPrefWidth(150);
            labelPaid.getStyleClass().add("labelForDiscount");

            Label labelDiscount = new Label("  " + log.getDiscount());
            labelDiscount.setGraphic(line());
            labelDiscount.setPrefWidth(100);
            labelDiscount.getStyleClass().add("labelForDiscount");

            Label labelStatus = new Label("  " + log.getStatus());
            labelStatus.setGraphic(line());
            labelStatus.setPrefWidth(339);
            labelStatus.getStyleClass().add("labelForDiscount");

            ImageView imageViewDetail = new ImageView();
            imageViewDetail.setOnMouseClicked(event -> {
                flowPane.getChildren().clear();
                showDetail(flowPane, log);
            });
            imageViewDetail.getStyleClass().add("imageViewDetail");
            imageViewDetail.setFitWidth(35);
            imageViewDetail.setFitHeight(35);

            hBox.getChildren().addAll(labelCode, labelDate, labelPaid, labelDiscount, labelStatus, imageViewDetail);
            flowPane.getChildren().add(hBox);
        }

        return flowPane;
    }

    private void showDetail(FlowPane flowPane, BuyerLog buyerLog) {
        HBox hBoxTitle = new HBox(0);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(60);

        Label sellerTitle = new Label("  " + "Seller");
        sellerTitle.setPrefWidth(200);
        sellerTitle.getStyleClass().add("labelForDiscount");

        Label goodTitle = new Label("  " + "Goods");
        goodTitle.setGraphic(line());
        goodTitle.setPrefWidth(703);
        goodTitle.getStyleClass().add("labelForDiscount");

        hBoxTitle.getChildren().addAll(sellerTitle, goodTitle, back(flowPane));
        flowPane.getChildren().add(hBoxTitle);

        for (String sellerUsername : buyerLog.getSellersToHisGoods().keySet()) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 0, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(150);

            Label sellerName = new Label("  " + sellerUsername);
            sellerName.setPrefWidth(200);
            sellerName.getStyleClass().add("labelForDiscount");

            HBox goods = new HBox();
            goods.setStyle("-fx-background-color: none");
            goods.setPrefSize(758, 120);
            ScrollPane goodsScrollPane = new ScrollPane(goods);
            goodsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            goodsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            for (Good good : buyerLog.getSellersToHisGoods().get(sellerUsername)) {
                VBox productBox = new VBox();
                productBox.setPrefWidth(90);
                productBox.setPrefHeight(100);
                productBox.getStyleClass().add("vBoxInMainMenu");
                ImageView logoImage = new ImageView(new Image("file:" + good.getImagePath()));
                logoImage.setFitHeight(60);
                logoImage.setFitWidth(60);
                Label name = new Label(good.getName());
                Label price = new Label("$" + good.getPrice() + "");
                Label number = new Label(good.getGoodsInBuyerCart().get(onlineAccount.getUsername()) + "x");
                name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
                price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 14px;" + "-fx-font-weight: bold;");
                number.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + " -fx-font-size: 13px;");
                productBox.setAlignment(Pos.CENTER);


                productBox.getChildren().addAll(logoImage, name, price, number);

                if (good.getImagePath().contains("file.png")) {
                    FileChooser fileChooser = new FileChooser();
                    Stage fileChooserWindow = new Stage();
                    Label don = new Label("Download");
                    don.setPadding(new Insets(4, 4, 4, 4));
                    don.getStyleClass().add("don");
                    productBox.getChildren().add(don);
                    don.setOnMouseClicked(event -> {
                        try {
                            dataOutputStream.writeUTF("receiveVideoFile_" + good.getId());
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        File selectedVideoFile = fileChooser.showSaveDialog(fileChooserWindow);
                        receiveFile(selectedVideoFile);
                    });

                }
                goods.getChildren().add(productBox);
            }


            hBox.getChildren().addAll(sellerName, goodsScrollPane);
            flowPane.getChildren().add(hBox);
        }

    }

    private String receiveFile(File selectedVideoFile) {
        try {
            int bytesRead;

            String fileName = dataInputStream.readUTF();
            OutputStream output = new FileOutputStream(selectedVideoFile);
            long size = dataInputStream.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            System.out.println("File " + fileName + " received from Server.");
            return "src/main/java/view/fileSender/" + fileName;
        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
            return null;
        }

    }

    private ImageView back(FlowPane flowPane) {
        ImageView imageViewBack = new ImageView();
        imageViewBack.setOnMouseClicked(event -> {
            buyerPaneScroll.getChildrenUnmodifiable().remove(flowPane);
            try {
                buyerPaneScroll.setContent(showOrders());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        imageViewBack.getStyleClass().add("imageViewBack");
        imageViewBack.setFitWidth(45);
        imageViewBack.setFitHeight(45);

        return imageViewBack;
    }

    private Rectangle line() {
        Rectangle line = new Rectangle(2, 60);
        line.setStyle("-fx-fill: #d5d5d5");
        return line;
    }

}

