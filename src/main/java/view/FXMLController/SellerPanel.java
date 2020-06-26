package view.FXMLController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import controller.AccountManager;
import controller.GoodsManager;
import controller.SellerManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import view.NumberField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.adminPopupURL;
import static view.FXMLController.MainMenu.fadeEffect;

public class SellerPanel {

    private AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane sellerPane;
    private Button selectedButton;
    private ScrollPane sellerScrollPane;
    private MainMenu main;
    private AnchorPane mainMenu;
    private Button user;
    private Button btnLogin;
    private File selectedImageFile;
    private File selectedVideoFile;
    private TextField goodName;
    private TextField company;
    private NumberField number;
    private NumberField price;
    private TextField description;
    private JFXRadioButton selectedCategory;
    private ArrayList<TextField> categoryAttributes;


    public SellerPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu, Button user, Button btnLogin) {
        this.mainPane = mainPane;
        this.main = main;
        this.mainMenu = mainMenu;
        this.btnLogin = btnLogin;
        this.user = user;
        sellerPane = new AnchorPane();
        optionsPane = new AnchorPane();
        sellerScrollPane = new ScrollPane();
        selectedButton = new Button("Profile");
        handelButtonOnMouseClick();
    }

    public void changePane() {
        sellerPane.setLayoutY(165);
        optionsPane.setLayoutY(35);
        optionsPane.setLayoutX(30);
        optionsPane.setPrefSize(250, 520);

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
                createButton("Manage offs", "src/main/java/view/image/AdminPanel/discount"),
                createButton("Manage products", "src/main/java/view/image/AdminPanel/product"),
                createButton("Manage requests", "src/main/java/view/image/AdminPanel/request"),
                createButton("Category", "src/main/java/view/image/AdminPanel/category"),
                rectangleDown, createButton("Log out", "src/main/java/view/image/AdminPanel/logout"));
        vBox.setStyle("-fx-background-color: none;");
        vBox.setPadding(new Insets(10, 10, 10, 8));


        optionsPane.setStyle("-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: #e1e1e1;"
                + "-fx-border-radius: 10;" + "-fx-background-radius: 10");
        optionsPane.getChildren().addAll(vBox);
        sellerPane.getChildren().add(optionsPane);
        optionsPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        Login.currentPane = sellerPane;
        mainPane.getChildren().add(sellerPane);

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
        if (text.equals("Profile")) {
            button.setGraphic(imageViewHover);
            button.setStyle("-fx-text-fill: red");
        }

        button.setOnMouseClicked(e -> {
            selectedButton = button;
            button.setGraphic(imageViewHover);
            handelButtonOnMouseClick();
        });
        return button;
    }

    private void handelButtonOnMouseClick() {

        sellerPane.getChildren().remove(sellerScrollPane);
        sellerScrollPane.setPrefSize(1150, 620);
        sellerScrollPane.getStyleClass().add("scroll-bar");
        sellerScrollPane.setLayoutX(330);
        sellerScrollPane.setLayoutY(35);
        Account currentAccount = AccountManager.getOnlineAccount();

        if (selectedButton.getText().equals("Profile")) {
            FlowPane flowPane = new FlowPane();
            flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
            flowPane.setPrefWidth(1200);
            flowPane.setPrefHeight(420);
            flowPane.setPadding(new Insets(50, 0, 10, 70));
            flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
            flowPane.getChildren().addAll(createItemOfProfile("Username:", currentAccount.getUsername()),
                    createItemOfProfile("Full name:", currentAccount.getFirstName() + " " + currentAccount.getLastName()),
                    createItemOfProfile("Phone number:", currentAccount.getPhoneNumber()),
                    createItemOfProfile("Email:", currentAccount.getEmail()));
            sellerScrollPane.setContent(flowPane);
            sellerPane.getChildren().add(sellerScrollPane);
            sellerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            sellerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        } else if (selectedButton.getText().equals("Manage offs")) {
//            sellerScrollPane.setContent();
            sellerPane.getChildren().add(sellerScrollPane);
        } else if (selectedButton.getText().equals("Manage products")) {
            sellerScrollPane.setContent(handelManageProduct());
            sellerPane.getChildren().add(sellerScrollPane);
        } else if (selectedButton.getText().equals("Log out")) {
            AccountManager.setOnlineAccount(new Buyer("temp"));
            user.setVisible(false);
            btnLogin.setVisible(true);
            backToMainMenu();
        }
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

    private void addGood() {
        error.setText("");
        loginPane.getChildren().clear();

        categoryAttributes = new ArrayList<>();
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(120);
        rectangle.setWidth(100);
        rectangle.setLayoutX(340);
        rectangle.setLayoutY(80);
        rectangle.setStyle("-fx-fill: white;" + "-fx-border-width: 20px");

        Label title = new Label("+ ADD GOOD");
        title.setLayoutY(80);
        title.setLayoutX(40);
        title.getStyleClass().add("labelForLoginTitle");

        Button selectAPhoto = new Button("Select a photo");
        selectAPhoto.setLayoutX(340);
        selectAPhoto.setLayoutY(206);
        selectAPhoto.setPrefWidth(100);
        selectAPhoto.getStyleClass().add("selectPhoto");

        FileChooser imageFileChooser = new FileChooser();
        imageFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
                , new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage windowImageFile = new Stage();

        loginPane.getChildren().addAll(selectAPhoto, rectangle);
        selectAPhoto.setOnAction(e -> {
            selectedImageFile = imageFileChooser.showOpenDialog(windowImageFile);
            ImageView imageView = new ImageView(new Image("file:" + selectedImageFile));
            imageView.setFitHeight(120);
            imageView.setFitWidth(100);
            imageView.setLayoutX(340);
            imageView.setLayoutY(80);
            imageView.getStyleClass().add("imageView");
            imageView.autosize();
            loginPane.getChildren().add(imageView);
        });

        goodName = new TextField();
        goodName.setPromptText("Name");
        goodName.setLayoutY(140);
        goodName.setLayoutX(40);
        goodName.setPrefSize(260, 40);
        goodName.getStyleClass().add("text-fieldForSignUp");

        company = new TextField();
        company.setPromptText("Company");
        company.setLayoutY(190);
        company.setLayoutX(40);
        company.setPrefSize(260, 40);
        company.getStyleClass().add("text-fieldForSignUp");

        number = new NumberField();
        number.setPromptText("Number");
        number.setLayoutY(240);
        number.setLayoutX(40);
        number.setPrefSize(100, 40);
        number.getStyleClass().add("text-fieldForSignUp");

        price = new NumberField();
        price.setPromptText("Price");
        price.setLayoutY(240);
        price.setLayoutX(150);
        price.setPrefSize(150, 40);
        price.getStyleClass().add("text-fieldForSignUp");

        description = new TextField();
        description.setPromptText("Description");
        description.setLayoutY(395);
        description.setLayoutX(40);
        description.setPrefSize(400, 40);
        description.getStyleClass().add("text-fieldForSignUp");

        JFXButton submit = new JFXButton("Submit");
        submit.setLayoutY(445);
        submit.setLayoutX(40);
        submit.setPrefSize(400, 40);
        submit.getStyleClass().add("signUp");
        submit.setOnAction(event -> {
            processAddProduct();
        });

        Button selectAVideo = new Button("Select a video");
        selectAVideo.setLayoutX(340);
        selectAVideo.setLayoutY(256);
        selectAVideo.setPrefWidth(100);
        selectAVideo.getStyleClass().add("selectPhoto");

        ScrollPane categoryPack = new ScrollPane();
        categoryPack.setLayoutY(290);
        categoryPack.setLayoutX(40);
        categoryPack.setPrefSize(120, 95);
        categoryPack.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        categoryPack.getStyleClass().add("scroll-barInDiscount");
        categoryPack.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        categoryPack.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        ScrollPane attributePack = new ScrollPane();
        attributePack.setLayoutY(290);
        attributePack.setLayoutX(170);
        attributePack.setPrefSize(280, 95);
        attributePack.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        attributePack.getStyleClass().add("scroll-barInDiscount");
        attributePack.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        attributePack.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox attributeBox = new VBox(8);
        attributeBox.setPrefSize(260, 90);
        attributePack.setContent(attributeBox);

        VBox categoryRadioButtonBox = new VBox(8);
        categoryRadioButtonBox.setPrefSize(110, 95);
        //showCategory
        for (Category category : Shop.getShop().getAllCategories()) {
            JFXRadioButton radioButton = new JFXRadioButton(category.getName());
            categoryRadioButtonBox.getChildren().add(radioButton);
            radioButton.setSelectedColor(Color.YELLOW);
            radioButton.setUnSelectedColor(Color.WHITE);
            radioButton.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white");
            radioButton.setOnMouseClicked(event -> {
                selectedCategory = radioButton;
                Category categorySelected = Shop.getShop().getCategoryByName(selectedCategory.getText());
                attributeBox.getChildren().clear();
                for (String attribute : categorySelected.getAttributes()) {
                    TextField attributeField = new TextField();
                    attributeField.setPrefSize(260, 30);
                    attributeField.setPromptText(attribute);
                    attributeField.getStyleClass().add("text-fieldForSignUp");
                    attributeBox.getChildren().add(attributeField);
                    categoryAttributes.add(attributeField);
                }
            });
        }
        categoryPack.setContent(categoryRadioButtonBox);
        FileChooser videoFileChooser = new FileChooser();
        videoFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4 Files", "*.mp4")
        );
        Stage windowVideoFile = new Stage();
        loginPane.getChildren().add(selectAVideo);
        selectAVideo.setOnAction(e -> {
            selectedVideoFile = videoFileChooser.showOpenDialog(windowVideoFile);
        });

//        FlowPane flowPane = new FlowPane();
//        flowPane.setLayoutX(40);
//        flowPane.setLayoutY(350);
//        flowPane.setPrefSize(400, 80);
//        flowPane.setStyle("-fx-background-color: none");

//        scrollPane.setContent(flowPane);
//        scrollPane.getStyleClass().add("scroll-barInDiscount");
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

//        for (Category category : Shop.getShop().getAllCategories()) {
//            HBox hBox = new HBox();
//            hBox.setPrefSize(100, 40);
//            hBox.setPadding(new Insets(8, 5, 8, 5));
//
//            JFXCheckBox username = new JFXCheckBox(allAccount.getUsername());
//            username.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';");
//            hBox.getChildren().add(username);
//            hBox.setStyle("-fx-background-color: none");
////            if ()
//
//            flowPane.getChildren().add(hBox);
//        }


        loginPane.getChildren().addAll(exitButton(), goodName, company,
                number, price, description, categoryPack, attributePack, title, submit, error);
    }

    private boolean isAllFieldsFilled() {
        if (goodName.getText().length() == 0 || company.getText().length() == 0 || number.getText().length() == 0 ||
        price.getText().length() == 0 || description.getText().length() == 0) {
            return false;
        } else {
            for (TextField categoryAttribute : categoryAttributes) {
                if (categoryAttribute.getText().length() == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    private void processAddProduct() {
        if (isAllFieldsFilled()) {
            Category selectedCategory = Shop.getShop().getCategoryByName(this.selectedCategory.getText());
            HashMap<String, String> hashMap = new HashMap<>();
            for (int i = 0; i < selectedCategory.getAttributes().size(); i++) {
                hashMap.put(selectedCategory.getAttributes().get(i), categoryAttributes.get(i).getText());
            }
            int number = Integer.parseInt(this.number.getText());
            long price = Long.parseLong(this.price.getText());
            SellerManager.addProduct(goodName.getText(), company.getText(), number, price, selectedCategory.getName(),
                    hashMap, description.getText(), selectedImageFile.getAbsolutePath());
            popupWindow.close();
            fade(0.5, 10);
            sellerScrollPane.setContent(null);
            sellerScrollPane.setContent(handelManageProduct());
        } else {
            error.setText("you should fill all the fields");
        }
    }

    private Button exitButton() {
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

    private AnchorPane loginPane;
    private Label error;
    private Stage popupWindow;

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }


    public void popup(String input) throws IOException {
        loginPane = new AnchorPane();
        error = new Label();
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        URL url = Paths.get(adminPopupURL).toUri().toURL();
        AnchorPane layout = FXMLLoader.load(url);
        Scene scene1 = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        loginPane.setStyle("-fx-background-color: #1089ff;" + "-fx-background-radius: 30px;");
        loginPane.setPrefWidth(480);
        loginPane.setPrefHeight(580);

        fade(10, 0.5);

        layout.setLayoutX(500);
        layout.setLayoutY(150);
        layout.getChildren().add(loginPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene1);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);

        if (input.equals("addGood")) {
            addGood();
        }
        popupWindow.showAndWait();

    }

    private FlowPane handelManageProduct() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        ImageView addGood = new ImageView();
        addGood.setFitWidth(200);
        addGood.setFitHeight(200);
        addGood.getStyleClass().add("addGoodPlus");
        addGood.setOnMouseClicked(event -> {
            try {
                popup("addGood");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox addGoodBox = new VBox();
        addGoodBox.setPrefWidth(225);
        addGoodBox.setPrefHeight(350);
        addGoodBox.getStyleClass().add("vBoxInMainMenu");
        addGoodBox.getChildren().add(addGood);
        addGoodBox.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(addGoodBox);

        for (Good good : ((Seller) AccountManager.getOnlineAccount()).getGoods()) {
            VBox goodPack = new VBox();
            goodPack.setPrefWidth(225);
            goodPack.setPrefHeight(350);
            goodPack.getStyleClass().add("vBoxInMainMenu");
            ImageView logoImage = new ImageView(new Image("file:src/main/java/view/image/logo.png"));
            logoImage.setFitHeight(170);
            logoImage.setFitWidth(170);
            Label name = new Label(good.getName());
            Label price = new Label("$" + good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            goodPack.setOnMouseEntered(event -> fadeEffect(goodPack));
            logoImage.setOnMouseClicked(event -> {
                GoodsManager.setCurrentGood(good);
                new GoodMenu(mainPane).changePane();
            });
            goodPack.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView();
            imageView.getStyleClass().add("imageViewRecy");
            imageView.setFitWidth(31);
            imageView.setFitHeight(31);
            HBox hBox = new HBox(imageView);
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPrefWidth(230);
            hBox.setPadding(new Insets(0, 20, 0, 0));
            goodPack.getChildren().addAll(logoImage, name, price, visit, hBox);
            imageView.setOnMouseClicked(e -> {
                Shop.getShop().getAllGoods().remove(good);
                GoodsManager.getFilteredGoods().remove(good);
                ((Seller) AccountManager.getOnlineAccount()).getGoods().remove(good);
                flowPane.getChildren().remove(goodPack);
            });
            flowPane.getChildren().add(goodPack);
        }

        return flowPane;
    }


    public void backToMainMenu() {
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }

}
