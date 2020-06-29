package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import controller.AccountManager;
import controller.BuyerManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import view.NumberField;
import view.Purchase;
import view.ZipCode;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;

import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.paymentURL;


public class CartMenu {
    private AnchorPane mainPane;
    private Button btnCartMenu;
    private Button btnLogin;
    private Buyer currentBuyer = ((Buyer) AccountManager.getOnlineAccount());
    private AnchorPane paymentPane;
    private TextField nameField;
    private NumberField phoneNumberField;
    private TextField addressField;
    private ZipCode zipCode;
    private ZipCode discountCode;
    private Label error;
    private Stage paymentPopup;
    private Label payableAmount;
    private JFXToggleButton existenceOfDiscount;
    private Button confirmButton;
    private Button paymentButton;
    private Button increaseCreditButton;
    private double finalTotalPrice;
    private Discount discount;
    private ZipCode creditField;
    private MainMenu main;
    private AnchorPane mainMenu;

    public CartMenu(AnchorPane mainPane, Button btnCartMenu, Button btnLogin, MainMenu main, AnchorPane mainMenu) {
        this.mainPane = mainPane;
        this.btnCartMenu = btnCartMenu;
        this.btnLogin = btnLogin;
        this.main = main;
        this.mainMenu = mainMenu;
    }

