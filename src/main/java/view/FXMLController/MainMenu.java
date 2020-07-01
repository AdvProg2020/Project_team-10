package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import controller.AccountManager;
import controller.FileHandler;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro8.JMetro;
import model.*;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button btnLogin;
    public AnchorPane mainPane;
    public AnchorPane mainMenu;
    public FlowPane flowPane;
    public ScrollPane mainMenuScrollPane = new ScrollPane();
    public Rectangle header;
    public Button selectedButton = new Button("The most visited");
    public Button btnCartMenu;
    public URL location;
    public ResourceBundle resources;
    public MainMenu main;
    public JFXToggleButton offFilterButton;
    public JFXToggleButton availableFilterButton;
    public VBox vBoxForAddCompanyFilter;
    public VBox vBoxForAddCategoryFilter;
    public boolean updateFilters = true;
    public RangeSlider rangeSlider;
    public Label startPrice;
    public Label endPrice;


    void setValue(Label label, Number number) {
        String v = String.format("%d",number.intValue());
        label.setText(v);
    }


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
        new Login(mainPane, btnLogin, btnCartMenu, mainMenu, main).popupLogin(mouseEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rangeSlider.lowValueProperty().addListener(
                (observable, oldValue, newValue) -> setValue(startPrice, newValue)
        );

        rangeSlider.highValueProperty().addListener(
                (observable, oldValue, newValue) -> setValue(endPrice, newValue)
        );


        if (updateFilters) {
            updateAllFilter();
        }

        flowPane.getChildren().clear();
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
//        sort.setStyle("-fx-font-size: 15px;-fx-text-fill: black;-fx-font-family: sans-serif;");

        hBox.getChildren().addAll(imageSort, sort, buttonForSort("Time", location, resources),
                buttonForSort("Score", location, resources),
                buttonForSort("Price(Descending)", location, resources),
                buttonForSort("The most visited", location, resources));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 580, 10, 15));
        hBox.setSpacing(10);
        flowPane.getChildren().add(hBox);
