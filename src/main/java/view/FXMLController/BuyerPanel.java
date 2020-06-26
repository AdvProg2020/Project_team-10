package view.FXMLController;

import controller.AccountManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
import javafx.stage.Stage;
import model.*;
import view.NumberField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuyerPanel {
    public AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane buyerPane;
    private Button selectedButton = new Button("Profile");
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

    public BuyerPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu) {
        this.main = main;
        this.mainMenu = mainMenu;
        this.mainPane = mainPane;
        buyerPane = new AnchorPane();
        optionsPane = new AnchorPane();
        handelButtonOnMouseClick();
    }

    public void changePane() {
        buyerPane.setLayoutY(165);
        optionsPane.setLayoutY(35);
        optionsPane.setLayoutX(30);
        optionsPane.setPrefSize(250, 520);

        //image profile
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

        //credit
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
                createButton("Orders", "src/main/java/view/image/AdminPanel/discount"),
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
            button.setStyle("-fx-text-fill: red");
        }

        button.setOnMouseClicked(e -> {
            selectedButton = button;
            button.setGraphic(imageViewHover);
            handelButtonOnMouseClick();
        });
        return button;
    }

    private void handelButtonOnMouseClick() {
        buyerPane.getChildren().remove(buyerPaneScroll);
        buyerPaneScroll.setPrefSize(1150, 620);
        buyerPaneScroll.getStyleClass().add("scroll-bar");
        buyerPaneScroll.setLayoutX(330);
        buyerPaneScroll.setLayoutY(35);
        Account account = AccountManager.getOnlineAccount();

        if (selectedButton.getText().equals("Profile")) {
            FlowPane flowPane = new FlowPane();
            flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
            flowPane.setPrefWidth(1200);
            flowPane.setPrefHeight(420);
            flowPane.setPadding(new Insets(50, 0, 10, 70));
            flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
            flowPane.getChildren().addAll(createItemOfProfile("Username:", account.getUsername()),
                    createItemOfProfile("Full name:", account.getFirstName() + " " + account.getLastName()),
                    createItemOfProfile("Phone number:", account.getPhoneNumber()),
                    createItemOfProfile("Email:", account.getEmail()));
            buyerPaneScroll.setContent(flowPane);
            buyerPane.getChildren().add(buyerPaneScroll);
            buyerPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        } else if (selectedButton.getText().equals("Log out")) {
            backToMainMenu();
        } else if (selectedButton.getText().equals("Discounts")) {
            buyerPaneScroll.setContent(showDiscounts());
            buyerPane.getChildren().add(buyerPaneScroll);
        } else if (selectedButton.getText().equals("Orders")) {
            buyerPaneScroll.setContent(showOrders());
            buyerPane.getChildren().add(buyerPaneScroll);
        }
        buyerPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }

    private FlowPane showDiscounts() {
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

            Label amount = new Label("  " + "Maximum Amount");
            amount.setGraphic(line());
            amount.setPrefWidth(274);
            amount.getStyleClass().add("labelForDiscount");


            hBoxTitle.getChildren().addAll(discountCode, startDate, endDate, percent, amount);
            flowPane.getChildren().add(hBoxTitle);


            for (Discount discount : ((Buyer) AccountManager.getOnlineAccount()).getDiscounts()) {
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


                Label maxAmount = new Label("  " + discount.getMaxAmountOfDiscount());
                maxAmount.setGraphic(line());
                maxAmount.setPrefWidth(274);
                maxAmount.getStyleClass().add("labelForDiscount");


                hBox.getChildren().addAll(code, start, end, percentNum, maxAmount);
                flowPane.getChildren().add(hBox);
            }

            return flowPane;
    }

    private FlowPane showOrders() {

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

        Label code = new Label("Code");
        code.setPrefWidth(55);
        code.getStyleClass().add("labelForDiscount");

        Label date = new Label("  " + "Date");
        date.setGraphic(line());
        date.setPrefWidth(255);
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
        status.setPrefWidth(285);
        status.getStyleClass().add("labelForDiscount");

        hBoxTitle.getChildren().addAll(code, date, paid, discount, status);
        flowPane.getChildren().add(hBoxTitle);


        for (BuyerLog log : ((Buyer) AccountManager.getOnlineAccount()).getBuyerLogs()) {
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
            labelDate.setPrefWidth(255);
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
            labelStatus.setPrefWidth(250);
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

        ScrollPane scrollPane = new ScrollPane();


        Label seller1 = new Label("  " + "Seller");
        seller1.setPrefWidth(300);
        seller1.getStyleClass().add("labelForDiscount");

        Label good = new Label("  " + "Goods");
        good.setGraphic(line());
        good.setPrefWidth(500);
        good.getStyleClass().add("labelForDiscount");

        hBoxTitle.getChildren().addAll(seller1, good);
        flowPane.getChildren().add(hBoxTitle);

        for (String seller : buyerLog.getSellersToHisGoods().keySet()) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(60);

            Label sellerName = new Label("  " + seller);
            sellerName.setGraphic(line());
            sellerName.setPrefWidth(150);
            sellerName.getStyleClass().add("labelForDiscount");

            hBox.getChildren().add(sellerName);
            flowPane.getChildren().add(hBox);
        }

    }

    private Rectangle line() {
        Rectangle line = new Rectangle(2, 60);
        line.setStyle("-fx-fill: #d5d5d5");
        return line;
    }

}