    public void changePane() {
        btnCartMenu.setVisible(false);

        AnchorPane allPain = new AnchorPane();
        allPain.setPrefSize(1400, 690);
        allPain.setLayoutY(170);


        VBox purchaseAndPrice = new VBox(10);
        purchaseAndPrice.setStyle("-fx-background-color: white;-fx-background-radius: 10;-fx-border-width: 1;-fx-border-color: #E3E3E3;-fx-border-radius: 10;");
        purchaseAndPrice.setPrefSize(350, 300);
        purchaseAndPrice.setPadding(new Insets(25, 25, 10, 25));
        purchaseAndPrice.setLayoutX(1160);
        purchaseAndPrice.setLayoutY(20);
        purchaseAndPrice.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");

        //--------

        Label totalPrice = new Label("Total price: $" + BuyerManager.getTotalPrice());
        totalPrice.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 18;-fx-text-fill: #353535");

        Label finaTotalPrice = new Label("Total price: $" + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
        finaTotalPrice.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 18;-fx-text-fill: green");

        Label offPrice = new Label("Off price: $" + (BuyerManager.getTotalPrice() - BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart())));
        offPrice.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 18;-fx-text-fill: red");

        JFXButton purchase = new JFXButton("Purchase");
        if (BuyerManager.getTotalPrice() == 0) {
            purchase.setDisable(true);
        }
        purchase.getStyleClass().add("purchase");
        purchase.setOnMouseClicked(event -> {
            try {
                if (AccountManager.getOnlineAccount().getUsername().equals("temp")) {
                    new Login(mainPane, btnLogin, btnCartMenu, mainMenu, main).popupLogin(null);
                } else {
                    paymentPopup();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        purchaseAndPrice.getChildren().addAll(totalPrice,offPrice, finaTotalPrice, purchase);

        ///-----


        AnchorPane cartMenuPack = new AnchorPane();
        cartMenuPack.setLayoutX(30);
        cartMenuPack.setLayoutY(20);
        cartMenuPack.setPrefSize(1110, 650);
        cartMenuPack.setStyle("-fx-background-color: white;-fx-background-radius: 10;-fx-border-width: 1;-fx-border-color: #E3E3E3;-fx-border-radius: 10;");

        AnchorPane innerPane = new AnchorPane();
        ScrollPane cartMenuScrollPane = new ScrollPane(innerPane);
        cartMenuScrollPane.setLayoutY(5);
        cartMenuScrollPane.setLayoutX(1);
        cartMenuScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cartMenuScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox products = new VBox();
        products.setSpacing(0);

        for (Good good : currentBuyer.getCart()) {
            ImageView goodImage = new ImageView(new Image("file:" + good.getImagePath()));
            goodImage.setFitWidth(150);
            goodImage.setFitHeight(150);
            goodImage.getStyleClass().add("goodImage");

            Rectangle line = new Rectangle(110, 1);
            line.setStyle("-fx-fill: #e8e8e8");

            VBox productFields = new VBox(labelForCartField(good.getName(), "Name: "),
                    labelForCartField(good.getCompany(), "Company: "), labelForCartField("" + good.getPrice(), "Price: $"), line);

            HBox productBox = new HBox(goodImage, productFields);
            productFields.getChildren().add(increaseOrDecreaseNumber(totalPrice,finaTotalPrice, offPrice, good, productBox, products));
            productFields.setSpacing(9);
            productBox.setPrefWidth(1000);
            productBox.setPadding(new Insets(10, 10, 10, 10));
            productBox.setStyle("-fx-border-width: 1;-fx-border-color: #efefef");
            productBox.setSpacing(30);
            productBox.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");
            products.getChildren().add(productBox);

        }

        VBox vBox = new VBox(products);
        vBox.setSpacing(50);
        innerPane.getChildren().add(vBox);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        innerPane.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");
        innerPane.setStyle("-fx-background-color: white");
        cartMenuScrollPane.getStyleClass().add("scroll-bar");
        cartMenuPack.getChildren().add(cartMenuScrollPane);
        allPain.getChildren().addAll(cartMenuPack, purchaseAndPrice);
        mainPane.getChildren().add(allPain);
        innerPane.setPrefSize(1100, 640);
        Login.currentPane = allPain;
    }

    private Label labelForCartField(String text, String type) {
        Label label = new Label(type + text);
        label.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 16;-fx-text-fill: #313131");
        return label;
    }

    private HBox increaseOrDecreaseNumber(Label totalPrice,Label finalTotalPrice,Label offPrice, Good good, HBox productBox, VBox products) {
        final int[] count = {good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername())};
        TextField number = new TextField();
        number.setAlignment(Pos.TOP_CENTER);
        number.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 12;");
        number.setPadding(new Insets(0, 0, 10, 0));
        number.setPrefSize(50, 30);
        number.setStyle("-fx-background-color: none");
        number.setText("" + good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername()));
        number.setDisable(true);
        ImageView plus = new ImageView();
        plus.getStyleClass().add("plusCart");
        plus.setFitHeight(30);
        plus.setFitWidth(30);
        ImageView minus = new ImageView();
        plus.setOnMouseClicked(event -> {
            if (count[0] < good.getNumber()) {
                minus.getStyleClass().remove("minesCart2");
                minus.getStyleClass().add("minesCart");
                count[0] += 1;
                number.setText("" + count[0]);
                good.getGoodsInBuyerCart().put(currentBuyer.getUsername(), count[0]);
                totalPrice.setText("Total price : " + BuyerManager.getTotalPrice());
                finalTotalPrice.setText("Final price price: $" + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
                offPrice.setText("Off price: $" + (BuyerManager.getTotalPrice() - BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart())));
            }

        });

        minus.getStyleClass().add("minesCart");
        minus.setFitHeight(30);
        minus.setFitWidth(30);
        if (count[0] == 1) {
            minus.getStyleClass().add("minesCart2");
        }
        minus.setOnMouseClicked(event -> {
            count[0] -= 1;
            number.setText("" + count[0]);
            good.getGoodsInBuyerCart().put(currentBuyer.getUsername(), count[0]);
            if (count[0] == 1) {
                minus.getStyleClass().add("minesCart2");
            }
            if (count[0] == 0) {
                good.getGoodsInBuyerCart().remove(AccountManager.getOnlineAccount().getUsername());
                products.getChildren().remove(productBox);
                ((Buyer) AccountManager.getOnlineAccount()).getCart().remove(good);
            }
            totalPrice.setText("Total price : " + BuyerManager.getTotalPrice());
            finalTotalPrice.setText("Final price price: $" + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
            offPrice.setText("Off price: $" + (BuyerManager.getTotalPrice() - BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart())));
        });

        return new HBox(minus, number, plus);
    }

    private void paymentPopup() throws IOException {
        paymentPane = new AnchorPane();
        nameField = new TextField();
        phoneNumberField = new NumberField();
        addressField = new TextField();
        zipCode = new ZipCode();
        discountCode = new ZipCode();
        error = new Label();
        paymentPopup = new Stage();
        paymentPopup.initModality(Modality.APPLICATION_MODAL);
        URL url = Paths.get(paymentURL).toUri().toURL();
        AnchorPane layout = FXMLLoader.load(url);
        Scene scene1 = new Scene(layout);
        paymentPopup.setMaximized(true);

        Label payment = new Label("Payment");
        payment.getStyleClass().add("labelForPaymentTitle");
        payment.setLayoutX(160);
        payment.setLayoutY(38);

        layout.setStyle("-fx-background-color: none;");
        paymentPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        paymentPane.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");
        paymentPane.setPrefWidth(480);
        paymentPane.setPrefHeight(580);

        fade(10, 0.5);

        error.getStyleClass().add("error");
        error.setLayoutX(60);
        error.setLayoutY(530);
        error.setAlignment(Pos.CENTER);
        layout.setLayoutX(500);
        layout.setLayoutY(150);
        layout.getChildren().add(paymentPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        paymentPopup.setScene(scene1);
        paymentPopup.initStyle(StageStyle.TRANSPARENT);
        paymentPopup.getScene().setFill(Color.TRANSPARENT);
        paymentPane.getChildren().addAll(exitButton(), nameField(), phoneNumberField(), addressField(), zipCode(),
                discountCode(), existenceOfDiscount(), payment, totalPrice(), confirmButton(), paymentButton(), increaseCreditButton(),
                payableAmount(), error);
        paymentPopup.showAndWait();

    }

    private TextField nameField() {
        nameField.setPromptText("name");
        nameField.setLayoutX(90);
        nameField.setLayoutY(85);
        nameField.setPrefHeight(50);
        nameField.setPrefWidth(310);
        nameField.getStyleClass().add("paymentFields");
        nameField.getStyleClass().add("username");
        return nameField;
    }

    private NumberField phoneNumberField() {
        phoneNumberField.setPromptText("phone number");
        phoneNumberField.setLayoutX(90);
        phoneNumberField.setLayoutY(145);
        phoneNumberField.setPrefHeight(50);
        phoneNumberField.setPrefWidth(310);
        phoneNumberField.getStyleClass().add("paymentFields");
        phoneNumberField.getStyleClass().add("phoneNumber");
        return phoneNumberField;
    }

    private TextField addressField() {
        addressField.setPromptText("address");
        addressField.setLayoutX(90);
        addressField.setLayoutY(205);
        addressField.setPrefHeight(50);
        addressField.setPrefWidth(310);
        addressField.getStyleClass().add("paymentFields");
        addressField.getStyleClass().add("address");
        return addressField;
    }

    private TextField zipCode() {
        zipCode.setPromptText("zip code");
        zipCode.setLayoutX(90);
        zipCode.setLayoutY(265);
        zipCode.setPrefHeight(50);
        zipCode.setPrefWidth(310);
        zipCode.getStyleClass().add("paymentFields");
        zipCode.getStyleClass().add("zipCode");
        return zipCode;
    }

    private ZipCode discountCode() {
        discountCode.setPromptText("discount code");
        discountCode.setLayoutX(90);
        discountCode.setLayoutY(325);
        discountCode.setPrefHeight(50);
        discountCode.setPrefWidth(240);
        discountCode.getStyleClass().add("paymentFields");
        discountCode.getStyleClass().add("discountCode");
        discountCode.setDisable(true);
        return discountCode;
    }

    private Label totalPrice() {
        Label totalPrice = new Label("Total price: $ " + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
        totalPrice.getStyleClass().add("totalPrice");
        totalPrice.setLayoutX(90);
        totalPrice.setLayoutY(385);
        totalPrice.setAlignment(Pos.CENTER);
        return totalPrice;
    }

    private Label payableAmount() {
        finalTotalPrice = BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart());
        payableAmount = new Label("payable amount: $ " + ((long) finalTotalPrice));
        payableAmount.getStyleClass().add("totalPrice");
        payableAmount.setLayoutX(90);
        payableAmount.setLayoutY(420);
        payableAmount.setAlignment(Pos.CENTER);
        return payableAmount;
    }

    private JFXToggleButton existenceOfDiscount() {
        existenceOfDiscount = new JFXToggleButton();
        existenceOfDiscount.setToggleColor(WHITE);
        existenceOfDiscount.setToggleLineColor(WHITE);
        existenceOfDiscount.setOnAction(event -> {
            if (existenceOfDiscount.isSelected()) {
                discountCode.setDisable(false);
            } else {
                discountCode.setDisable(true);
            }
        });
        existenceOfDiscount.setLayoutX(335);
        existenceOfDiscount.setLayoutY(322);
        return existenceOfDiscount;
    }

    private Button confirmButton() {
        confirmButton = new Button("Confirm");
        confirmButton.setPrefSize(310, 55);
        confirmButton.setLayoutX(90);
        confirmButton.setLayoutY(465);
        confirmButton.getStyleClass().add("confirm");
        confirmButton.setOnMouseClicked(event -> processPurchase());
        return confirmButton;
    }

    private Button paymentButton() {
        paymentButton = new Button("Payment");
        paymentButton.setPrefSize(150, 55);
        paymentButton.setLayoutX(90);
        paymentButton.setLayoutY(470);
        paymentButton.getStyleClass().add("confirm");
        paymentButton.setOnMouseClicked(event -> processPayment(discount));
        paymentButton.setVisible(false);
        return paymentButton;
    }

    private Button increaseCreditButton() {
        increaseCreditButton = new Button("increase credit");
        increaseCreditButton.setPrefSize(150, 55);
        increaseCreditButton.setLayoutX(250);
        increaseCreditButton.setLayoutY(470);
        increaseCreditButton.getStyleClass().add("confirm");
        increaseCreditButton.setStyle("-fx-font-size: 17");
        increaseCreditButton.setOnMouseClicked(event -> processIncreaseCredit());
        increaseCreditButton.setVisible(false);
        return increaseCreditButton;
    }

    private void processIncreaseCredit() {
        if (paymentButton.isVisible()) {
            paymentButton.setVisible(false);
            creditField = new ZipCode();
            creditField.setPromptText("credit");
            creditField.setPrefSize(150, 55);
            creditField.setLayoutX(90);
            creditField.setLayoutY(470);
            creditField.getStyleClass().add("paymentFields");
            paymentPane.getChildren().add(creditField);
        } else {
            AccountManager.getOnlineAccount().increaseCredit(Long.parseLong(creditField.getText()));
            error.setText("your credit increased");
            creditField.setVisible(false);
            paymentButton.setVisible(true);
        }
    }

    private void processPurchase() {
        if ((discountCode.getText().length() == 0 && existenceOfDiscount.isSelected()) || zipCode.getText().length() == 0 || phoneNumberField.getText().length() == 0 ||
                nameField.getText().length() == 0 || addressField.getText().length() == 0) {
            error.setText("you must fill all the fields");
        } else if (existenceOfDiscount.isSelected()) {
            discount = Shop.getShop().getDiscountWithCode(((int) Long.parseLong(discountCode.getText())));
            Date currentDate = new Date();
            if (discount == null) {
                error.setText("discount does not exist");
            } else if (discount.getStartDate().after(currentDate)) {
                error.setText("It is not yet time to use the discount code");
            } else if (discount.getEndDate().before(currentDate)) {
                error.setText("The discount code has expired");
            } else if (currentBuyer.getDiscountAndNumberOfAvailableDiscount().get(discount.getCode()) == null) {
                error.setText("you cannot use the discount anymore");
            } else {
                error.setText("");
                finalTotalPrice = Purchase.getFinalTotalPrice(discount);
                payableAmount.setText("payable amount: " + ((long) finalTotalPrice));
                confirmButton.setVisible(false);
                paymentButton.setVisible(true);
                increaseCreditButton.setVisible(true);
            }
        } else {
            error.setText("");
            existenceOfDiscount.setDisable(true);
            confirmButton.setVisible(false);
            paymentButton.setVisible(true);
            increaseCreditButton.setVisible(true);
        }
    }

    private void processPayment(Discount discount) {
        if (Purchase.canPay(finalTotalPrice)) {
            Purchase.pay(finalTotalPrice, discount);
            paymentPopup.close();
            fade(0.5, 10);
            backToMainMenu();
        } else {
            error.setText("your credit is not enough");
        }
    }

    private Button exitButton() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExit");
        exitButton.setLayoutY(30);
        exitButton.setLayoutX(435);
        exitButton.setOnAction(event -> {
            paymentPopup.close();
            fade(0.5, 10);
        });
        return exitButton;
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
