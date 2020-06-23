package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import controller.GoodsManager;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Good;

public class GoodMenu {
    public AnchorPane mainPane;
    public ScrollPane goodPageScrollPane;

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
            Label commentLabel = new Label(comment.toString());
            comments.getChildren().add(commentLabel);
        }
        comments.getStyleClass().add("productFields");
        comments.setLayoutX(100);
        comments.setLayoutY(950);
        return comments;
    }



}
