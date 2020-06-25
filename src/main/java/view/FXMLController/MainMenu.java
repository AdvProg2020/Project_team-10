package view.FXMLController;

import controller.AccountManager;
import controller.FileHandler;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Admin;
import model.Buyer;
import model.BuyerLog;
import model.Good;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button btnLogin;
    public AnchorPane mainPane;
    public FlowPane flowPane;
    public ScrollPane mainMenuScrollPane = new ScrollPane();
    public Rectangle header;
    public Button selectedButton = new Button("The most visited");
    public Button btnCartMenu;
    private URL location;
    private ResourceBundle resources;


    public void exit(MouseEvent mouseEvent) {
        FileHandler.write();
        Platform.exit();
    }

    public void minimize(MouseEvent mouseEvent) {
        ((Stage) ((Button) mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    public static void fadeEffect(Node object) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(200));
        fadeTransition.setFromValue(8);
        fadeTransition.setToValue(10);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setNode(object);
        fadeTransition.play();
    }

    public void popupLogin(MouseEvent mouseEvent) throws IOException {
        new Login(mainPane, btnLogin, btnCartMenu).popupLogin(mouseEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        HBox hBox = new HBox();
        if (AccountManager.getOnlineAccount() instanceof Buyer) {
            btnCartMenu.setVisible(true);
        }
        ImageView imageSort = new ImageView(new Image("file:src/main/java/view/image/sorticon.png"));
        imageSort.setFitWidth(25);
        imageSort.setFitHeight(25);
        Label sort = new Label("Sort by:");
        sort.setStyle("-fx-font-size: 15px;-fx-text-fill: black;-fx-font-family: sans-serif;");

        hBox.getChildren().addAll(imageSort, sort, buttonForSort("Time", location, resources), buttonForSort("Score", location, resources),
                buttonForSort("Price(Descending)", location, resources), buttonForSort("The most visited", location, resources));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 580, 10, 15));
        hBox.setSpacing(10);
        flowPane.getChildren().add(hBox);
        Collections.sort(GoodsManager.getFilteredGoods());
        for (Good good : GoodsManager.getFilteredGoods()) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(297);
            vBox.setPrefHeight(350);
            vBox.getStyleClass().add("vBoxInMainMenu");
            ImageView logoImage = new ImageView(new Image("file:src/main/java/view/image/logo.png"));
            logoImage.setFitHeight(190);
            logoImage.setFitWidth(190);
            Label name = new Label(good.getName());
            Label price = new Label("$" +good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            vBox.setOnMouseEntered(event -> fadeEffect(vBox));
            logoImage.setOnMouseClicked(event -> {
                GoodsManager.setCurrentGood(good);
                mainPane.getChildren().remove(mainMenuScrollPane);
                new GoodMenu(mainPane).changePane();
            });
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(logoImage, name, price, visit);
            flowPane.getChildren().add(vBox);
        }

        flowPane.setStyle("-fx-background-color: white;");
        mainMenuScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Login.currentPane = mainMenuScrollPane;
    }

    public Button buttonForSort(String input, URL location, ResourceBundle resources) {
        Button button = new Button(input);
        button.getStyleClass().add("buttonSort");
        if (input.equals("Time")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("time");
                sort(location, resources);
            });
            if (selectedButton.getText().equals("Time")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else if (input.equals("Score")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("score");
                sort(location, resources);
            });
            if (selectedButton.getText().equals("Score")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else if (input.startsWith("Price")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("price");
                sort(location, resources);
            });
            if (selectedButton.getText().startsWith("Price")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                GoodsManager.setKindOfSort("visit number");
                sort(location, resources);
            });
            if (selectedButton.getText().startsWith("The")) {
                button.getStyleClass().add("buttonSort-select");
            }
        }
        return button;
    }

    public void sort(URL location, ResourceBundle resources) {
        flowPane.getChildren().clear();
        initialize(location, resources);
    }

    public void cartMenu(MouseEvent mouseEvent) {
        mainPane.getChildren().remove(mainMenuScrollPane);
        new CartMenu(mainPane, btnCartMenu).changePane();
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        if (!mainPane.getChildren().contains(mainMenuScrollPane)) {
            mainPane.getChildren().removeIf(child -> child instanceof ScrollPane);
            flowPane.getChildren().clear();
            initialize(location, resources);
            mainPane.getChildren().add(mainMenuScrollPane);
        }
    }
}
