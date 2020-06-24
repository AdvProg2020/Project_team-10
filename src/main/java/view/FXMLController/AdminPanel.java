package view.FXMLController;

import com.jfoenix.controls.JFXTabPane;
import controller.AccountManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Account;
import model.Shop;

public class AdminPanel {
    public AnchorPane mainPane;
    public AnchorPane adminPane;
    private Button selectedButton = new Button("Profile");

    public AdminPanel(AnchorPane mainPane) {
        this.mainPane = mainPane;
        handelButtonOnMouseClick();
        selectedButton.setGraphic(new ImageView(new Image("file:src/main/java/view/image/AdminPanel/userAdminHover.png")));
    }

    public void changePane() {
        adminPane = new AnchorPane();
        adminPane.setLayoutY(200);
        adminPane.setLayoutX(30);
        adminPane.setPrefSize(250, 520);


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


        adminPane.setStyle("-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: #e1e1e1;"
                + "-fx-border-radius: 10;" + "-fx-background-radius: 10");
        adminPane.getChildren().addAll(vBox);
        mainPane.getChildren().add(adminPane);
        adminPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
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

        button.setOnMouseClicked(e -> {
            selectedButton.setGraphic(imageView);
            selectedButton = button;
            button.setGraphic(imageViewHover);
            handelButtonOnMouseClick();
        });
//        button.setOnMouseEntered(event -> {
//            if ()
//        });
        return button;
    }

    private ScrollPane adminPaneScroll = new ScrollPane();

    private void handelButtonOnMouseClick() {

        mainPane.getChildren().remove(adminPaneScroll);
        adminPaneScroll.setPrefSize(1150, 620);
        adminPaneScroll.getStyleClass().add("scroll-bar");
        adminPaneScroll.setLayoutX(330);
        adminPaneScroll.setLayoutY(200);
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
            adminPaneScroll.setContent(flowPane);
            mainPane.getChildren().add(adminPaneScroll);
            adminPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        } else if (selectedButton.getText().equals("Manage users")) {
            adminPaneScroll.setContent(handelManageUsers());
            mainPane.getChildren().add(adminPaneScroll);
        }
        adminPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private FlowPane handelManageUsers() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
        for (Account allAccount : Shop.getShop().getAllAccounts()) {
            HBox hBox = new HBox(100);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(60);
            Label label = new Label(allAccount.getUsername());
            label.setPrefWidth(150);
            label.getStyleClass().add("labelUsernameInProfile");
            Label label1 = new Label("  " + allAccount.getEmail());
            Rectangle rectangle = new Rectangle(2, 60);
            rectangle.setStyle("-fx-fill: #d5d5d5");
            label1.setGraphic(rectangle);
            label1.setPrefWidth(600);
            label1.getStyleClass().add("labelUsernameInProfile");
            ImageView imageView = new ImageView();
            imageView.getStyleClass().add("imageView");
            imageView.setFitWidth(31);
            imageView.setFitHeight(25);
            hBox.getChildren().addAll(label, label1, imageView);
            flowPane.getChildren().add(hBox);
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

}
