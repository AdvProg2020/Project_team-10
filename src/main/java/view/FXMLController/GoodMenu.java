package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import controller.AccountManager;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Comment;
import model.Good;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.nio.file.Paths;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.loginURL;

public class GoodMenu {
    public AnchorPane mainPane;
    public ScrollPane goodPageScrollPane;
    private Button addComment;
    private Stage popupWindow;
    TextField title;
    TextArea content;

    public GoodMenu(AnchorPane mainPane) {
        this.mainPane = mainPane;

    }

    public void changePane() {
        Good currentGood = GoodsManager.getCurrentGood();
        currentGood.setVisitNumber(currentGood.getVisitNumber() + 1);

        AnchorPane innerPane = new AnchorPane();

        goodPageScrollPane = new ScrollPane(innerPane);
        goodPageScrollPane.setLayoutY(164);
        goodPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        goodPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        ImageView goodImage = new ImageView(new Image("file:" + currentGood.getImagePath()));

        Label isAvailable = new Label();
        isAvailable.getStyleClass().add("availableLabel");
        isAvailable.setLayoutX(700);
        isAvailable.setLayoutY(140);

        Label productName = new Label();
        productName.getStyleClass().add("productNameLabel");
        productName.setLayoutX(700);
        productName.setLayoutY(190);

        Label productPrice = new Label();
        productPrice.getStyleClass().add("priceLabel");
        productPrice.setLayoutX(700);
        productPrice.setLayoutY(250);

        JFXButton addToCart = new JFXButton("Add To Cart");
        addToCart.getStyleClass().add("addToCartButton");
        addToCart.setLayoutX(700);
        addToCart.setLayoutY(430);

        innerPane.getChildren().addAll(goodImage, productName, productPrice, isAvailable, addToCart);
        goodImage.setFitWidth(500);
        goodImage.setFitHeight(500);
        goodImage.setLayoutX(50);
        goodImage.setLayoutY(100);
        if (currentGood.getNumber() > 0) {
            isAvailable.setText("Available");
        } else {
            isAvailable.setText("Not available");
            addToCart.setDisable(true);
        }
        productName.setText(currentGood.getName());
        productPrice.setText("$" + currentGood.getPrice());
        mainPane.getStylesheets().add("file:src/main/java/view/css/goodPage.css");
        tabPane(innerPane);
        mainPane.getChildren().add(goodPageScrollPane);
        innerPane.setPrefSize(1520, 699);
        Login.currentPane = goodPageScrollPane;

    }

    private void tabPane(AnchorPane innerPane) {
        JFXTabPane tabPane = new JFXTabPane();
        Tab productFieldsTab = new Tab("productFields", productFields());
        productFieldsTab.setClosable(false);
        productFieldsTab.getStyleClass().add("tabs");
        Tab commentsTab = new Tab("comments", comments());
        commentsTab.setClosable(false);
        commentsTab.getStyleClass().add("tabs");
        tabPane.getTabs().addAll(productFieldsTab, commentsTab);
        tabPane.setLayoutX(50);
        tabPane.setLayoutY(600);
        innerPane.getChildren().add(tabPane);
    }

    private VBox productFields() {
        Good currentGood = GoodsManager.getCurrentGood();
        VBox productFields = new VBox();
        Label seller = new Label("seller: " + currentGood.getSellerUsername());
        Label company = new Label("company: " + currentGood.getCompany());
        Label category = new Label("category: " + currentGood.getCategory());
        VBox categoryAttributes = new VBox();
        for (String attribute : currentGood.getCategoryAttribute().keySet()) {
            Label categoryAttribute = new Label(attribute + ": " + currentGood.getCategoryAttribute().get(attribute));
            categoryAttributes.getChildren().add(categoryAttribute);
        }
        Label description = new Label("description: " + currentGood.getDescription());
        productFields.getChildren().addAll(seller, company, category, categoryAttributes, description);
        productFields.getStyleClass().add("productFields");
        productFields.setLayoutX(100);
        productFields.setLayoutY(650);
        return productFields;
    }

    private VBox comments() {
        Good currentGood = GoodsManager.getCurrentGood();
        VBox comments = new VBox();
        for (Comment comment : currentGood.getComments()) {
            VBox commentVBox = new VBox(250);
            Rectangle rectangleTop = new Rectangle(240, 2);
            rectangleTop.setFill(Color.rgb(225, 0, 225));
            Rectangle rectangleDown = new Rectangle(240, 2);
            rectangleDown.setFill(Color.rgb(225, 0, 225));
            Label commentLabel = new Label(comment.toString());
            commentVBox.getChildren().add(commentLabel);
            comments.getChildren().addAll(commentVBox, rectangleDown, rectangleTop);
        }
        comments.getStyleClass().add("productFields");
        comments.getChildren().add(addComment());
        comments.setLayoutX(100);
        comments.setLayoutY(950);
        return comments;
    }


    private void popupComment(){
        AnchorPane commentPane = new AnchorPane();
        commentPane.getStylesheets().add("file:src/main/java/view/css/loginMenu.css");
        popupWindow = new Stage();
        title = new TextField();
        content = new TextArea();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        commentPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        commentPane.setPrefWidth(480);
        commentPane.setPrefHeight(580);

        fade(10, 0.5);

        layout.setLayoutX(500);
        layout.setLayoutY(150);
        layout.getChildren().add(commentPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);
        commentPane.getChildren().addAll(title(), content(), submit(), exit());
        popupWindow.showAndWait();




    }

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    private Button exit() {
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

    private Button addComment() {
        addComment = new Button();
        addComment.setText("Add");
        addComment.setPrefSize(290, 55);
        addComment.setLayoutX(100);
        addComment.setLayoutY(370);
        addComment.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                popupComment();
            }
        });

        return addComment;
    }

    private Button submit() {
        Button submit = new Button();
        submit.setText("Submit");
        submit.setPrefSize(290, 55);
        submit.setLayoutX(100);
        submit.setLayoutY(370);
        submit.getStyleClass().add("login");
        submit.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                popupWindow.close();
                fade(0.5, 10);
                GoodsManager.getCurrentGood().getComments().add(new Comment(AccountManager.getOnlineAccount(), GoodsManager.getCurrentGood().getId(),
                        "title : " + title.getText() + "\n" + "content : " + content.getText()));
            }
        });
        return submit;
    }

    private TextField title() {
        title.setPromptText("title");
        title.setLayoutX(100);
        title.setLayoutY(150);
        title.setPrefHeight(50);
        title.setPrefWidth(290);
        title.getStyleClass().add("typeField");
        return title;
    }

    private TextArea content() {
        content.setPromptText("content");
        content.setLayoutX(100);
        content.setLayoutY(230);
        content.setPrefHeight(50);
        content.setPrefWidth(290);
        return content;
    }

}
