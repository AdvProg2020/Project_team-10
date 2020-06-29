package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import controller.AccountManager;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    private TextField content;
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
        scrollPack.setPrefSize(1480, 650);

        AnchorPane innerPane = new AnchorPane();
        innerPane.setStyle("-fx-background-color: white;-fx-background-radius: 10");
        goodPageScrollPane = new ScrollPane(innerPane);
        goodPageScrollPane.setLayoutY(5);
        goodPageScrollPane.setLayoutX(5);
        goodPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        goodPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        goodPageScrollPane.getStyleClass().add("scroll-bar");

        ImageView goodImage = new ImageView(new Image("file:" + currentGood.getImagePath()));

        Rectangle hLine = new Rectangle(2, 500);
        hLine.setStyle("-fx-fill: #eaeaea");
        hLine.setLayoutX(570);
        hLine.setLayoutY(50);

        Rectangle vLine = new Rectangle(500, 2);
        vLine.setStyle("-fx-fill: #eaeaea");
        vLine.setLayoutX(590);
        vLine.setLayoutY(140);

        Button isAvailable = new Button();
        isAvailable.getStyleClass().add("availableLabel");
        isAvailable.setLayoutX(600);
        isAvailable.setLayoutY(60);
        isAvailable.setPrefSize(30, 10);

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

        innerPane.getChildren().addAll(goodImage, productName, productPrice, isAvailable, addToCart, rate, hLine, vLine);
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
        if (AccountManager.getOnlineAccount() instanceof Buyer) {
            for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getGoods()) {
                if (good.getId() == GoodsManager.getCurrentGood().getId()) {
                    return true;
                }
            }
        }
        return false;
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

    private Rectangle line() {
        Rectangle line = new Rectangle(2, 60);
        line.setStyle("-fx-fill: #d5d5d5");
        return line;
    }

    private VBox productFields() {

        VBox productFields = new VBox();
        productFields.getStylesheets().add("file:src/main/java/view/css/goodPage.css");

        Good currentGood = GoodsManager.getCurrentGood();
        HBox boxOneFiled = new HBox();
        Label sellerLabel = new Label(" Seller");
        sellerLabel.setPrefSize(150, 60);
        sellerLabel.getStyleClass().add("labelForDiscount");
        Label seller = new Label(" " + currentGood.getSellerUsername());
        seller.setPrefSize(1000, 60);
        seller.getStyleClass().add("labelForDiscount");
        boxOneFiled.getChildren().addAll(sellerLabel, line(), seller);
        boxOneFiled.getStyleClass().add("hbox");


        HBox boxTwoFiled = new HBox();
        boxTwoFiled.getStyleClass().add("hbox");
        Label companyLabel = new Label(" Company");
        companyLabel.setPrefSize(150, 60);
        companyLabel.getStyleClass().add("labelForDiscount");
        Label company = new Label(" " + currentGood.getCompany());
        company.setPrefSize(1000, 60);
        company.getStyleClass().add("labelForDiscount");
        boxTwoFiled.getChildren().addAll(companyLabel, line(), company);

        HBox boxTreeFiled = new HBox();
        boxTreeFiled.getStyleClass().add("hbox");
        Label categoryLabel = new Label(" category");
        categoryLabel.setPrefSize(150, 60);
        categoryLabel.getStyleClass().add("labelForDiscount");
        Label category = new Label(" " + currentGood.getCategory());
        category.setPrefSize(1000, 60);
        category.getStyleClass().add("labelForDiscount");
        boxTreeFiled.getChildren().addAll(categoryLabel, line(), category);

        productFields.getChildren().addAll(boxOneFiled, boxTwoFiled, boxTreeFiled);
        for (String attribute : currentGood.getCategoryAttribute().keySet()) {
            HBox boxAttribute = new HBox();
            boxAttribute.getStyleClass().add("hbox");
            Label label = new Label(" " + attribute);
            label.setPrefSize(150, 60);
            label.getStyleClass().add("labelForDiscount");
            Label categoryAttribute = new Label(" " + currentGood.getCategoryAttribute().get(attribute));
            categoryAttribute.setPrefSize(1000, 60);
            categoryAttribute.getStyleClass().add("labelForDiscount");
            boxAttribute.getChildren().addAll(label, line(), categoryAttribute);
            productFields.getChildren().addAll(boxAttribute);
        }
        HBox boxForeFiled = new HBox();
        boxForeFiled.getStyleClass().add("hbox");
        Label descriptionLabel = new Label(" Description");
        descriptionLabel.setPrefSize(150, 60);
        descriptionLabel.getStyleClass().add("labelForDiscount");
        Label description = new Label(" " + currentGood.getDescription());
        description.setPrefSize(1000, 60);
        description.getStyleClass().add("labelForDiscount");
        boxForeFiled.getChildren().addAll(descriptionLabel, line(), description);

        productFields.getChildren().addAll(boxForeFiled);
        productFields.getStyleClass().add("productFields");
        productFields.setLayoutX(100);
        productFields.setLayoutY(650);
        return productFields;
    }

    private VBox comments() {
        Good currentGood = GoodsManager.getCurrentGood();
        VBox comments = new VBox();
        for (Comment comment : currentGood.getComments()) {
            VBox commentVBox = new VBox(4);
            commentVBox.setPadding(new Insets(7,0,0,0));
            commentVBox.setPrefHeight(120);
            Label title = new Label("   " + comment.getTitle());
            title.getStyleClass().add("labelForDiscount");
            Rectangle rectangle = new Rectangle(280, 1);
            rectangle.setStyle("-fx-fill: #e7e7e7");
            Label commentLabel = new Label("   " + comment.getText());
            commentLabel.getStyleClass().add("labelForComment");
            commentVBox.getChildren().addAll(title, rectangle, commentLabel);
            commentVBox.getStyleClass().add("hbox");
            comments.getChildren().addAll(commentVBox);
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
        content = new TextField();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        commentPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        commentPane.setPrefWidth(430);
        commentPane.setPrefHeight(300);

        fade(10, 0.5);

        layout.setLayoutX(550);
        layout.setLayoutY(250);
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
        exitButton.setLayoutX(395);
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
        addComment.setOnMouseClicked(event -> popupComment());
        addComment.setText("ADD");
        addComment.setPrefSize(1450, 40);
        ImageView plus = new ImageView(new Image("file:src/main/java/view/image/plus.png"));
        plus.setFitWidth(30);
        plus.setFitHeight(30);
        addComment.getStyleClass().add("buttonComment");
        addComment.setGraphic(plus);
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
        submit.setPrefSize(300, 55);
        submit.setLayoutX(65);
        submit.setLayoutY(200);
        submit.getStyleClass().add("login");
        submit.setOnMouseClicked(event -> {
            popupWindow.close();
            fade(0.5, 10);
            GoodsManager.getCurrentGood().getComments().add(new Comment(AccountManager.getOnlineAccount().getUsername(), GoodsManager.getCurrentGood().getId(),
                    "title : " + title.getText() + "\n" + "content : " + content.getText()));
        submit.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                popupWindow.close();
                fade(0.5, 10);
                GoodsManager.getCurrentGood().getComments().add(new Comment(AccountManager.getOnlineAccount(),
                        GoodsManager.getCurrentGood().getId(), "" + content.getText(), "" + title.getText()));
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
        submit.setOnMouseClicked(event -> {
            if (Integer.parseInt(scoreField.getText()) > 5 || Integer.parseInt(scoreField.getText()) < 0) {
                error.setText("only between 0 - 5");
                error.setStyle("-fx-text-fill: darkred");
            } else {
                popupWindow.close();
                fade(0.5, 10);
                GoodsManager.getCurrentGood().getAllScores().add(Integer.parseInt(scoreField.getText()));
            }
        });
        return submit;
    }

    private TextField title() {
        title.setPromptText("Title");
        title.setLayoutX(65);
        title.setLayoutY(60);
        title.setPrefHeight(30);
        title.setPrefWidth(300);
        title.getStyleClass().add("fieldGoodPage");
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

    private TextField content() {
        content.setPromptText("Content");
        content.setLayoutX(65);
        content.setLayoutY(120);
        content.setPrefHeight(30);
        content.setPrefWidth(300);
        content.getStyleClass().add("fieldGoodPage");
        return content;
    }

    private Label error() {
        error = new Label();
        error.setLayoutX(110);
        error.setLayoutY(160);
        return error;
    }


}
