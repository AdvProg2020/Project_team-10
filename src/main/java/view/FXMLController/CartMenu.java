package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import controller.AccountManager;
import controller.BuyerManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Buyer;
import model.Discount;
import model.Good;
import model.Shop;
import view.NumberField;
import view.Purchase;
import view.ZipCode;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;

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
        AnchorPane innerPane = new AnchorPane();
        ScrollPane cartMenuScrollPane = new ScrollPane(innerPane);
        cartMenuScrollPane.setLayoutY(164);
        cartMenuScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cartMenuScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox products = new VBox();
        products.setSpacing(50);
        Label totalPrice = new Label();
        for (Good good : currentBuyer.getCart()) {
            ImageView goodImage = new ImageView(new Image("file:" + good.getImagePath()));
            goodImage.setFitWidth(300);
            goodImage.setFitHeight(300);
            goodImage.getStyleClass().add("goodImage");

            VBox productFields = new VBox(new Label("name: " + good.getName()),
                    new Label("company: " + good.getCompany()), new Label("price: " + good.getPrice()));
            productFields.getChildren().add(increaseOrDecreaseNumber(totalPrice, good));
            productFields.setSpacing(25);
            HBox productBox = new HBox(goodImage, productFields);
            productBox.setSpacing(50);
            productBox.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");
            products.getChildren().add(productBox);

        }
        JFXButton purchase = new JFXButton("Purchase");
        totalPrice.setText("Total price : " + BuyerManager.getTotalPrice());
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
        HBox purchaseAndTotalPrice = new HBox(purchase, totalPrice);
        purchaseAndTotalPrice.setSpacing(500);
        VBox vBox = new VBox(products, purchaseAndTotalPrice);
        vBox.setSpacing(50);
        innerPane.getChildren().add(vBox);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        innerPane.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");
        mainPane.getChildren().add(cartMenuScrollPane);
        innerPane.setPrefSize(1200, 699);
        Login.currentPane = cartMenuScrollPane;
    }

    private HBox increaseOrDecreaseNumber(Label totalPrice, Good good) {
        final int[] count = {good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername())};
        TextField number = new TextField();
        number.setAlignment(Pos.CENTER);
        number.setPrefSize(70, 45);
        number.setText("" + good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername()));
        number.setDisable(true);
        JFXButton plus = new JFXButton("+");
        plus.setPrefSize(45, 45);
        plus.setOnMouseClicked(event -> {
            if (count[0] < good.getNumber()) {
                count[0] += 1;
                number.setText("" + count[0]);
                good.getGoodsInBuyerCart().put(currentBuyer.getUsername(), count[0]);
                totalPrice.setText("Total price : " + BuyerManager.getTotalPrice());
            }
        });
        JFXButton minus = new JFXButton("-");
        minus.setPrefSize(45, 45);
        minus.setOnMouseClicked(event -> {
            if (count[0] != 0) {
                count[0] -= 1;
                number.setText("" + count[0]);
                good.getGoodsInBuyerCart().put(currentBuyer.getUsername(), count[0]);
                totalPrice.setText("Total price : " + BuyerManager.getTotalPrice());
            }
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
        payment.setLayoutY(30);

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
        nameField.setLayoutY(80);
        nameField.setPrefHeight(50);
        nameField.setPrefWidth(310);
        nameField.getStyleClass().add("paymentFields");
        nameField.getStyleClass().add("username");
        return nameField;
    }

    private NumberField phoneNumberField() {
        phoneNumberField.setPromptText("phone number");
        phoneNumberField.setLayoutX(90);
        phoneNumberField.setLayoutY(140);
        phoneNumberField.setPrefHeight(50);
        phoneNumberField.setPrefWidth(310);
        phoneNumberField.getStyleClass().add("paymentFields");
        phoneNumberField.getStyleClass().add("phoneNumber");
        return phoneNumberField;
    }

    private TextField addressField() {
        addressField.setPromptText("address");
        addressField.setLayoutX(90);
        addressField.setLayoutY(200);
        addressField.setPrefHeight(50);
        addressField.setPrefWidth(310);
        addressField.getStyleClass().add("paymentFields");
        addressField.getStyleClass().add("address");
        return addressField;
    }

    private TextField zipCode() {
        zipCode.setPromptText("zip code");
        zipCode.setLayoutX(90);
        zipCode.setLayoutY(260);
        zipCode.setPrefHeight(50);
        zipCode.setPrefWidth(310);
        zipCode.getStyleClass().add("paymentFields");
        zipCode.getStyleClass().add("zipCode");
        return zipCode;
    }

    private ZipCode discountCode() {
        discountCode.setPromptText("discount code");
        discountCode.setLayoutX(90);
        discountCode.setLayoutY(320);
        discountCode.setPrefHeight(50);
        discountCode.setPrefWidth(310);
        discountCode.getStyleClass().add("paymentFields");
        discountCode.getStyleClass().add("discountCode");
        discountCode.setDisable(true);
        return discountCode;
    }

    private Label totalPrice() {
        Label totalPrice = new Label("Total price: " + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
        totalPrice.getStyleClass().add("totalPrice");
        totalPrice.setLayoutX(90);
        totalPrice.setLayoutY(380);
        totalPrice.setAlignment(Pos.CENTER);
        return totalPrice;
    }

    private Label payableAmount() {
        finalTotalPrice = BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart());
        payableAmount = new Label("payable amount: " + ((long) finalTotalPrice));
        payableAmount.getStyleClass().add("totalPrice");
        payableAmount.setLayoutX(90);
        payableAmount.setLayoutY(420);
        payableAmount.setAlignment(Pos.CENTER);
        return payableAmount;
    }

    private JFXToggleButton existenceOfDiscount() {
        existenceOfDiscount = new JFXToggleButton();
        existenceOfDiscount.setOnAction(event -> {
            if (existenceOfDiscount.isSelected()) {
                discountCode.setDisable(false);
            } else {
                discountCode.setDisable(true);
            }
        });
        existenceOfDiscount.setLayoutX(20);
        existenceOfDiscount.setLayoutY(315);
        return existenceOfDiscount;
    }

    private Button confirmButton() {
        confirmButton = new Button("Confirm");
        confirmButton.setPrefSize(310, 55);
        confirmButton.setLayoutX(90);
        confirmButton.setLayoutY(470);
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
