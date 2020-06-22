package view.FXMLController;

import controller.AccountManager;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Comment;
import model.Good;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.*;

public class GoodPage implements Initializable{
    public AnchorPane mainPane;
    public ScrollPane scrollPane;
    public AnchorPane innerPane;
    public Label isAvailable;
    public Label productName;
    public Label productPrice;
    public Button addToCart;
    public Button btnLogin;
    public FlowPane popupUser;
    private Good currentGood = GoodsManager.getCurrentGood();
    private Button user;



    public void exit(MouseEvent mouseEvent) {
        new MainMenu().exit(mouseEvent);
    }

    public void minimize(MouseEvent mouseEvent) {
        ((Stage) ((Button) mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    public static void fadeEffect(Node object) {
        DoubleProperty opacity = object.opacityProperty();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(200), new KeyValue(opacity, 1.5))
        );
        fadeIn.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ImageView goodImage = new ImageView(new Image("file:" + currentGood.getImagePath()));
        goodImage.setFitWidth(500);
        goodImage.setFitHeight(500);
        goodImage.setLayoutX(50);
        goodImage.setLayoutY(100);
        innerPane.getChildren().add(goodImage);
        if (currentGood.getNumber() > 0) {
            isAvailable.setText("Available");
        } else {
            isAvailable.setText("Not available");
            addToCart.setDisable(true);
        }
        productName.setText(currentGood.getName());
        productPrice.setText("" + currentGood.getPrice());
        mainPane.getStylesheets().add("file:/D:/java/Project_team-10/src/main/java/view/css/mainMenu.css");
        tabPane();
    }

    private void tabPane() {
        TabPane tabPane = new TabPane();
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
        innerPane.getChildren().add(productFields);
        return productFields;
    }

    private VBox comments() {
        VBox comments = new VBox();
        for (Comment comment : currentGood.getComments()) {
            Label commentLabel = new Label(comment.toString());
            comments.getChildren().add(commentLabel);
        }
        comments.getStyleClass().add("productFields");
        comments.setLayoutX(100);
        comments.setLayoutY(950);
        innerPane.getChildren().add(comments);
        return comments;
    }

    private void increaseAndDecrease() {
        final int[] count = {0};
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setPrefSize(60, 45);
        textField.setText("" + count[0]);
        textField.setDisable(true);
        Button plusButton = new Button("+");
        plusButton.setPrefSize(45, 45);
        plusButton.setOnMouseClicked(event -> {
            count[0] += 1;
            textField.setText("" + count[0]);
        });
        Button minusButton = new Button("-");
        minusButton.setPrefSize(45, 45);
        minusButton.setOnMouseClicked(event -> {
            if (count[0] != 0) {
                count[0] -= 1;
                textField.setText("" + count[0]);
            }
        });
        plusButton.getStyleClass().add("increaseOrDecrease");
        minusButton.getStyleClass().add("increaseOrDecrease");
        HBox increaseAndDecrease = new HBox();
        increaseAndDecrease.getChildren().addAll(minusButton, textField, plusButton);
        increaseAndDecrease.setLayoutX(645);
        increaseAndDecrease.setLayoutY(443);
        innerPane.getChildren().addAll(increaseAndDecrease);
    }

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        switchScene(mainMenuURL, mouseEvent.getPickResult().getIntersectedNode());
//        Stage stage = ((Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow());
//        stage.setScene(new Scene(MainMenu.mainMenuPane));
//        stage.show();
    }







}

