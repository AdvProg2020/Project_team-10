package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import org.controlsfx.control.RangeSlider;
import view.NumberField;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.adminPopupURL;

public class MainMenu implements Initializable {
    public Button btnLogin;
    public AnchorPane mainPane;
    public AnchorPane mainMenu;
    public FlowPane flowPane = new FlowPane();
    //    public ScrollPane mainMenuScrollPane = new ScrollPane();
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
    public Button btnOnlineSupport;
    public ScrollPane mainMenuScrollPane;
    public FlowPane flowPaneForBoxOfGoods;
    public Button btnAuction;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    public Account onlineAccount = new Buyer("temp");
    public String token;

    public ArrayList<Good> filteredGoods;
    private String kindOfSort = "visit number";
    private Map<String, String> kindOfFilter = new HashMap<>();
    private ArrayList<String> filteredCompanies = new ArrayList<>();

    public Pagination pagi;
    public ImageView gif;
    private List<String> list = new ArrayList<>();
    private int j = 0;
    double orgCliskSceneX, orgReleaseSceneX;
    private Button lbutton, rButton;
    private ImageView imageView;
    private EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgCliskSceneX = t.getSceneX();
        }
    };

    private AnchorPane paneForScroll = new AnchorPane();
    private ScrollPane scrollPaneForSelectChat = new ScrollPane();
    private FlowPane paneForChat = new FlowPane();

    public MainMenu() throws IOException {
        this.socket = new Socket("localhost", 6060);
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        filteredGoods = getAllProducts();
    }

    private ArrayList<Good> getAllProducts() throws IOException {
        dataOutputStream.writeUTF("getAllGoods");
        dataOutputStream.flush();
        String allProductsJson = dataInputStream.readUTF();
        Type productsListType = new TypeToken<ArrayList<Good>>() {
        }.getType();
        return new Gson().fromJson(allProductsJson, productsListType);
    }

    void setValue(Label label, Number number) {
        String v = String.format("%d", number.intValue());
        label.setText(v);
    }

    public void exit() throws IOException {
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
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
        new Login(mainPane, btnLogin, btnAuction, btnOnlineSupport, btnCartMenu, mainMenu, main, socket, onlineAccount).popupLogin(mouseEvent);
    }

    public FlowPane createPage(int pageIndex) {
        FlowPane box = new FlowPane();
        int page = pageIndex * 12;
        for (int i = page; i < page + 12; i++) {
            if (i >= filteredGoods.size()) {
                break;
            }
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(20, 5, 10, 5));
            vBox.setPrefWidth(295);
            vBox.setPrefHeight(320);
            vBox.getStyleClass().add("vBoxInMainMenu");
            ImageView logoImage = new ImageView(new Image("file:" + filteredGoods.get(i).getImagePath()));
            logoImage.setFitHeight(190);
            logoImage.setFitWidth(190);
            logoImage.getStyleClass().add("goodImage");
            Label name = new Label(filteredGoods.get(i).getName());
            Label price = new Label("$" + filteredGoods.get(i).getPrice() + "");
            Label visit = new Label(filteredGoods.get(i).getVisitNumber() + "");
            visit.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 12;-fx-text-fill: #0084ff;-fx-font-weight: bold;");
            ImageView eye = new ImageView(new Image("file:src/main/java/view/image/eye.png"));
            eye.setFitHeight(15);
            eye.setFitWidth(15);
            visit.setGraphic(eye);

            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            vBox.setOnMouseEntered(event -> fadeEffect(vBox));
            int finalI = i;
            logoImage.setOnMouseClicked(event -> {
                GoodMenu goodMenu = null;
                try {
                    goodMenu = new GoodMenu(mainPane, socket, onlineAccount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goodMenu.setCurrentGood(filteredGoods.get(finalI));
                mainPane.getChildren().remove(mainMenu);
                goodMenu.changePane();
            });

            HBox visitAndOff = new HBox(5);
            visitAndOff.setPadding(new Insets(45, 0, 0, 15));
            visitAndOff.getChildren().add(visit);
            if (filteredGoods.get(i).getOffId() != 0) {
                try {
                    dataOutputStream.writeUTF("get_off_" + filteredGoods.get(i).getOffId());
                    dataOutputStream.flush();
                    Type offType = new TypeToken<Off>() {
                    }.getType();
                    Off off = new Gson().fromJson(dataInputStream.readUTF(), offType);
                    Label offLabel = new Label(off.getPercent() + "%");
                    offLabel.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 12;-fx-text-fill: red;-fx-font-weight: bold;");
                    ImageView offImage = new ImageView(new Image("file:src/main/java/view/image/off.png"));
                    offImage.setFitWidth(15);
                    offImage.setFitHeight(15);
                    offLabel.setGraphic(offImage);
                    visitAndOff.getChildren().add(offLabel);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }


            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(logoImage, name, price, covertScoreToStar((int) filteredGoods.get(i).calculateAverageRate()), visitAndOff);

            box.getChildren().add(vBox);
        }
        return box;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (onlineAccount instanceof Buyer && !onlineAccount.getUsername().equals("temp")) {
            btnOnlineSupport.setVisible(true);
            btnAuction.setVisible(true);
        } else {
            btnOnlineSupport.setVisible(false);
            btnAuction.setVisible(false);
        }

        rangeSlider.lowValueProperty().addListener(
                (observable, oldValue, newValue) -> setValue(startPrice, newValue)
        );


        list.add("file:src/main/java/view/image/266661.jpg");
        list.add("file:src/main/java/view/image/2156097.jpg");
        list.add("file:src/main/java/view/image/112551619.jpg");


        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        lbutton = new Button("❮");
        lbutton.getStyleClass().add("btnSlid");
        rButton = new Button("❯");
        rButton.getStyleClass().add("btnSlid");

        Image[] images = new Image[list.size()];
        for (int i = 0; i < list.size(); i++) {
            images[i] = new Image(list.get(i));
        }

        imageView = new ImageView(images[j]);
        imageView.setCursor(Cursor.CLOSED_HAND);

        imageView.setOnMousePressed(circleOnMousePressedEventHandler);

        imageView.setOnMouseReleased(e -> {
            orgReleaseSceneX = e.getSceneX();
            if (orgCliskSceneX > orgReleaseSceneX) {
                lbutton.fire();
            } else {
                rButton.fire();
            }
        });

        rButton.setOnAction(e -> {
            j = j + 1;
            if (j == list.size()) {
                j = 0;
            }
            imageView.setImage(images[j]);

        });
        lbutton.setOnAction(e -> {
            j = j - 1;
            if (j == 0 || j > list.size() + 1 || j == -1) {
                j = list.size() - 1;
            }
            imageView.setImage(images[j]);

        });

        imageView.setFitHeight(110);
        imageView.setFitWidth(110);

        HBox hBox1 = new HBox();
        hBox1.setSpacing(15);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(lbutton, imageView, rButton);
        hBox1.setLayoutX(350);
        hBox1.setLayoutY(30);

        mainPane.getChildren().add(hBox1);

        gif.setImage(new Image("file:src/main/java/view/image/shopGif.gif"));

//        flowPane.setPrefSize(1100 , 600);
        pagi.setPadding(new Insets(45, 5, 0, 0));
        pagi.setPrefSize(1200, 1200);
        rangeSlider.lowValueProperty().addListener(
                (observable, oldValue, newValue) -> setValue(startPrice, newValue)
        );

        rangeSlider.highValueProperty().addListener(
                (observable, oldValue, newValue) -> setValue(endPrice, newValue)
        );


        if (updateFilters) {
            try {
                updateAllFilter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        flowPane.getChildren().clear();
        this.location = location;
        this.resources = resources;
        HBox hBox = new HBox();
        if (onlineAccount instanceof Buyer) {
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
        hBox.setPadding(new Insets(10, 10, 10, 8));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: white;-fx-background-radius: 10");
        hBox.setPrefWidth(1180);
        hBox.setLayoutY(27);
        hBox.setLayoutX(32.1);
        mainMenu.getChildren().add(hBox);
//        filter();
        pagi.setPageCount(21);
        pagi.setCurrentPageIndex(0);
        pagi.setMaxPageIndicatorCount(3);
        Collections.sort(filteredGoods);
        pagi.setPageFactory(this::createPage);
        flowPaneForBoxOfGoods.setPadding(new Insets(10, 0, 0, 0));

        //Speed For ScrollPane
        final double SPEED = 0.006;
        mainMenuScrollPane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            mainMenuScrollPane.setVvalue(mainMenuScrollPane.getVvalue() - deltaY);
        });

        flowPane.setStyle("-fx-background-color: white;");
        Login.currentPane = mainMenu;
        main = this;
    }

    private void updateAllFilter() throws IOException {
        rangeSlider.setHighValue(50000.0);
        rangeSlider.setLowValue(0.0);

        vBoxForAddCategoryFilter.getChildren().clear();
        vBoxForAddCompanyFilter.getChildren().clear();
//        GoodsManager.getFilteredCatogories().clear();
        dataOutputStream.writeUTF("getAllCategories");
        dataOutputStream.flush();
        filteredCompanies.clear();
        Type allCategoriesType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> categories = new Gson().fromJson(dataInputStream.readUTF(), allCategoriesType);
        for (Category category : categories) {
            JFXButton categoryFiltered = new JFXButton("● " + category.getName());
            vBoxForAddCategoryFilter.getChildren().add(categoryFiltered);
            categoryFiltered.setStyle("-fx-font-family:'Franklin Gothic Medium Cond';" + "-fx-font-size: 14pt;" + "-fx-text-fill: #8c8c8c");
        }
        dataOutputStream.writeUTF("getAllCompanies");
        dataOutputStream.flush();
        Type allCompaniesType = new TypeToken<Set<String>>() {
        }.getType();
        Set<String> allCompanies = new Gson().fromJson(dataInputStream.readUTF(), allCompaniesType);
        for (String company : allCompanies) {
            JFXCheckBox companyFiltered = new JFXCheckBox(company);
            companyFiltered.setOnAction(event -> {
                if (companyFiltered.isSelected()) {
                    filteredCompanies.add(company);
                } else {
                    filteredCompanies.remove(company);
                }
                try {
                    filter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                kindOfSort = "time";
                sort(location, resources);
            });
            if (selectedButton.getText().equals("Time")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else if (input.equals("Score")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                kindOfSort = "score";
                sort(location, resources);
            });
            if (selectedButton.getText().equals("Score")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else if (input.startsWith("Price")) {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                kindOfSort = "price";
                sort(location, resources);
            });
            if (selectedButton.getText().startsWith("Price")) {
                button.getStyleClass().add("buttonSort-select");
            }
        } else {
            button.setOnMouseClicked(event -> {
                selectedButton = button;
                kindOfSort = "visit number";
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

    public void cartMenu(MouseEvent mouseEvent) throws IOException {
        mainPane.getChildren().remove(Login.currentPane);
        new CartMenu(mainPane, btnCartMenu, btnLogin, btnAuction, btnOnlineSupport, main, mainMenu, socket, onlineAccount, token).changePane();
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        if (!mainPane.getChildren().contains(mainMenu)) {
            updateFilters = true;
            mainPane.getChildren().remove(Login.currentPane);
            initialize(location, resources);
            mainPane.getChildren().add(mainMenu);
        }
    }

    public void filterByOff(MouseEvent mouseEvent) throws IOException {
        if (offFilterButton.isSelected()) {
            kindOfFilter.put("onlyOffs", "onlyOffs");
        } else {
            kindOfFilter.remove("onlyOffs");
        }
        filter();
    }

    public void filterByAvailability(MouseEvent mouseEvent) throws IOException {
        if (availableFilterButton.isSelected()) {
            kindOfFilter.put("onlyAvailable", "onlyAvailable");
        } else {
            kindOfFilter.remove("onlyAvailable");
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
    private void filter() throws IOException {
        filteredGoods.clear();
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        dataOutputStream.writeUTF("getAllGoods");
        dataOutputStream.flush();
        Type allGoodsType = new TypeToken<ArrayList<Good>>() {
        }.getType();
        ArrayList<Good> allGoods = new Gson().fromJson(dataInputStream.readUTF(), allGoodsType);
        if (filteredCompanies.size() == 0) {
            filteredGoods.addAll(allGoods);
        }
        for (String filteredCompany : filteredCompanies) {
            for (Good good : allGoods) {
                if (good.getCompany().equals(filteredCompany)) {
                    filteredGoods.add(good);
                }
            }
        }
        if (kindOfFilter.containsKey("onlyOffs")) {
            for (Good good : filteredGoods) {
                if (good.getOffId() == 0) {
                    shouldBeRemoved.add(good);
                }
            }
        }
        if (kindOfFilter.containsKey("onlyAvailable")) {
            for (Good good : filteredGoods) {
                if (good.getNumber() <= 0) {
                    shouldBeRemoved.add(good);
                }
            }
        }
        long min = Long.parseLong(startPrice.getText());
        long max = Long.parseLong(endPrice.getText());
        if (min != 0 || max != 50000) {
            for (Good filteredGood : filteredGoods) {
                if (filteredGood.getPrice() < min || filteredGood.getPrice() > max) {
                    shouldBeRemoved.add(filteredGood);
                }
            }
        }
        filteredGoods.removeAll(shouldBeRemoved);
        updateFilters = false;
        initialize(location, resources);
    }

    public void popupOnlineSupport() throws IOException {
        scrollPaneForSelectChat.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        scrollPaneForSelectChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        paneForScroll.getChildren().clear();
        paneForScroll.setPrefSize(196, 210);
        scrollPaneForSelectChat.setPrefSize(189, 200);
        scrollPaneForSelectChat.getStyleClass().add("scroll-barInDiscount");
        scrollPaneForSelectChat.setLayoutY(5);
        scrollPaneForSelectChat.setLayoutX(5);
        paneForScroll.setStyle("-fx-background-color: white;-fx-border-radius: 10;-fx-border-width: 1;-fx-border-color: #dadada;-fx-background-radius: 10");
        paneForScroll.setLayoutY(148);
        paneForScroll.setLayoutX(1175);
        paneForScroll.getChildren().add(scrollPaneForSelectChat);
        dataOutputStream.writeUTF("getOnlineSupporters");
        dataOutputStream.flush();
        Type onlineSupportersType = new TypeToken<ArrayList<Supporter>>() {
        }.getType();
        ArrayList<Supporter> supporters = new Gson().fromJson(dataInputStream.readUTF(), onlineSupportersType);
        VBox boxOfOnlineSupport = new VBox(0);
        boxOfOnlineSupport.setAlignment(Pos.CENTER);
        boxOfOnlineSupport.setStyle("-fx-background-color: none");
        for (Supporter supporter : supporters) {
            HBox supporterBox = new HBox();
            supporterBox.setPrefHeight(38);
            supporterBox.setAlignment(Pos.CENTER);
            supporterBox.setStyle("-fx-background-color: none;");
            Button chat = new Button("Chat");
            chat.setOnMouseClicked(event -> {
                try {
                    handelMouseClickChat(supporter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            chat.getStyleClass().add("buttonChat");
            Label username = new Label(supporter.getUsername());
            username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-text-fill: black;-fx-font-size: 10pt");
            username.setPrefWidth(109);
            supporterBox.getChildren().addAll(username, chat);
            boxOfOnlineSupport.getChildren().addAll(supporterBox, rectangle(189, 1));
        }
        scrollPaneForSelectChat.setContent(boxOfOnlineSupport);

        if (!mainPane.getChildren().contains(paneForScroll)) {
            mainPane.getChildren().add(paneForScroll);
            paneForScroll.setOnMouseExited(event -> mainPane.getChildren().remove(paneForScroll));
        }
    }

    private Rectangle rectangle(int x, int y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(x);
        rectangle.setHeight(y);
        rectangle.setStyle("-fx-fill: #f5f5f5");
        return rectangle;
    }

    private void handelMouseClickChat(Supporter supporter) throws IOException {

        paneForChat.setVgap(3);
        paneForChat.setLayoutX(570);
        paneForChat.setLayoutY(200);
        paneForChat.setStyle("-fx-background-color: white;-fx-background-radius: 10;" +
                "-fx-border-width: 1;-fx-border-radius: 10;-fx-border-color: #eeeeee;");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1200.0);
        dropShadow.setHeight(1200);
        dropShadow.setWidth(1200);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        paneForChat.setEffect(dropShadow);
        paneForChat.setPrefSize(340, 390);
        mainPane.getChildren().addAll(paneForChat);

        dataOutputStream.writeUTF("get_supporter_" + supporter.getUsername());
        dataOutputStream.flush();
        Type supporterType = new TypeToken<Supporter>() {
        }.getType();
        supporter = new Gson().fromJson(dataInputStream.readUTF(), supporterType);

        dataOutputStream.writeUTF("add_to_buyers_" + supporter.getUsername() + "_" + onlineAccount.getUsername());
        dataOutputStream.flush();

        HBox boxOfSupporter = new HBox();
        boxOfSupporter.setAlignment(Pos.CENTER_LEFT);
        boxOfSupporter.setPrefWidth(320);
        Circle circle = new Circle(20);
        ImagePattern pattern = new ImagePattern(new Image("file:" + supporter.getImagePath()));
        circle.setFill(pattern);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.rgb(16, 137, 255));

        Label username = new Label(" " + supporter.getUsername());
        username.setPrefWidth(250);
        username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 17pt;-fx-text-fill: #1089FF");
        boxOfSupporter.getChildren().addAll(circle, username, exitButton(supporter.getUsername()));

        ScrollPane scrollPaneChat = new ScrollPane();
        scrollPaneChat.setPrefSize(320, 290);
        paneForChat.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        scrollPaneChat.getStyleClass().add("scroll-barInDiscount");

        HBox sendAndChatField = new HBox();
        TextField chatField = new TextField();
        chatField.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 10pt");
        chatField.setPromptText("Massage");
        chatField.setPrefSize(260, 30);
        Button send = new Button("Send");
        send.getStyleClass().add("send");
        VBox innerChat = new VBox(5);
        send.setPrefSize(60, 30);
        Supporter finalSupporter = supporter;
        send.setOnAction(event -> {
            try {
                sendMessage(scrollPaneChat, chatField, innerChat, finalSupporter.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        chatField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendMessage(scrollPaneChat, chatField, innerChat, finalSupporter.getUsername());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
        scrollPaneChat.setContent(innerChat);

        sendAndChatField.getChildren().addAll(chatField, send);

        paneForChat.setAlignment(Pos.CENTER);
        paneForChat.getChildren().addAll(boxOfSupporter, rectangle(320, 2), scrollPaneChat, sendAndChatField);

        new BuyerReceiver(dataInputStream, innerChat, scrollPaneChat, this).start();
    }

    private void sendMessage(ScrollPane scrollPaneChat, TextField chatField, VBox innerChat, String supporterUsername) throws IOException {
        String message = chatField.getText();
        dataOutputStream.writeUTF("from_buyer_" + onlineAccount.getUsername() + "_to_" + supporterUsername + "_" + message);
        dataOutputStream.flush();
//        dataOutputStream.writeUTF("update_messages_of_" + supporterUsername + "_" + onlineAccount.getUsername() + "_" + message);
//        dataOutputStream.flush();
        showMessage(innerChat, message, "-fx-background-color: #efefef;-fx-text-fill: black;-fx-background-radius: 5;");
        chatField.clear();
        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
    }

    public void showMessage(VBox innerChat, String message, String style) {
        int a = message.length() / 40;
        if (a != 0) {
            for (int i = 0; i < a; i++) {
                message = insertString(message, "\n", (i + 1) * 40);
            }
        }
        VBox messageBox = new VBox(3);
        Label label = new Label(message);
        label.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-text-fill: black;");
        messageBox.setPadding(new Insets(5, 5, 5, 5));
        Date date = new Date();
        Label time = new Label(date.getHours() + ":" + date.getMinutes() + "");
        time.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-text-fill: #9f9f9f;-fx-font-size: 6pt");
        messageBox.setStyle(style);
        messageBox.getChildren().addAll(label, time);
        innerChat.getChildren().add(messageBox);
    }

    public static String insertString(String originalString, String stringToBeInserted, int index) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < originalString.length(); i++) {
            newString.append(originalString.charAt(i));
            if (i == index) {
                newString.append(stringToBeInserted);
            }
        }
        return newString.toString();
    }

    private Button exitButton(String supporterUsername) {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExitPop");
        exitButton.setOnAction(event -> {
            try {
                dataOutputStream.writeUTF("disconnect_buyer_" + supporterUsername);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainPane.getChildren().remove(paneForChat);
            paneForChat.getChildren().clear();
        });
        return exitButton;
    }

    private AnchorPane auctionPane;
    private Stage popupWindow;

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    public void popupAuctions(MouseEvent mouseEvent) throws IOException {
        auctionPane = new AnchorPane();
        auctionPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        URL url = Paths.get(adminPopupURL).toUri().toURL();
        AnchorPane layout = FXMLLoader.load(url);
        Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        auctionPane.setStyle("-fx-background-color: #fbfffb;" + "-fx-background-radius: 30px;");
        auctionPane.setPrefWidth(680);
        auctionPane.setPrefHeight(580);

        fade(10, 0.5);

        layout.setLayoutX(360);
        layout.setLayoutY(150);

        layout.getChildren().add(auctionPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        showAuction();

        popupWindow.setScene(scene1);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);
        popupWindow.showAndWait();
    }

    private void showAuction() throws IOException {
        auctionPane.getChildren().clear();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(650, 538);
        scrollPane.setLayoutX(15);
        scrollPane.setLayoutY(32);
        scrollPane.getStyleClass().add("scroll-barInD");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        FlowPane flowPane = new FlowPane();
        flowPane.setPrefSize(650, 550);
        scrollPane.setContent(flowPane);

        dataOutputStream.writeUTF("getAllAuctions");
        dataOutputStream.flush();
        Type productsListType = new TypeToken<ArrayList<Auction>>() {
        }.getType();
        ArrayList<Auction> allAuctions = new Gson().fromJson(dataInputStream.readUTF(), productsListType);

        for (Auction auction : allAuctions) {
            VBox boxTextAndImage = new VBox(8);
            boxTextAndImage.setAlignment(Pos.CENTER);
            boxTextAndImage.setPadding(new Insets(8, 8, 8, 8));
            boxTextAndImage.setStyle("-fx-border-color: #e1e1e1;-fx-border-width: 1");
            ImageView imageView = new ImageView(new Image("file:" + auction.getGood().getImagePath()));
            imageView.setFitWidth(195);
            imageView.setFitHeight(195);
            Label label = new Label(auction.getGood().getName());
            label.setStyle("-fx-text-fill: black;-fx-font-size: 14pt;-fx-font-family: 'Franklin Gothic Medium Cond'");
            Button button = new Button("Participate in the auction");
            button.getStyleClass().add("auctionButton");
            button.setPrefSize(195, 30);
            button.setOnMouseClicked(event -> handelAuction(auction));

            boxTextAndImage.getChildren().addAll(imageView, label, button);
            flowPane.getChildren().add(boxTextAndImage);
        }

        auctionPane.getChildren().addAll(exitButtonPop(), scrollPane);
    }

    private Button exitButtonPop() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExitPop");
        exitButton.setLayoutY(15);
        exitButton.setLayoutX(645);
        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
    }

    private void handelAuction(Auction auction) {
        auctionPane.getChildren().clear();
        ImageView back = new ImageView();
        back.setFitHeight(25);
        back.setFitWidth(25);
        back.setLayoutX(10);
        back.setLayoutY(10);
        back.getStyleClass().add("imageViewBack");
        back.setOnMouseClicked(event -> {
            try {
                showAuction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox boxOfImageAndPrice = new VBox(5);
        boxOfImageAndPrice.setLayoutX(35);
        boxOfImageAndPrice.setLayoutY(50);
        boxOfImageAndPrice.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView("file:" + auction.getGood().getImagePath());
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Label name = new Label(auction.getGood().getName());
        name.setStyle("-fx-font-size: 16pt;-fx-font-family: 'Franklin Gothic Medium Cond';-fx-text-fill: black");

        Label currentPrice = new Label();
        currentPrice.setStyle("-fx-text-fill: #0069ff;-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 15pt");

        Label time = new Label("end time: " + auction.getEndDate());
        time.setStyle("-fx-text-fill: #3f3f3f;-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 10pt");
        time.setPadding(new Insets(0, 0, 130, 0));

        NumberField price = new NumberField();
        price.setPrefSize(200, 40);
        price.setPromptText("Proposed price");
        price.setStyle("-fx-background-color: none;-fx-text-fill: black;-fx-border-width: 2;" +
                "-fx-border-color: #0069ff;-fx-border-radius: 8;" +
                "-fx-prompt-text-fill: #e6e6e6;-fx-font-family: sans-serif;-fx-font-weight: bold;-fx-font-size: 12pt");

//        timer();
        Button suggest = new Button("Suggest");
        suggest.setPrefSize(200, 25);
        suggest.getStyleClass().add("suggestBtn");

        try {
            dataOutputStream.writeUTF("getAuctionPrice_" + auction.getId());
            dataOutputStream.flush();
            Type priceType = new TypeToken<String>() {
            }.getType();
            String priceString = new Gson().fromJson(dataInputStream.readUTF(), priceType);
            currentPrice.setText("$" + priceString);
            suggest.setOnMouseClicked(event -> {
                currentPrice.setText("$" + price.getText());
                try {
                    dataOutputStream.writeUTF("setAuctionPrice_" + price.getText() + "_" + auction.getId());
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                price.clear();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        boxOfImageAndPrice.getChildren().addAll(imageView, name, currentPrice, time, price, suggest);

        FlowPane paneForChatInAuction = new FlowPane();
        paneForChatInAuction.setVgap(3);
        paneForChatInAuction.setStyle("-fx-background-color: white;-fx-border-width: 1;-fx-border-radius: 10;-fx-border-color: #eeeeee;");
        paneForChatInAuction.setPrefSize(360, 400);
        paneForChatInAuction.setLayoutX(260);
        paneForChatInAuction.setLayoutY(50);
        paneForChatInAuction.setPadding(new Insets(15, 15, 15, 15));

        HBox boxOfSupporter = new HBox();
        boxOfSupporter.setAlignment(Pos.CENTER_LEFT);
        boxOfSupporter.setPrefWidth(360);

        Label username = new Label("Group Chat...");
        username.setPrefWidth(250);
        username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 17pt;-fx-text-fill: #1089FF");
        boxOfSupporter.getChildren().addAll(username);

        ScrollPane scrollPaneChat = new ScrollPane();
        scrollPaneChat.setPrefSize(360, 395);
        paneForChatInAuction.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        scrollPaneChat.getStyleClass().add("scroll-barInDiscount");

        HBox sendAndChatField = new HBox();
        TextField chatField = new TextField();
        chatField.getStyleClass().add("chatField");
        chatField.setPromptText("Massage");
        chatField.setPrefSize(280, 30);
        Button send = new Button("Send");
        send.getStyleClass().add("send");
        VBox innerChat = new VBox(5);
        send.setPrefSize(80, 30);

        send.setOnAction(event -> {
//            try {
////                sendMessage(scrollPaneChat, chatField, innerChat, buyer.getUsername());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        });
        chatField.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                try {
//                    sendMessage(scrollPaneChat, chatField, innerChat, buyer.getUsername());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        });
//        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
//        showOldMessages(buyer, innerChat, scrollPaneChat);
        scrollPaneChat.setContent(innerChat);
        sendAndChatField.getChildren().addAll(chatField, send);

        paneForChatInAuction.setAlignment(Pos.CENTER);
        paneForChatInAuction.getChildren().addAll(boxOfSupporter, rectangle(357, 1), scrollPaneChat, sendAndChatField);


        auctionPane.getChildren().addAll(boxOfImageAndPrice,paneForChatInAuction, back);


    }
}