//        filter();
        Collections.sort(GoodsManager.getFilteredGoods());
        for (Good good : GoodsManager.getFilteredGoods()) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(297);
            vBox.setPrefHeight(350);
            vBox.getStyleClass().add("vBoxInMainMenu");
            ImageView logoImage = new ImageView(new Image("file:" + good.getImagePath()));
            logoImage.setFitHeight(190);
            logoImage.setFitWidth(190);
            logoImage.getStyleClass().add("goodImage");
            Label name = new Label(good.getName());
            Label price = new Label("$" + good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            visit.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 12;-fx-text-fill: #0084ff;-fx-font-weight: bold;");
            ImageView eye = new ImageView(new Image("file:src/main/java/view/image/eye.png"));
            eye.setFitHeight(15);
            eye.setFitWidth(15);
            visit.setGraphic(eye);

            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            vBox.setOnMouseEntered(event -> fadeEffect(vBox));
            logoImage.setOnMouseClicked(event -> {
                GoodsManager.setCurrentGood(good);
                mainPane.getChildren().remove(mainMenu);
                new GoodMenu(mainPane).changePane();
            });

            HBox visitAndOff = new HBox(5);
            visitAndOff.setPadding(new Insets(45 , 0, 0,15));
            visitAndOff.getChildren().add(visit);
            if (good.getOffId() != 0) {
                Off off = Shop.getShop().getOffWithId(good.getOffId());
                Label offLabel = new Label( off.getPercent() + "%");
                offLabel.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 12;-fx-text-fill: red;-fx-font-weight: bold;");
                ImageView offImage = new ImageView(new Image("file:src/main/java/view/image/off.png"));
                offImage.setFitWidth(15);
                offImage.setFitHeight(15);
                offLabel.setGraphic(offImage);
                visitAndOff.getChildren().add(offLabel);
            }


            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(logoImage, name, price, covertScoreToStar((int) good.calculateAverageRate()),visitAndOff);
            flowPane.getChildren().add(vBox);
        }
        mainMenuScrollPane.getStyleClass().add("scroll-bar");
        flowPane.setStyle("-fx-background-color: white;");
        mainMenuScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Login.currentPane = mainMenu;
        main = this;
    }

    private void updateAllFilter() {
        rangeSlider.setHighValue(50000.0);
        rangeSlider.setLowValue(0.0);

        vBoxForAddCategoryFilter.getChildren().clear();
        vBoxForAddCompanyFilter.getChildren().clear();
        GoodsManager.getFilteredCatogories().clear();
        GoodsManager.getFilteredCompanies().clear();
        for (Category category : Shop.getShop().getAllCategories()) {
            JFXButton categoryFiltered = new JFXButton("● "+category.getName());
            vBoxForAddCategoryFilter.getChildren().add(categoryFiltered);
            categoryFiltered.setStyle("-fx-font-family:'Franklin Gothic Medium Cond';" + "-fx-font-size: 14pt;" + "-fx-text-fill: #8c8c8c");
        }
        for (String company : Shop.getShop().allCompanies()) {
            JFXCheckBox companyFiltered = new JFXCheckBox(company);
            companyFiltered.setOnAction(event -> {
                if (companyFiltered.isSelected()) {
                    GoodsManager.getFilteredCompanies().add(company);
                } else {
                    GoodsManager.getFilteredCompanies().remove(company);
                }
                filter();
            });
            companyFiltered.getStyleClass().add("filterButton");
            vBoxForAddCompanyFilter.getChildren().add(companyFiltered);
            companyFiltered.setStyle("-fx-font-family:'Franklin Gothic Medium Cond';" + "-fx-font-size: 14pt;" + "-fx-text-fill: #8c8c8c");
        }
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
        mainPane.getChildren().remove(Login.currentPane);
        new CartMenu(mainPane, btnCartMenu, btnLogin, main, mainMenu).changePane();
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        if (!mainPane.getChildren().contains(mainMenu)) {
            updateFilters = true;
            mainPane.getChildren().remove(Login.currentPane);
            initialize(location, resources);
            mainPane.getChildren().add(mainMenu);
        }
    }

    public void filterByOff(MouseEvent mouseEvent) {
        if (offFilterButton.isSelected()) {
            GoodsManager.getKindOfFilter().put("onlyOffs", "onlyOffs");
        } else {
            GoodsManager.getKindOfFilter().remove("onlyOffs");
        }
        filter();
    }

    public void filterByAvailability(MouseEvent mouseEvent) {
        if (availableFilterButton.isSelected()) {
            GoodsManager.getKindOfFilter().put("onlyAvailable", "onlyAvailable");
        } else {
            GoodsManager.getKindOfFilter().remove("onlyAvailable");
        }
        filter();
    }

    private HBox covertScoreToStar(int score) {
        ImageView star = new ImageView(new Image("file:src/main/java/view/image/5starblock.png"));
        star.setFitHeight(21);
        star.setFitWidth(100);
        ImageView star1 = new ImageView(new Image("file:src/main/java/view/image/1star.png"));
        star1.setFitHeight(21);
        star1.setFitWidth(100);
        ImageView star2 = new ImageView(new Image("file:src/main/java/view/image/2star.png"));
        star2.setFitHeight(21);
        star2.setFitWidth(100);
        ImageView star3 = new ImageView(new Image("file:src/main/java/view/image/3star.png"));
        star3.setFitHeight(21);
        star3.setFitWidth(100);
        ImageView star4 = new ImageView(new Image("file:src/main/java/view/image/4star.png"));
        star4.setFitHeight(21);
        star4.setFitWidth(100);
        ImageView star5 = new ImageView(new Image("file:src/main/java/view/image/5star.png"));
        star5.setFitHeight(21);
        star5.setFitWidth(100);
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        if (score == 0) {
            box.getChildren().add(star);
        } else if (score == 1) {
            box.getChildren().add(star1);
        } else if (score == 2) {
            box.getChildren().add(star2);
        } else if (score == 3) {
            box.getChildren().add(star3);
        } else if (score == 4) {
            box.getChildren().add(star4);
        } else if (score == 5) {
            box.getChildren().add(star5);
        }
        return box;
    }

    @FXML
    private void filter() {
        GoodsManager.getFilteredGoods().clear();
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        if (GoodsManager.getFilteredCompanies().size() == 0) {
            GoodsManager.getFilteredGoods().addAll(Shop.getShop().getAllGoods());
        }
        for (String filteredCompany : GoodsManager.getFilteredCompanies()) {
            for (Good good : Shop.getShop().getAllGoods()) {
                if (good.getCompany().equals(filteredCompany)) {
                    GoodsManager.getFilteredGoods().add(good);
                }
            }
        }
        if (GoodsManager.getKindOfFilter().containsKey("onlyOffs")) {
            for (Good good : GoodsManager.getFilteredGoods()) {
                if (good.getOffId() == 0) {
                    shouldBeRemoved.add(good);
                }
            }
        }
        if (GoodsManager.getKindOfFilter().containsKey("onlyAvailable")) {
            for (Good good : GoodsManager.getFilteredGoods()) {
                if (good.getNumber() <= 0) {
                    shouldBeRemoved.add(good);
                }
            }
        }
        long min = Long.parseLong(startPrice.getText());
        long max = Long.parseLong(endPrice.getText());
        if (min != 0 || max != 50000) {
            for (Good filteredGood : GoodsManager.getFilteredGoods()) {
                if (filteredGood.getPrice() < min || filteredGood.getPrice() > max) {
                    shouldBeRemoved.add(filteredGood);
                }
            }
        }
        GoodsManager.getFilteredGoods().removeAll(shouldBeRemoved);
        updateFilters = false;
        initialize(location, resources);
    }

}
