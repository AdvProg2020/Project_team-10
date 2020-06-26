package view.FXMLController;

import controller.AccountManager;
import controller.GoodsManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import model.*;

import static view.FXMLController.MainMenu.fadeEffect;

public class SellerPanel {

    private AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane sellerPane;
    private Button selectedButton;
    private ScrollPane sellerScrollPane;
    private MainMenu main;
    private AnchorPane mainMenu;
    private Button user;
    private Button btnLogin;

    public SellerPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu, Button user, Button btnLogin) {
        this.mainPane = mainPane;
        this.main = main;
        this.mainMenu = mainMenu;
        this.btnLogin = btnLogin;
        this.user = user;
        sellerPane = new AnchorPane();
        optionsPane = new AnchorPane();
        sellerScrollPane = new ScrollPane();
        selectedButton = new Button("Profile");
        handelButtonOnMouseClick();
    }

    public void changePane() {
        sellerPane.setLayoutY(165);
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
                createButton("Manage offs", "src/main/java/view/image/AdminPanel/users"),
                createButton("Manage products", "src/main/java/view/image/AdminPanel/product"),
                createButton("Manage requests", "src/main/java/view/image/AdminPanel/request"),
                createButton("Category", "src/main/java/view/image/AdminPanel/category"),
                rectangleDown, createButton("Log out", "src/main/java/view/image/AdminPanel/logout"));
        vBox.setStyle("-fx-background-color: none;");
        vBox.setPadding(new Insets(10, 10, 10, 8));


        optionsPane.setStyle("-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: #e1e1e1;"
                + "-fx-border-radius: 10;" + "-fx-background-radius: 10");
        optionsPane.getChildren().addAll(vBox);
        sellerPane.getChildren().add(optionsPane);
        optionsPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        Login.currentPane = sellerPane;
        mainPane.getChildren().add(sellerPane);

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

        sellerPane.getChildren().remove(sellerScrollPane);
        sellerScrollPane.setPrefSize(1150, 620);
        sellerScrollPane.getStyleClass().add("scroll-bar");
        sellerScrollPane.setLayoutX(330);
        sellerScrollPane.setLayoutY(35);
        Account currentAccount = AccountManager.getOnlineAccount();

        if (selectedButton.getText().equals("Profile")) {
            FlowPane flowPane = new FlowPane();
            flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
            flowPane.setPrefWidth(1200);
            flowPane.setPrefHeight(420);
            flowPane.setPadding(new Insets(50, 0, 10, 70));
            flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
            flowPane.getChildren().addAll(createItemOfProfile("Username:", currentAccount.getUsername()),
                    createItemOfProfile("Full name:", currentAccount.getFirstName() + " " + currentAccount.getLastName()),
                    createItemOfProfile("Phone number:", currentAccount.getPhoneNumber()),
                    createItemOfProfile("Email:", currentAccount.getEmail()));
            sellerScrollPane.setContent(flowPane);
            sellerPane.getChildren().add(sellerScrollPane);
            sellerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            sellerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        } else if (selectedButton.getText().equals("Manage offs")) {
//            sellerScrollPane.setContent();
            sellerPane.getChildren().add(sellerScrollPane);
        } else if (selectedButton.getText().equals("Manage products")) {
            sellerScrollPane.setContent(handelManageProduct());
            sellerPane.getChildren().add(sellerScrollPane);
        } else if (selectedButton.getText().equals("Log out")) {
            AccountManager.setOnlineAccount(new Buyer("temp"));
            user.setVisible(false);
            btnLogin.setVisible(true);
            backToMainMenu();
        }
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

    private FlowPane handelManageProduct() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        for (Good good : ((Seller) AccountManager.getOnlineAccount()).getGoods()) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(225);
            vBox.setPrefHeight(350);
            vBox.getStyleClass().add("vBoxInMainMenu");
            ImageView logoImage = new ImageView(new Image("file:src/main/java/view/image/logo.png"));
            logoImage.setFitHeight(170);
            logoImage.setFitWidth(170);
            Label name = new Label(good.getName());
            Label price = new Label("$" + good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            vBox.setOnMouseEntered(event -> fadeEffect(vBox));
            logoImage.setOnMouseClicked(event -> {
                GoodsManager.setCurrentGood(good);
                new GoodMenu(mainPane).changePane();
            });
            vBox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView();
            imageView.getStyleClass().add("imageViewRecy");
            imageView.setFitWidth(31);
            imageView.setFitHeight(31);
            HBox hBox = new HBox(imageView);
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPrefWidth(230);
            hBox.setPadding(new Insets(0, 20, 0, 0));
            vBox.getChildren().addAll(logoImage, name, price, visit, hBox);
            imageView.setOnMouseClicked(e -> {
                Shop.getShop().getAllGoods().remove(good);
                GoodsManager.getFilteredGoods().remove(good);
                ((Seller) AccountManager.getOnlineAccount()).getGoods().remove(good);
                flowPane.getChildren().remove(vBox);
            });
            flowPane.getChildren().add(vBox);
        }

        return flowPane;
    }


    public void backToMainMenu() {
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }

}
