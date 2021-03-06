package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
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

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.paymentURL;


public class CartMenu {
    private AnchorPane mainPane;
    private Button btnCartMenu;
    private Button btnLogin;
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
    private Button bankAccountButton;
    private Button creditButton;
    private double finalTotalPrice;
    private Discount discount;
    private ZipCode creditField;
    private MainMenu main;
    private AnchorPane mainMenu;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Account onlineAccount;
    private String token;
    public Button btnOnlineSupport;
    public Button btnAuction;

    public CartMenu(AnchorPane mainPane, Button btnCartMenu, Button btnLogin, Button btnAuction, Button btnSupporter, MainMenu main, AnchorPane mainMenu
            , Socket socket, Account onlineAccount, String token) throws IOException {
        this.mainPane = mainPane;
        this.btnCartMenu = btnCartMenu;
        this.btnLogin = btnLogin;
        this.main = main;
        this.mainMenu = mainMenu;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.onlineAccount = onlineAccount;
        this.token = token;
        this.btnOnlineSupport = btnSupporter;
        this.btnAuction = btnAuction;
    }

    public long totalPrice;
    public long finalPrice;
    public JFXButton purchase;

    public void changePane() throws IOException {
        btnCartMenu.setVisible(false);

        AnchorPane allPain = new AnchorPane();
        allPain.setPrefSize(1400, 690);
        allPain.setLayoutY(170);


        VBox purchaseAndPrice = new VBox(10);
        purchaseAndPrice.setStyle("-fx-background-color: white;-fx-background-radius: 10;-fx-border-width: 1;" +
                "-fx-border-color: #E3E3E3;-fx-border-radius: 10;");
        purchaseAndPrice.setPrefSize(350, 300);
        purchaseAndPrice.setPadding(new Insets(25, 25, 10, 25));
        purchaseAndPrice.setLayoutX(1160);
        purchaseAndPrice.setLayoutY(20);
        purchaseAndPrice.getStylesheets().add("file:src/main/java/view/css/cartMenu.css");

        //--------

        dataOutputStream.writeUTF("get_total_price_" + token);
        dataOutputStream.flush();
        totalPrice = Long.parseLong(dataInputStream.readUTF());
        Label totalPriceLabel = new Label("Total price: $" + totalPrice);
        totalPriceLabel.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 18;-fx-text-fill: #353535");

        dataOutputStream.writeUTF("get_final_price_" + token);
        dataOutputStream.flush();
        finalPrice = Long.parseLong(dataInputStream.readUTF());
        Label finaTotalPriceLabel = new Label("Total price: $" + finalPrice);
//        Label finaTotalPrice = new Label("Total price: $" + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
        finaTotalPriceLabel.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 18;-fx-text-fill: green");

        Label offPrice = new Label("Off price: $" + (totalPrice - finalPrice));
//        Label offPrice = new Label("Off price: $" + (BuyerManager.getTotalPrice() - BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart())));
        offPrice.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 18;-fx-text-fill: red");

        purchase = new JFXButton("Purchase");
        if (totalPrice == 0) {
            purchase.setDisable(true);
        }
        purchase.getStyleClass().add("purchase");
        purchase.setOnMouseClicked(event -> {
            try {
                if (main.onlineAccount.getUsername().equals("temp")) {
                    new Login(mainPane, btnLogin, btnAuction, btnOnlineSupport, btnCartMenu, mainMenu, main, socket, onlineAccount).popupLogin(null);
                } else {
                    paymentPopup();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        purchaseAndPrice.getChildren().addAll(totalPriceLabel, offPrice, finaTotalPriceLabel, purchase);

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

        dataOutputStream.writeUTF("getGoodInCart");
        dataOutputStream.flush();
        Type goodInCartType = new TypeToken<ArrayList<Good>>() {
        }.getType();
        ArrayList<Good> goodInCart = new Gson().fromJson(dataInputStream.readUTF(), goodInCartType);

        for (Good good : goodInCart) {
            ImageView goodImage = new ImageView(new Image("file:" + good.getImagePath()));
            goodImage.setFitWidth(150);
            goodImage.setFitHeight(150);
            goodImage.getStyleClass().add("goodImage");

            Rectangle line = new Rectangle(110, 1);
            line.setStyle("-fx-fill: #e8e8e8");

            VBox productFields = new VBox(labelForCartField(good.getName(), "Name: "),
                    labelForCartField(good.getCompany(), "Company: "), labelForCartField("" + good.getPrice(), "Price: $"), line);

            HBox productBox = new HBox(goodImage, productFields);
            productFields.getChildren().add(increaseOrDecreaseNumber(totalPriceLabel, finaTotalPriceLabel, offPrice, good, productBox, products));
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

    private HBox increaseOrDecreaseNumber(Label totalPriceLabel, Label finalTotalPriceLabel, Label offPrice, Good good, HBox productBox, VBox products) {
        final int[] count = {good.getGoodsInBuyerCart().get(onlineAccount.getUsername())};
        TextField number = new TextField();
        number.setAlignment(Pos.TOP_CENTER);
        number.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 12;");
        number.setPadding(new Insets(0, 0, 10, 0));
        number.setPrefSize(50, 30);
        number.setStyle("-fx-background-color: none");
        number.setText("" + good.getGoodsInBuyerCart().get(onlineAccount.getUsername()));
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
                try {
                    dataOutputStream.writeUTF("putInMapGoodsInBuyerCart_" + count[0] + "_" + good.getId());
                    dataOutputStream.flush();

                    dataOutputStream.writeUTF("get_total_price_" + token);
                    dataOutputStream.flush();
                    totalPrice = Long.parseLong(dataInputStream.readUTF());
                    dataOutputStream.writeUTF("get_final_price_" + token);
                    dataOutputStream.flush();
                    finalPrice = Long.parseLong(dataInputStream.readUTF());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
//                good.getGoodsInBuyerCart().put(onlineAccount.getUsername(), count[0]);
                totalPriceLabel.setText("Total price: " + totalPrice);
                finalTotalPriceLabel.setText("Final price: $" + finalPrice);
                offPrice.setText("Off price: $" + (totalPrice - finalPrice));
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
            try {
                dataOutputStream.writeUTF("putInMapGoodsInBuyerCart_" + count[0] + "_" + good.getId());
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
//            good.getGoodsInBuyerCart().put(onlineAccount.getUsername(), count[0]);
            if (count[0] == 1) {
                minus.getStyleClass().add("minesCart2");
            }
            if (count[0] == 0) {
                try {
                    dataOutputStream.writeUTF("removeInMapGoodsInBuyerCart_" + good.getId());
                    dataOutputStream.flush();
                    dataOutputStream.writeUTF("removeInBuyerCart_" + good.getId());
                    products.getChildren().remove(productBox);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            try {
                dataOutputStream.writeUTF("get_total_price_" + token);
                dataOutputStream.flush();
                totalPrice = Long.parseLong(dataInputStream.readUTF());
                dataOutputStream.writeUTF("get_final_price_" + token);
                dataOutputStream.flush();
                finalPrice = Long.parseLong(dataInputStream.readUTF());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (totalPrice == 0) {
                purchase.setDisable(true);
            }
            totalPriceLabel.setText("Total price: " + totalPrice);
            finalTotalPriceLabel.setText("Final price: $" + finalPrice);
//            finalTotalPriceLabel.setText("Final price price: $" + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
            offPrice.setText("Off price: $" + (totalPrice - finalPrice));
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
                discountCode(), existenceOfDiscount(), payment, totalPrice(), confirmButton(), payWithBankAccount(), payWithCredit(),
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
//        Label totalPrice = new Label("Total price: $ " + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
        Label totalPrice = new Label("Total price: $" + finalPrice);
        totalPrice.setStyle("-fx-text-fill: #ffdf00;-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 14pt");
        totalPrice.setLayoutX(90);
        totalPrice.setLayoutY(389);
//        totalPrice.setAlignment(Pos.CENTER);
        return totalPrice;
    }

    private Label payableAmount() {
//        finalTotalPrice = BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart());
        finalTotalPrice = finalPrice;
        payableAmount = new Label("Payable amount: $" + ((long) finalTotalPrice));
        payableAmount.setStyle("-fx-text-fill: #ffdf00;-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 14pt");
        payableAmount.getStyleClass().add("totalPrice");
        payableAmount.setLayoutX(90);
        payableAmount.setLayoutY(415);
//        payableAmount.setAlignment(Pos.CENTER);
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
        confirmButton.setLayoutY(460);
        confirmButton.getStyleClass().add("confirm");
        confirmButton.setOnMouseClicked(event -> processPurchase());
        return confirmButton;
    }

    private Button payWithBankAccount() {
        bankAccountButton = new Button();
        bankAccountButton.setAlignment(Pos.CENTER);
        bankAccountButton.setPrefSize(150, 65);
        ImageView imageView = new ImageView(new Image("file:src/main/java/view/image/banklogo.png"));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        bankAccountButton.setLayoutX(90);
        bankAccountButton.setLayoutY(450);
        bankAccountButton.getStyleClass().add("confirm");
        bankAccountButton.setGraphic(imageView);
        bankAccountButton.setOnMouseClicked(event -> {
            try {
                processPaymentWithBankAccount(discount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bankAccountButton.setVisible(false);
        return bankAccountButton;
    }

    private Button payWithCredit() {
        creditButton = new Button();
        creditButton.setPrefSize(150, 65);
        creditButton.setLayoutX(250);
        creditButton.setLayoutY(450);
        creditButton.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image("file:src/main/java/view/image/creditlogo.png"));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        creditButton.setGraphic(imageView);

        creditButton.getStyleClass().add("confirm");
        creditButton.setOnMouseClicked(event -> {
            try {
                processPaymentWithCredit(discount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        creditButton.setVisible(false);
        return creditButton;
    }

//    private void processIncreaseCredit() throws IOException {
//        if (bankAccountButton.isVisible()) {
//            bankAccountButton.setVisible(false);
//            creditField = new ZipCode();
//            creditField.setPromptText("credit");
//            creditField.setPrefSize(150, 55);
//            creditField.setLayoutX(90);
//            creditField.setLayoutY(470);
//            creditField.getStyleClass().add("paymentFields");
//            paymentPane.getChildren().add(creditField);
//        } else {
//            dataOutputStream.writeUTF("increase_credit_" + creditField.getText());
//            dataOutputStream.flush();
//            error.setText("your credit increased");
//            creditField.setVisible(false);
//            bankAccountButton.setVisible(true);
//        }
//    }

    private void processPurchase() {
        if ((discountCode.getText().length() == 0 && existenceOfDiscount.isSelected()) || zipCode.getText().length() == 0 || phoneNumberField.getText().length() == 0 ||
                nameField.getText().length() == 0 || addressField.getText().length() == 0) {
            error.setText("you must fill all the fields");
        } else if (existenceOfDiscount.isSelected()) {
            try {
                dataOutputStream.writeUTF("get_discount_" + ((int) Long.parseLong(discountCode.getText())));
                dataOutputStream.flush();
                Type discountType = new TypeToken<Discount>() {
                }.getType();
                discount = new Gson().fromJson(dataInputStream.readUTF(), discountType);
                Date currentDate = new Date();
                if (discount == null) {
                    error.setText("discount does not exist");
                } else if (discount.getStartDate().after(currentDate)) {
                    error.setText("It is not yet time to use the discount code");
                } else if (discount.getEndDate().before(currentDate)) {
                    error.setText("The discount code has expired");
                } else if (((Buyer) onlineAccount).getDiscountAndNumberOfAvailableDiscount().get(discount.getCode()) == null) {
                    error.setText("you cannot use the discount anymore");
                } else {
                    error.setText("");
                    dataOutputStream.writeUTF("getFinalTotalPrice_" + discount.getCode());
                    dataOutputStream.flush();
                    finalTotalPrice = Long.parseLong(dataInputStream.readUTF());
                    payableAmount.setText("payable amount: " + ((long) finalTotalPrice));
                    confirmButton.setVisible(false);
                    bankAccountButton.setVisible(true);
                    creditButton.setVisible(true);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            error.setText("");
            existenceOfDiscount.setDisable(true);
            confirmButton.setVisible(false);
            bankAccountButton.setVisible(true);
            creditButton.setVisible(true);
        }
    }

    private void processPaymentWithCredit(Discount discount) throws IOException {
        dataOutputStream.writeUTF("can_pay_" + finalTotalPrice);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        if (response.equals("true")) {
            if (discount == null) {
                dataOutputStream.writeUTF("payWithCredit_" + ((long) finalTotalPrice) + "_" + 0);
            } else {
                dataOutputStream.writeUTF("payWithCredit_" + ((long) finalTotalPrice) + "_" + discount.getCode());
            }
            dataOutputStream.flush();
            paymentPopup.close();
            fade(0.5, 10);
            backToMainMenu();
        } else {
            error.setText("your credit is not enough");
        }
    }

    private void processPaymentWithBankAccount(Discount discount) throws IOException {
        if (discount == null) {
            dataOutputStream.writeUTF("payWithBankAccount_" + ((long) finalTotalPrice) + "_" + 0);
        } else {
            dataOutputStream.writeUTF("payWithBankAccount_" + ((long) finalTotalPrice) + "_" + discount.getCode());
        }
        dataOutputStream.flush();
        paymentPopup.close();
        fade(0.5, 10);
        backToMainMenu();
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
