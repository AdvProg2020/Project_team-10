package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AccountManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.*;
import view.NumberField;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;

import static view.FXMLController.MainMenu.fadeEffect;

public class SupporterPanel {
    public AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane adminPane;
    private Button selectedButton = new Button("Profile");
    private ImageView imageViewSelectedButton;
    private ScrollPane adminScrollPane = new ScrollPane();
    private MainMenu main;
    private AnchorPane mainMenu;
    private Button user;
    private Button btnLogin;
    //edit
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phoneNumber;
    private PasswordField password;
    //network
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Account onlineAccount;
    //chat
    private Map<String, FlowPane> buyerToChatPane = new HashMap<>();
    private HBox selectedBox;

    private boolean isManageChatsClicked;

    public SupporterPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu, Button user, Button btnLogin
            , Socket socket, Account onlineAccount) throws IOException {
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
                createButton("Manage chats", "src/main/java/view/image/AdminPanel/request"),
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

    private void processEdit() throws IOException {
        handelButtonOnMouseClick();
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

    public Button createButton(String text, String style) {
        ImageView imageView = new ImageView(new Image("file:" + style + ".png"));
        ImageView imageViewHover = new ImageView(new Image("file:" + style + "Hover.png"));
        imageViewHover.setFitWidth(30);
        imageViewHover.setFitHeight(30);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Button button = new Button(text);
        button.setPrefSize(240, 25);
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
            try {
                if (isManageChatsClicked) {
                    dataOutputStream.writeUTF("disconnect_supporter");
                    dataOutputStream.flush();
                }
                handelButtonOnMouseClick();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    private void handelButtonOnMouseClick() throws IOException {
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
        Account account = onlineAccount;

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
                        createItemOfProfile("Email:", account.getEmail()), hyperLink);
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
            case "Manage chats":
                adminScrollPane.setContent(handelManageChat());
                adminPane.getChildren().add(adminScrollPane);
                adminScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                isManageChatsClicked = true;
                break;
            case "Log out":
                dataOutputStream.writeUTF("logout");
                dataOutputStream.flush();
                onlineAccount = (new Buyer("temp"));
                main.onlineAccount = onlineAccount;
                user.setVisible(false);
                btnLogin.setVisible(true);
                backToMainMenu();
                break;
        }
        adminScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private Node handelManageChat() throws IOException {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-barInD");
        scrollPane.setPrefSize(300, 540);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox buyerItemBox = new VBox();
        buyerItemBox.setPrefSize(200, 530);
        buyerItemBox.setStyle("-fx-border-width: 1;-fx-border-color: #eeeeee");
        scrollPane.setContent(buyerItemBox);
        flowPane.getChildren().addAll(scrollPane);
        dataOutputStream.writeUTF("get_supporter_" + onlineAccount.getUsername());
        dataOutputStream.flush();
        Type supporterType = new TypeToken<Supporter>() {
        }.getType();
        String json = dataInputStream.readUTF();
        System.out.println("json: " + json);
        Supporter supporter = new Gson().fromJson(json, supporterType);
        this.onlineAccount = supporter;

        SupporterReceiver supporterReceiver = new SupporterReceiver(dataInputStream, this, buyerToChatPane);

        for (String buyerUsername : supporter.getBuyersToMessages().keySet()) {
            dataOutputStream.writeUTF("get_buyer_" + buyerUsername);
            dataOutputStream.flush();
            System.out.println(buyerUsername);
            Type buyerType = new TypeToken<Buyer>() {
            }.getType();
            String buyerJson;
            buyerJson = dataInputStream.readUTF();
            System.out.println("buyer Json : " + buyerJson);
            Buyer buyer = new Gson().fromJson(buyerJson, buyerType);
            HBox buyerBox = new HBox(3);
            buyerBox.getStyleClass().add("boxChat");
            buyerBox.setAlignment(Pos.CENTER_LEFT);
            buyerBox.setPadding(new Insets(6, 6, 6, 6));

            Circle circle = new Circle(20);
            ImagePattern pattern = new ImagePattern(new Image("file:" + buyer.getImagePath()));
            circle.setFill(pattern);
            circle.setStrokeWidth(2);
            circle.setStroke(Color.rgb(16, 137, 255));

            Label username = new Label(" " + buyer.getUsername());
            username.setPrefWidth(285);
            username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 14pt;-fx-text-fill: #1089FF");

            buyerBox.setOnMouseClicked(event -> {
                if (selectedBox != null) {
                    selectedBox.setStyle("-fx-background-color: white");
                }
                buyerBox.setStyle("-fx-background-color: #f0f0f0");
                selectedBox = buyerBox;
                for (String userBuyer : buyerToChatPane.keySet()) {
                    flowPane.getChildren().remove(buyerToChatPane.get(userBuyer));
                }
                FlowPane paneForChat;
                if (buyerToChatPane.containsKey(buyer.getUsername())) {
                    paneForChat = buyerToChatPane.get(buyer.getUsername());
                    flowPane.getChildren().add(paneForChat);
                } else {
                    paneForChat = new FlowPane();
                    try {
                        handelMouseClickChat(buyer, flowPane, paneForChat);
                        buyerToChatPane.put(buyer.getUsername(), paneForChat);
                        supporterReceiver.setBuyerToChatPane(buyerToChatPane);
                        dataOutputStream.writeUTF("page_" + buyerUsername);
                        dataOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            buyerBox.getChildren().addAll(circle, username);
            buyerItemBox.getChildren().addAll(buyerBox, rectangle(285, 1));
        }

        supporterReceiver.start();

        return flowPane;
    }

    private void handelMouseClickChat(Buyer buyer, FlowPane flowPane, FlowPane paneForChat) throws IOException {
        paneForChat.setVgap(3);
        paneForChat.setStyle("-fx-background-color: white;-fx-border-width: 1;-fx-border-radius: 10;-fx-border-color: #eeeeee;");
        paneForChat.setPrefSize(700, 540);
        flowPane.getChildren().addAll(paneForChat);
        paneForChat.setPadding(new Insets(15, 15, 15, 15));

        HBox boxOfSupporter = new HBox();
        boxOfSupporter.setAlignment(Pos.CENTER_LEFT);
        boxOfSupporter.setPrefWidth(690);
        Circle circle = new Circle(20);
        ImagePattern pattern = new ImagePattern(new Image("file:" + buyer.getImagePath()));
        circle.setFill(pattern);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.rgb(16, 137, 255));

        Label username = new Label(" " + buyer.getUsername());
        username.setPrefWidth(250);
        username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 17pt;-fx-text-fill: #1089FF");
        boxOfSupporter.getChildren().addAll(circle, username);

        ScrollPane scrollPaneChat = new ScrollPane();
        scrollPaneChat.setPrefSize(690, 430);
        paneForChat.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        scrollPaneChat.getStyleClass().add("scroll-barInDiscount");

        HBox sendAndChatField = new HBox();
        TextField chatField = new TextField();
        chatField.getStyleClass().add("chatField");
        chatField.setPromptText("Massage");
        chatField.setPrefSize(600, 30);
        Button send = new Button("Send");
        send.getStyleClass().add("send");
        VBox innerChat = new VBox(5);
        send.setPrefSize(90, 30);

        send.setOnAction(event -> {
            try {
                sendMessage(scrollPaneChat, chatField, innerChat, buyer.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        chatField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendMessage(scrollPaneChat, chatField, innerChat, buyer.getUsername());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
        showOldMessages(buyer, innerChat, scrollPaneChat);
        scrollPaneChat.setContent(innerChat);
        sendAndChatField.getChildren().addAll(chatField, send);

        paneForChat.setAlignment(Pos.CENTER);
        paneForChat.getChildren().addAll(boxOfSupporter, rectangle(690, 1), scrollPaneChat, sendAndChatField);

    }

    private void sendMessage(ScrollPane scrollPaneChat, TextField chatField, VBox innerChat, String buyerUsername) throws IOException {
        String message = chatField.getText();
        dataOutputStream.writeUTF("from_supporter_to_" + buyerUsername + "_" + message);
        dataOutputStream.flush();
        showMessage(innerChat, message, "-fx-background-color: #efefef;-fx-text-fill: black;-fx-background-radius: 5;");
        chatField.clear();
        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
    }

    public void showMessage(VBox innerChat, String message, String style) {
        int a = message.length() / 80;
        if (a != 0) {
            for (int i = 0; i < a; i++) {
                message = insertString(message, "\n", (i + 1) * 80);
            }
        }
        VBox messageBox = new VBox(3);
        Label label = new Label(message);
        label.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-text-fill: black;");
        messageBox.setPadding(new Insets(5, 5, 5, 5));
        Date date = new Date();
        Label time = new Label(date.getHours() + ":" + date.getMinutes() + "");
        time.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-text-fill: #9f9f9f;-fx-font-size: 6pt");
        messageBox.setStyle(style);
        messageBox.getChildren().addAll(label, time);
        innerChat.getChildren().add(messageBox);
    }

    public static String insertString(String originalString, String stringToBeInserted, int index) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < originalString.length(); i++) {
            newString.append(originalString.charAt(i));
            if (i == index) {
                newString.append(stringToBeInserted);
            }
        }
        return newString.toString();
    }

    private void showOldMessages(Buyer buyer, VBox innerChat, ScrollPane scrollPaneChat) throws IOException {
        for (String message : ((Supporter) onlineAccount).getBuyersToMessages().get(buyer.getUsername())) {
            showMessage(innerChat, message, "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;");
        }
        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
        dataOutputStream.writeUTF("clear_messages_of_" + buyer.getUsername());
        dataOutputStream.flush();
    }

    private Rectangle rectangle(int x, int y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(y);
        rectangle.setWidth(x);
        rectangle.setStyle("-fx-fill: #dadada");
        return rectangle;
    }

    private FlowPane handelCategory() throws IOException {
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

        dataOutputStream.writeUTF("getAllCategories");
        dataOutputStream.flush();
        Type allCategoriesType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> categories = new Gson().fromJson(dataInputStream.readUTF(), allCategoriesType);

        for (Category category : categories) {
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
                try {
                    dataOutputStream.writeUTF("remove_category_" + category.getName());
                    dataOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                flowPane.getChildren().remove(hBox);
            });
        }

        return flowPane;

    }

    private FlowPane handelManageProduct() throws IOException {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        dataOutputStream.writeUTF("getAllGoods");
        dataOutputStream.flush();
        Type allGoodsType = new TypeToken<ArrayList<Good>>() {
        }.getType();
        ArrayList<Good> goods = new Gson().fromJson(dataInputStream.readUTF(), allGoodsType);

        for (Good good : goods) {
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
                try {
                    dataOutputStream.writeUTF("remove_product_" + good.getId());
                    dataOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                flowPane.getChildren().remove(vBox);
            });
            flowPane.getChildren().add(vBox);
        }

        return flowPane;
    }

    private FlowPane handelManageUsers() throws IOException {
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

        dataOutputStream.writeUTF("getAllSellers");
        dataOutputStream.flush();
        Type allSellerType = new TypeToken<ArrayList<Seller>>() {
        }.getType();
        ArrayList<Account> sellers = new Gson().fromJson(dataInputStream.readUTF(), allSellerType);

        dataOutputStream.writeUTF("getAllAdmins");
        dataOutputStream.flush();
        Type allAdminType = new TypeToken<ArrayList<Admin>>() {
        }.getType();
        ArrayList<Account> admins = new Gson().fromJson(dataInputStream.readUTF(), allAdminType);

        dataOutputStream.writeUTF("getAllBuyers");
        dataOutputStream.flush();
        Type allBuyerType = new TypeToken<ArrayList<Buyer>>() {
        }.getType();
        ArrayList<Account> buyer = new Gson().fromJson(dataInputStream.readUTF(), allBuyerType);

        dataOutputStream.writeUTF("getAllSupporters");
        dataOutputStream.flush();
        Type allSupporterType = new TypeToken<ArrayList<Supporter>>() {
        }.getType();
        ArrayList<Account> supporters = new Gson().fromJson(dataInputStream.readUTF(), allSupporterType);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.addAll(buyer);
        accounts.addAll(admins);
        accounts.addAll(sellers);
        accounts.addAll(supporters);

        for (Account account : accounts) {
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
                try {
                    dataOutputStream.writeUTF("remove_account_" + new Gson().toJson(account));
                    dataOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

    private FlowPane handelDiscounts() throws IOException {
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

        dataOutputStream.writeUTF("getAllDiscounts");
        dataOutputStream.flush();
        Type allDiscountsType = new TypeToken<ArrayList<Discount>>() {
        }.getType();
        ArrayList<Discount> discounts = new Gson().fromJson(dataInputStream.readUTF(), allDiscountsType);

        for (Discount discount : discounts) {
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
                try {
                    dataOutputStream.writeUTF("remove_discount_" + discount.getCode());
                    dataOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

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

    public void backToMainMenu() {
        main.updateFilters = true;
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }

}
