package view.FXMLController;

import com.jfoenix.controls.JFXTabPane;
import controller.AccountManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class AdminPanel {
    public AnchorPane mainPane;
    public AnchorPane adminPane;

    public AdminPanel(AnchorPane mainPane) {
        this.mainPane = mainPane;
    }

    public void changePane() {
        adminPane = new AnchorPane();
        adminPane.setLayoutY(200);
        adminPane.setLayoutX(30);
        adminPane.setPrefSize(250, 500);


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

        Rectangle rectangle = new Rectangle(240, 2);
        rectangle.setFill(Color.rgb(225, 225, 225));
        vBox.getChildren().addAll(hBox, rectangle, createButton("Profile", "src/main/java/view/image/AdminPanel/userAdmin"),
                createButton("Mange users", "src/main/java/view/image/AdminPanel/users"),
                createButton("Manage products", "src/main/java/view/image/AdminPanel/product"),
                createButton("Manage requests", "src/main/java/view/image/AdminPanel/request"),
                createButton("Discounts", "src/main/java/view/image/AdminPanel/discount"),
                createButton("Category", "src/main/java/view/image/AdminPanel/category"),
                createButton("Log out", "src/main/java/view/image/AdminPanel/logout"));
        vBox.setStyle("-fx-background-color: none;");
        vBox.setPadding(new Insets(10, 10, 10, 8));


        adminPane.setStyle("-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: #e1e1e1;"
                + "-fx-border-radius: 10;" + "-fx-background-radius: 10");
        adminPane.getChildren().addAll(vBox);
        mainPane.getChildren().add(adminPane);
        adminPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
    }

    public Button createButton(String text, String image) {
        ImageView imageView = new ImageView(new Image("file:" + image + ".png"));
        ImageView imageViewHover = new ImageView(new Image("file:" + image + "Hover.png"));
        imageViewHover.setFitWidth(30);
        imageViewHover.setFitHeight(30);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Button button = new Button(text);
        button.setPrefSize(240, 25);
        button.getStyleClass().add("button");
        button.setGraphic(imageView);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setOnMouseEntered(e -> button.setGraphic(imageViewHover));
        button.setOnMouseExited(e -> button.setGraphic(imageView));
        return button;
    }


}
