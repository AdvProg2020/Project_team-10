package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import controller.AccountManager;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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
import model.Admin;
import model.Buyer;
import model.Comment;
import model.Good;
import view.NumberField;

import java.net.URL;
import java.nio.file.Paths;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.loginURL;

public class GoodMenu {
    private AnchorPane mainPane;
    public ScrollPane goodPageScrollPane;
    private Button addComment;
    private Stage popupWindow;
    private TextField title;
    private TextArea content;
    private NumberField scoreField;
    private Label error;

    public GoodMenu(AnchorPane mainPane) {
        this.mainPane = mainPane;
    }

    public void changePane() {
        Good currentGood = GoodsManager.getCurrentGood();
        currentGood.setVisitNumber(currentGood.getVisitNumber() + 1);
        AnchorPane scrollPack = new AnchorPane();
        scrollPack.setStyle("-fx-background-radius: 10;-fx-background-color: white;-fx-border-width: 1;-fx-border-color: #E3E3E3;-fx-border-radius: 10");
        scrollPack.setLayoutY(190);
        scrollPack.setLayoutX(30);
        scrollPack.setPrefSize(1480 , 650);

        AnchorPane innerPane = new AnchorPane();
        innerPane.setStyle("-fx-background-color: white;-fx-background-radius: 10");
        goodPageScrollPane = new ScrollPane(innerPane);
        goodPageScrollPane.setLayoutY(5);
        goodPageScrollPane.setLayoutX(5);
        goodPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        goodPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        goodPageScrollPane.getStyleClass().add("scroll-bar");

        ImageView goodImage = new ImageView(new Image("file:" + currentGood.getImagePath()));

        Rectangle hLine = new Rectangle(2 , 500);
        hLine.setStyle("-fx-fill: #eaeaea");
        hLine.setLayoutX(570);
        hLine.setLayoutY(50);

        Rectangle vLine = new Rectangle(500 , 2);
        vLine.setStyle("-fx-fill: #eaeaea");
        vLine.setLayoutX(590);
        vLine.setLayoutY(140);

        Button isAvailable = new Button();
        isAvailable.getStyleClass().add("availableLabel");
        isAvailable.setLayoutX(600);
        isAvailable.setLayoutY(60);
        isAvailable.setPrefSize(30 , 10);

        Label productName = new Label();
        productName.getStyleClass().add("productNameLabel");
        productName.setLayoutX(600);
        productName.setLayoutY(10);

        Label productPrice = new Label();
        productPrice.getStyleClass().add("priceLabel");
        productPrice.setLayoutX(600);
        productPrice.setLayoutY(300);

        JFXButton addToCart = new JFXButton("Add To Cart");
        addToCart.getStyleClass().add("addToCartButton");
        addToCart.setLayoutX(600);
        addToCart.setLayoutY(430);
        addToCart.setOnMouseClicked(event -> {
            addToCart.setDisable(true);
            ((Buyer) AccountManager.getOnlineAccount()).getCart().add(currentGood);
            currentGood.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount().getUsername(), 1);
        });

        ImageView rate = new ImageView();
        rate.setLayoutY(62);
        rate.setLayoutX(705);
        rate.setFitHeight(25);
        rate.setFitWidth(25);
        rate.getStyleClass().add("rate");
        rate.setOnMouseClicked(event -> popupScore());

        if (!(AccountManager.getOnlineAccount() instanceof Buyer) || ((Buyer) AccountManager.getOnlineAccount()).getCart().contains(currentGood)) {
            addToCart.setDisable(true);
        }
        if (!canScore()) {
            rate.setDisable(true);
        }

        innerPane.getChildren().addAll(goodImage, productName, productPrice, isAvailable, addToCart, rate, hLine,vLine);
        goodImage.setFitWidth(500);
        goodImage.setFitHeight(500);
        goodImage.setLayoutX(50);
        goodImage.setLayoutY(50);
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
        scrollPack.getChildren().add(goodPageScrollPane);
        mainPane.getChildren().add(scrollPack);
        innerPane.setPrefSize(1470, 640);
        Login.currentPane = scrollPack;

    }

    private boolean canScore() {
        if (!(AccountManager.getOnlineAccount() instanceof Buyer)) {
            return false;
        } else {
            for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getGoods()) {
                if (good.getId() == GoodsManager.getCurrentGood().getId()) {
                    return true;
                }
            }
            return false;
        }
    }

    private void popupScore() {
        AnchorPane scorePane = new AnchorPane();
        scorePane.getStylesheets().add("file:src/main/java/view/css/loginMenu.css");
        popupWindow = new Stage();
        scoreField = new NumberField();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        scorePane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        scorePane.setPrefWidth(220);
        scorePane.setPrefHeight(250);

        fade(10, 0.5);

        layout.setLayoutX(700);
        layout.setLayoutY(300);
        layout.getChildren().add(scorePane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);
        scorePane.getChildren().addAll(confirmScore(), exitPopupScore(), scoreField(), error());
        popupWindow.showAndWait();
    }

    private void tabPane(AnchorPane innerPane) {
        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefWidth(1360);

        Tab productFieldsTab = new Tab("Specifications", productFields());
        productFieldsTab.setClosable(false);
        productFieldsTab.getStyleClass().add("jfx-tab-pane");
        ImageView good = new ImageView(new Image("file:src/main/java/view/image/specifications.png"));
        good.setFitWidth(30);
        good.setFitHeight(30);
        productFieldsTab.setGraphic(good);

        Tab commentsTab = new Tab("Comments", comments());
        ImageView comment = new ImageView(new Image("file:src/main/java/view/image/comment.png"));
        comment.setFitWidth(30);
        comment.setFitHeight(30);
        commentsTab.setGraphic(comment);
        commentsTab.setClosable(false);
        commentsTab.getStyleClass().add("jfx-tab-pane");

        tabPane.getTabs().addAll(productFieldsTab, commentsTab);
        tabPane.setLayoutX(50);
        tabPane.setLayoutY(580);
        innerPane.getChildren().add(tabPane);
    }

    private VBox productFields() {
        Good currentGood = GoodsManager.getCurrentGood();
        HBox boxOneFiled = new HBox();
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

    private void popupComment() {
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

    private Button exitPopupScore() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExit");
        exitButton.setLayoutY(27);
        exitButton.setLayoutX(345);
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
        addComment.setStyle("-fx-font-size: 18pt; -fx-background-color: rgba(255,254,98,0.99); -fx-background-radius: 10%; -fx-border-radius: 10%; -fx-font-family: 'Franklin Gothic Medium Cond'");
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

    private Button confirmScore() {
        Button submit = new Button();
        submit.setText("Ok!");
        submit.setPrefSize(100, 50);
        submit.setLayoutX(250);
        submit.setLayoutY(190);
        submit.getStyleClass().add("login");
        submit.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (Integer.parseInt(scoreField.getText()) > 5 || Integer.parseInt(scoreField.getText()) < 0) {
                    error.setText("only between 0 - 5");
                    error.setStyle("-fx-text-fill: darkred");
                } else {
                    popupWindow.close();
                    fade(0.5, 10);
                    GoodsManager.getCurrentGood().getAllScores().add(Integer.parseInt(scoreField.getText()));
                }
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

    private NumberField scoreField() {
        scoreField.setPromptText("Score [0 - 5]");
        scoreField.setLayoutX(100);
        scoreField.setLayoutY(100);
        scoreField.setPrefHeight(50);
        scoreField.setPrefWidth(200);
        scoreField.getStyleClass().add("typeField");
        return scoreField;
    }

    private TextArea content() {
        content.setPromptText("content");
        content.setLayoutX(100);
        content.setLayoutY(230);
        content.setPrefHeight(50);
        content.setPrefWidth(290);
        return content;
    }

    private Label error() {
        error = new Label();
        error.setLayoutX(110);
        error.setLayoutY(160);
        return error;
    }


}
