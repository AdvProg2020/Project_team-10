package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.*;
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

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.paint.Color.color;
import static view.FXML.FXML.adminPopupURL;
import static view.FXMLController.MainMenu.fadeEffect;

public class SellerPanel {

    private AnchorPane mainPane;
    public AnchorPane optionsPane;
    private AnchorPane sellerPane;
    private Button selectedButton = new Button("Profile");
    private ImageView imageViewSelectedButton;
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
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Account onlineAccount;
    private String token;

    private JFXDatePicker startDate;
    private JFXTimePicker startTime;
    private JFXDatePicker endDate;
    private JFXTimePicker endTime;
    private NumberField percent;
    private ArrayList<Integer> selectedGoodsId;

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phoneNumber;
    private PasswordField password;

    private AnchorPane loginPane;
    private Label error;
    private Stage popupWindow;

    private Button auctionSelected = new Button();
    private int auctionGoodId;
    private ArrayList<Integer> auctionGoodsId = new ArrayList<>();

    private NumberField moneyField;
    private FlowPane moneyPane;
    private Label creditLabel;


    public SellerPanel(AnchorPane mainPane, MainMenu main, AnchorPane mainMenu, Button user, Button btnLogin
            , Socket socket, Account onlineAccount, String token) throws IOException {
        this.mainPane = mainPane;
        this.main = main;
        this.mainMenu = mainMenu;
        this.btnLogin = btnLogin;
        this.user = user;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.onlineAccount = onlineAccount;
        this.token = token;
        sellerPane = new AnchorPane();
        optionsPane = new AnchorPane();
        sellerScrollPane = new ScrollPane();
        handelButtonOnMouseClick();
    }


    private VBox boxForEdit(String name) {
        VBox boxFirstName = new VBox(2);
        boxFirstName.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label();
        TextField field = new TextField();
        field.setPrefSize(350, 40);

        if (name.equals("First name: ")) {
            label.setText(" First name: ");
            firstName = field;
            field.setText(onlineAccount.getFirstName());
        } else if (name.equals("Last name: ")) {
            lastName = field;
            label.setText(" Last name: ");
            field.setText(onlineAccount.getLastName());
        } else if (name.equals("Email: ")) {
            email = field;
            label.setText(" Email: ");
            field.setText(onlineAccount.getEmail());
        } else if (name.equals("Phone: ")) {
            NumberField numberField = new NumberField();
            numberField.setPrefSize(350, 40);
            numberField.setText(onlineAccount.getPhoneNumber());
            label.setText(" Phone number: ");
            field = numberField;
            phoneNumber = field;
        } else if (name.equals("password")) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Current password");
            passwordField.setPrefSize(350, 40);
            field = passwordField;
            password = passwordField;
        } else if (name.equals("newPass")) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("New password");
            passwordField.setPrefSize(350, 40);
            field = passwordField;
        }
        label.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 15;-fx-text-fill:rgb(16,137,255)");
        field.getStyleClass().add("text-fieldForEdit");
        boxFirstName.getChildren().addAll(label, field);

        return boxFirstName;

    }

    private void editProfilePain(FlowPane flowPane) {
        flowPane.getChildren().clear();
        flowPane.setHgap(80);
        flowPane.setVgap(12);

        VBox backBox = new VBox();
        backBox.setPrefSize(800, 40);
        ImageView back = new ImageView();
        back.getStyleClass().add("backStyle");
        back.setFitWidth(30);
        back.setFitHeight(30);
        backBox.getChildren().add(back);
        back.setOnMouseClicked(event -> {
            try {
                handelButtonOnMouseClick();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox currentPass = boxForEdit("password");
        currentPass.setDisable(true);
        VBox newPass = boxForEdit("newPass");

        VBox submitBox = new VBox();
        submitBox.setPadding(new Insets(20, 0, 0, 0));
        Button submit = new Button("Submit");
        submit.getStyleClass().add("buttonSubmit");
        submit.setPrefSize(780, 40);
        submit.setOnMouseClicked(event -> {
            try {
                processEdit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        submitBox.getChildren().add(submit);

        flowPane.getChildren().addAll(backBox, boxForEdit("First name: "), boxForEdit("Last name: "),
                boxForEdit("Email: "), boxForEdit("Phone: "), newPass, submitBox);


    }

    private void processEdit() throws IOException {
        dataOutputStream.writeUTF("edit_profile_" + password.getText() + "_" + firstName.getText() + "_" +
                lastName.getText() + "_" + phoneNumber.getText() + "_" + email.getText() + "_" + token);
        dataOutputStream.flush();
        handelButtonOnMouseClick();
    }

    public void changePane() {
        sellerPane.setLayoutY(165);
        optionsPane.setLayoutY(35);
        optionsPane.setLayoutX(30);
        optionsPane.setPrefSize(250, 520);

        HBox hBox = new HBox();
        Circle circle = new Circle(40);
        ImagePattern pattern = new ImagePattern(new Image("file:" + onlineAccount.getImagePath()));
        circle.setFill(pattern);
        circle.setStrokeWidth(4);
        circle.setStroke(Color.rgb(16, 137, 255));

        hBox.setPadding(new Insets(0, 0, 5, 9));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(4);
        hBox.setPrefWidth(170);

        ImageView credit = new ImageView(new Image("file:src/main/java/view/image/AdminPanel/credit.png"));
        credit.setFitHeight(20);
        credit.setFitWidth(25);
        creditLabel = new Label(" $" + onlineAccount.getCredit());
        creditLabel.getStyleClass().add("labelUsername");
        creditLabel.setStyle("-fx-text-fill: #00ff30");

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(credit, creditLabel);

        VBox boxIncreaseAndWith = new VBox(2);

        Label increase = new Label("➕ Credit");
        increase.getStyleClass().add("creditStyle1");
        increase.setPadding(new Insets(4, 4, 4, 4));
        increase.setOnMouseClicked(event -> popupMoney(true));

        Label withdrawal = new Label("➖ Withdrawal");
        withdrawal.getStyleClass().add("creditStyle1");
        withdrawal.setPadding(new Insets(4, 4, 4, 4));
        withdrawal.setOnMouseClicked(event -> popupMoney(false));

        boxIncreaseAndWith.getChildren().addAll(increase, withdrawal);

        VBox vBoxP = new VBox();
        Label username = new Label("Hi " + onlineAccount.getUsername());
        vBoxP.setAlignment(Pos.CENTER_LEFT);
        vBoxP.setSpacing(5);
        vBoxP.getChildren().addAll(username, hBox1, boxIncreaseAndWith);
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

    private Button createButton(String text, String style) {
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
//            button.setStyle("-fx-text-fill: red");
            selectedButton = button;
            imageViewSelectedButton = imageView;
        }

        button.setOnMouseClicked(e -> {
            selectedButton.setGraphic(imageViewSelectedButton);
            imageViewSelectedButton = imageView;
            selectedButton = button;
            selectedButton.setGraphic(imageViewHover);
            try {
                handelButtonOnMouseClick();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    private void handelButtonOnMouseClick() throws IOException {

        sellerPane.getChildren().remove(sellerScrollPane);
        sellerScrollPane.setPrefSize(1150, 620);
        sellerScrollPane.getStyleClass().add("scroll-bar");
        sellerScrollPane.setLayoutX(330);
        sellerScrollPane.setLayoutY(35);

        dataOutputStream.writeUTF("get_seller_" + onlineAccount.getUsername());
        dataOutputStream.flush();
        Type sellerType = new TypeToken<Seller>() {
        }.getType();
        onlineAccount = new Gson().fromJson(dataInputStream.readUTF(), sellerType);

        switch (selectedButton.getText()) {
            case "Profile":
                FlowPane flowPane = new FlowPane();
                flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
                flowPane.setPrefWidth(1200);
                flowPane.setPrefHeight(420);

                VBox hyperLink = new VBox();
                hyperLink.setPadding(new Insets(20, 0, 0, 0));
                Hyperlink editProfile = new Hyperlink("Edit profile");
                editProfile.setOnMouseClicked(event -> editProfilePain(flowPane));
                editProfile.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';-fx-font-size: 14;");
                ImageView edit = new ImageView(new Image("file:src/main/java/view/image/edit.png"));
                edit.setFitHeight(18);
                edit.setFitWidth(18);
                hyperLink.setPrefWidth(960);
                hyperLink.setAlignment(Pos.CENTER);
                editProfile.setGraphic(edit);
                hyperLink.getChildren().add(editProfile);

                flowPane.setPadding(new Insets(50, 0, 10, 70));
                flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");
                flowPane.getChildren().addAll(createItemOfProfile("Username:", onlineAccount.getUsername()),
                        createItemOfProfile("Full name:", onlineAccount.getFirstName() + " " + onlineAccount.getLastName()),
                        createItemOfProfile("Phone number:", onlineAccount.getPhoneNumber()),
                        createItemOfProfile("Email:", onlineAccount.getEmail()),
                        createCompanyOfProfile(((Seller) onlineAccount).getCompany()), hyperLink);
                sellerScrollPane.setContent(flowPane);
                sellerPane.getChildren().add(sellerScrollPane);
                sellerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                sellerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                break;
            case "Manage offs":
                sellerScrollPane.setContent(handelManageOff());
                sellerPane.getChildren().add(sellerScrollPane);
                break;
            case "Manage products":
                sellerScrollPane.setContent(handelManageProduct());
                sellerPane.getChildren().add(sellerScrollPane);
                break;
            case "Category":
                sellerScrollPane.setContent(handelCategory());
                sellerPane.getChildren().add(sellerScrollPane);
                break;
            case "Log out":
                dataOutputStream.writeUTF("logout_" + token);
                dataOutputStream.flush();
                onlineAccount = new Buyer("temp");
                main.onlineAccount = this.onlineAccount;
                user.setVisible(false);
                btnLogin.setVisible(true);
                backToMainMenu();
                break;
        }
    }

    private FlowPane handelCategory() throws IOException {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        HBox hBoxTitle = new HBox(0);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(40);

        Label categoryName = new Label("Name");
        categoryName.setPrefWidth(255);
        categoryName.getStyleClass().add("labelForDiscount");

        Label attributesTitle = new Label("  " + "Attributes");
        attributesTitle.setGraphic(line2());
        attributesTitle.setPrefWidth(700);
        attributesTitle.getStyleClass().add("labelForDiscount");


        hBoxTitle.getChildren().addAll(categoryName, attributesTitle);
        flowPane.getChildren().add(hBoxTitle);

        dataOutputStream.writeUTF("getAllCategories_" + token);
        dataOutputStream.flush();
        Type allCategoriesType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> categories = new Gson().fromJson(dataInputStream.readUTF(), allCategoriesType);

        for (Category category : categories) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(30);

            Label name = new Label("" + category.getName());
            name.setPrefWidth(255);
            name.getStyleClass().add("labelForDiscount");


            Label attributes = new Label("  " + category.getAttributes());
            attributes.setGraphic(line2());
            attributes.setPrefWidth(700);
            attributes.getStyleClass().add("labelForDiscount");

            hBox.getChildren().addAll(name, attributes);
            flowPane.getChildren().add(hBox);
        }

        return flowPane;

    }

    private Rectangle line2() {
        Rectangle line = new Rectangle(2, 40);
        line.setStyle("-fx-fill: #d5d5d5");
        return line;
    }

    private Rectangle line() {
        Rectangle line = new Rectangle(2, 70);
        line.setStyle("-fx-fill: #d5d5d5");
        return line;
    }

    private FlowPane handelManageOff() throws IOException {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setPadding(new Insets(50, 0, 10, 70));
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        HBox hBoxTitle = new HBox(0);
        hBoxTitle.setAlignment(Pos.CENTER_LEFT);
        hBoxTitle.setPadding(new Insets(0, 12, 0, 12));
        hBoxTitle.getStyleClass().add("hboxTitle");
        hBoxTitle.setPrefHeight(60);

        Label categoryName = new Label("Id");
        categoryName.setPrefWidth(50);
        categoryName.getStyleClass().add("labelForDiscount");

        Label startDate = new Label("  " + "Start date");
        startDate.setGraphic(line());
        startDate.setPrefWidth(225);
        startDate.getStyleClass().add("labelForDiscount");

        Label endDate = new Label("  " + "End date");
        endDate.setGraphic(line());
        endDate.setPrefWidth(225);
        endDate.getStyleClass().add("labelForDiscount");

        Label goodText = new Label("  " + "Good");
        goodText.setGraphic(line());
        goodText.setPrefWidth(450);
        goodText.getStyleClass().add("labelForDiscount");

        ImageView imageViewPlus = new ImageView();
        imageViewPlus.setOnMouseClicked(event -> {
            try {
                popup("Manage Offs");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        imageViewPlus.getStyleClass().add("imageViewPlus");
        imageViewPlus.setFitWidth(35);
        imageViewPlus.setFitHeight(35);

        hBoxTitle.getChildren().addAll(categoryName, startDate, endDate, goodText, imageViewPlus);
        flowPane.getChildren().add(hBoxTitle);

        for (Off off : ((Seller) onlineAccount).getOffs()) {
            HBox hBox = new HBox(0);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 12, 0, 12));
            hBox.getStyleClass().add("hbox");
            hBox.setPrefHeight(70);

            Label id = new Label("" + off.getId());
            id.setPrefWidth(50);
            id.getStyleClass().add("labelForDiscount");

            Label start = new Label("  " + off.getStartDate());
            start.setGraphic(line());
            start.setPrefWidth(225);
            start.getStyleClass().add("labelForDiscount");

            Label end = new Label("  " + off.getEndDate());
            end.setGraphic(line());
            end.setPrefWidth(225);
            end.getStyleClass().add("labelForDiscount");

            FlowPane flowPaneInner = new FlowPane();
            flowPaneInner.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
            flowPaneInner.setPrefWidth(440);
            flowPaneInner.setPrefHeight(50);
//            flowPaneInner.setPadding(new Insets(20, 0, 10, 20));

            ScrollPane goodOffPack = new ScrollPane();
            goodOffPack.setPrefSize(427, 50);
            goodOffPack.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            goodOffPack.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            goodOffPack.getStyleClass().add("scroll-barInDiscount");
            for (Integer goodId : off.getGoodsId()) {
                HBox goodPack = new HBox(1);
                goodPack.setPadding(new Insets(5, 5, 5, 5));
                dataOutputStream.writeUTF("get_product_" + goodId + "_" + token);
                dataOutputStream.flush();
                Type productType = new TypeToken<Good>() {
                }.getType();
                Good good = new Gson().fromJson(dataInputStream.readUTF(), productType);
                VBox vBox = new VBox(2);
                vBox.setPrefWidth(43);
                vBox.setPrefHeight(60);
                ImageView productImage = new ImageView(new Image("file:" + good.getImagePath()));
                productImage.setFitHeight(50);
                productImage.setFitWidth(50);
                productImage.getStyleClass().add("goodImage");
                Label name = new Label(good.getName());
                Label price = new Label("$" + good.getPrice() + "");
                name.setStyle("-fx-font-family: 'Myriad Pro'; -fx-font-size: 10px;-fx-text-fill: black");
                price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden'; -fx-font-size: 8px;-fx-font-weight: bold;-fx-text-fill: black");
                vBox.setOnMouseEntered(event -> fadeEffect(vBox));
                productImage.setOnMouseClicked(event -> {
                    GoodMenu goodMenu = null;
                    try {
                        goodMenu = new GoodMenu(mainPane, socket, onlineAccount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    goodMenu.setCurrentGood(good);
                    mainPane.getChildren().remove(Login.currentPane);
                    goodMenu.changePane();
                });
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(name, price);
                goodPack.getChildren().addAll(productImage, vBox);
                flowPaneInner.getChildren().add(goodPack);
            }
            goodOffPack.setContent(flowPaneInner);

            ImageView edit = new ImageView();
            edit.getStyleClass().add("editImage");
            edit.setFitWidth(25);
            edit.setFitHeight(25);

            ImageView bin = new ImageView();
            bin.getStyleClass().add("binImage");
            bin.setFitWidth(31);
            bin.setFitHeight(25);
            bin.setOnMouseClicked(e -> {
                try {
                    dataOutputStream.writeUTF("remove_off " + off.getId() + "_" + token);
                    dataOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                flowPane.getChildren().remove(hBox);
            });

            hBox.getChildren().addAll(id, start, end, line(), goodOffPack, edit, bin);
            flowPane.getChildren().add(hBox);
        }
        return flowPane;
    }

    private VBox createCompanyOfProfile(String account) {
        VBox vBox = new VBox(2);
        vBox.setPrefSize(1000, 80);
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setStyle("-fx-border-width: 1px;" + "-fx-border-color: #e2e2e2;");
        Label label = new Label("Company: ");
        ImageView company = new ImageView(new Image("file:src/main/java/view/image/company.png"));
        company.setFitWidth(30);
        company.setFitHeight(30);
        label.setGraphic(company);
        label.getStyleClass().add("labelUser");
        Label labelUsername = new Label(account);
        labelUsername.getStyleClass().add("labelUsernameInProfile");
        vBox.getChildren().addAll(label, labelUsername);
        return vBox;
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

    private void addGood() throws IOException {
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

        Button selectAVideo = new Button("Select a video");
        selectAVideo.setLayoutX(340);
        selectAVideo.setLayoutY(236);
        selectAVideo.setPrefWidth(100);
        selectAVideo.getStyleClass().add("selectPhoto");

        Button selectAFile= new Button("Upload file");
        selectAFile.setLayoutX(340);
        selectAFile.setLayoutY(256);
        selectAFile.setPrefWidth(100);
        selectAFile.getStyleClass().add("selectPhoto");

        FileChooser imageFileChooser = new FileChooser();
        imageFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
                , new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage windowImageFile = new Stage();

        FileChooser fileChooser = new FileChooser();
        Stage fileChooserWindow = new Stage();


        selectAFile.setOnAction(event -> {
            selectAPhoto.setDisable(true);
            selectAVideo.setDisable(true);
            selectedVideoFile = fileChooser.showOpenDialog(fileChooserWindow);
        });

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
            try {
                processAddProduct();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

        dataOutputStream.writeUTF("getAllCategories_" + token);
        dataOutputStream.flush();
        Type allCategoriesType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> allCategories = new Gson().fromJson(dataInputStream.readUTF(), allCategoriesType);
        for (Category category : allCategories) {
            JFXRadioButton radioButton = new JFXRadioButton(category.getName());
            categoryRadioButtonBox.getChildren().add(radioButton);
            radioButton.setSelectedColor(Color.YELLOW);
            radioButton.setUnSelectedColor(Color.WHITE);
            radioButton.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white");
            radioButton.setOnMouseClicked(event -> {
                try {
                    selectedCategory = radioButton;
                    dataOutputStream.writeUTF("get_category_" + selectedCategory.getText() + "_" + token);
                    dataOutputStream.flush();
                    Type categoryType = new TypeToken<Category>() {
                    }.getType();
                    Category categorySelected = new Gson().fromJson(dataInputStream.readUTF(), categoryType);
                    attributeBox.getChildren().clear();
                    for (String attribute : categorySelected.getAttributes()) {
                        TextField attributeField = new TextField();
                        attributeField.setPrefSize(260, 30);
                        attributeField.setPromptText(attribute);
                        attributeField.getStyleClass().add("text-fieldForSignUp");
                        attributeBox.getChildren().add(attributeField);
                        categoryAttributes.add(attributeField);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
                number, price, description, categoryPack, attributePack, title, submit, error,selectAFile);
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

    private void processAddProduct() throws IOException {
        if (isAllFieldsFilled()) {
            dataOutputStream.writeUTF("get_category_" + selectedCategory.getText() + "_" + token);
            dataOutputStream.flush();
            Type categoryType = new TypeToken<Category>() {
            }.getType();
            Category selectedCategory = new Gson().fromJson(dataInputStream.readUTF(), categoryType);
            HashMap<String, String> hashMap = new HashMap<>();
            for (int i = 0; i < selectedCategory.getAttributes().size(); i++) {
                hashMap.put(selectedCategory.getAttributes().get(i), categoryAttributes.get(i).getText());
            }
            int number = Integer.parseInt(this.number.getText());
            long price = Long.parseLong(this.price.getText());

//            File destImage = new File("src/main/java/view/databaseMedia/productImageAndVideo/" +
//                    Login.createTokenForFiles() + ".jpg");
//            copyFileUsingStream(selectedImageFile, destImage);


//            File destVideo = new File("src/main/java/view/databaseMedia/productImageAndVideo/" +
//                    Login.createTokenForFiles() + ".mp4");
//            copyFileUsingStream(selectedVideoFile, destVideo);
            String videoPath = selectedVideoFile.getPath();

            if (selectedImageFile== null){
                dataOutputStream.writeUTF("create_product_" + goodName.getText() + "_" + company.getText() + "_" + number
                        + "_" + price + "_" + selectedCategory.getName() + "_" + new Gson().toJson(hashMap) + "_" + description.getText()
                        + "_" + sendFile("src/main/java/view/image/file.png") + "_" + sendFile(videoPath) + "_" + token);
            } else {
                String imagePath = selectedImageFile.getPath();
                dataOutputStream.writeUTF("create_product_" + goodName.getText() + "_" + company.getText() + "_" + number
                        + "_" + price + "_" + selectedCategory.getName() + "_" + new Gson().toJson(hashMap) + "_" + description.getText()
                        + "_" + sendFile(imagePath) + "_" + sendFile(videoPath) + "_" + token);
            }
            dataOutputStream.flush();

            Type sellerType = new TypeToken<Seller>() {
            }.getType();
            onlineAccount = new Gson().fromJson(dataInputStream.readUTF(), sellerType);
            main.onlineAccount = this.onlineAccount;
//            SellerManager.addProduct(goodName.getText(), company.getText(), number, price, selectedCategory.getName(),
//                    hashMap, description.getText(), selectedImageFile.getAbsolutePath(), selectedVideoFile.getAbsolutePath());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            popupWindow.close();
            fade(0.5, 10);
            sellerScrollPane.setContent(handelManageProduct());
        } else {
            error.setText("you should fill all the fields");
        }
    }

    private String receiveFile() {
        try {
            int bytesRead;

            String fileName = dataInputStream.readUTF();
            OutputStream output = new FileOutputStream("src/main/java/view/fileSender/"+fileName);
            long size = dataInputStream.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            System.out.println("File "+fileName+" received from Server.");
            return "src/main/java/view/fileSender/"+fileName;
        } catch (IOException ex) {
            System.out.println("Exception: "+ex);
            return null;
        }

    }

    private String sendFile(String path) throws IOException {
        try {
            File myFile = new File(path);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            //Sending file name and file size to the server
            dataOutputStream.writeUTF("sendFile_"+myFile.getName());
            dataOutputStream.writeLong(mybytearray.length);
            dataOutputStream.write(mybytearray, 0, mybytearray.length);
            dataOutputStream.flush();
            System.out.println("File "+path+" sent to Server.");
        } catch (Exception e) {
            System.err.println("Exceptionnnn: "+e);
        }
        return dataInputStream.readUTF();
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
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

    private void fade(double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setNode(mainPane);
        fade.play();
    }

    private void popup(String input) throws IOException {
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
        } else if (input.equals("Manage Offs")) {
            addOff();
        } else if (input.equals("Auction")) {
            handelAuctionPop();
        }
        popupWindow.showAndWait();

    }

    private void addOff() {
        selectedGoodsId = new ArrayList<>();
        error.setText("");
        loginPane.getChildren().clear();

        Label title = new Label("+ ADD OFF");
        title.setLayoutY(80);
        title.setLayoutX(40);
        title.getStyleClass().add("labelForLoginTitle");

        JFXButton submit = new JFXButton("Submit");
        submit.setLayoutY(445);
        submit.setLayoutX(40);
        submit.setPrefSize(400, 40);
        submit.getStyleClass().add("signUp");
        submit.setOnMouseClicked(event -> {
            try {
                processAddOff();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        startDate = new JFXDatePicker();
        startDate.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        startDate.setDefaultColor(Color.rgb(244, 218, 0));
        startDate.setLayoutY(150);
        startDate.setLayoutX(40);
        startDate.setPrefSize(240, 40);
        startDate.setOnAction(event -> {
            LocalDate date = startDate.getValue();
        });

        startTime = new JFXTimePicker();
        startTime.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        startTime.setDefaultColor(Color.rgb(244, 218, 0));
        startTime.setPrefSize(140, 40);
        startTime.setLayoutY(150);
        startTime.setLayoutX(300);
        startTime.setOnAction(event -> {
            LocalTime date = startTime.getValue();
        });

        endDate = new JFXDatePicker();
        endDate.setDefaultColor(Color.rgb(244, 218, 0));
        endDate.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        endDate.setPrefSize(240, 40);
        endDate.setLayoutY(220);
        endDate.setLayoutX(40);
        endDate.setOnAction(event -> {
            LocalDate date = endDate.getValue();
        });

        endTime = new JFXTimePicker();
        endTime.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        endTime.setDefaultColor(Color.rgb(244, 218, 0));
        endTime.setPrefSize(140, 40);
        endTime.setLayoutY(220);
        endTime.setLayoutX(300);
        endTime.setOnAction(event -> {
            LocalTime date = endTime.getValue();
        });

        percent = new NumberField();
        percent.setPromptText("Percent");
        percent.setLayoutY(280);
        percent.setLayoutX(40);
        percent.setPrefSize(400, 40);
        percent.getStyleClass().add("text-fieldForSignUp");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutY(350);
        scrollPane.setLayoutX(40);
        scrollPane.setPrefSize(400, 80);

        FlowPane flowPane = new FlowPane();
        scrollPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setLayoutX(40);
        flowPane.setLayoutY(350);
        flowPane.setPrefSize(400, 80);
        flowPane.setStyle("-fx-background-color: none");

        scrollPane.getStyleClass().add("scroll-barInDiscount");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Seller seller = ((Seller) onlineAccount);

        for (Good good : seller.getGoods()) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(100);
            vBox.setPrefHeight(60);
            vBox.getStyleClass().add("vBoxInMainMenu");
            ImageView productImage = new ImageView(new Image("file:" + good.getImagePath()));
            productImage.setFitHeight(50);
            productImage.setFitWidth(50);
            productImage.getStyleClass().add("goodImage");
            JFXCheckBox productCheckBox = new JFXCheckBox(good.getName());
            productCheckBox.setOnAction(event -> {
                if (productCheckBox.isSelected()) {
                    selectedGoodsId.add(good.getId());
                } else {
                    selectedGoodsId.remove(good.getId());
                }
            });
            vBox.getChildren().addAll(productImage, productCheckBox);

            flowPane.getChildren().add(vBox);
        }
        scrollPane.setContent(flowPane);

        loginPane.getChildren().addAll(exitButton(), title, startDate,
                startTime, endDate, endTime, percent, submit, scrollPane, error);

    }

    private void processAddOff() throws IOException {
        String startYear = "" + startDate.getValue().getYear();
        String endYear = "" + endDate.getValue().getYear();
        String startMonth = "" + startDate.getValue().getMonthValue();
        if (startMonth.length() == 1) {
            startMonth = "0" + startMonth;
        }
        String endMonth = "" + endDate.getValue().getMonthValue();
        if (endMonth.length() == 1) {
            endMonth = "0" + endMonth;
        }
        String startDay = "" + startDate.getValue().getDayOfMonth();
        if (startDay.length() == 1) {
            startDay = "0" + startDay;
        }
        String endDay = "" + endDate.getValue().getDayOfMonth();
        if (endDay.length() == 1) {
            endDay = "0" + endDay;
        }
        String startDate = (startMonth + "/" + startDay + "/" + startYear + " " + this.startTime.getValue());
        String endDate = (endMonth + "/" + endDay + "/" + endYear + " " + this.endTime.getValue());
        int percent = Integer.parseInt(this.percent.getText());

        dataOutputStream.writeUTF("create_off_" + new Gson().toJson(selectedGoodsId) + "_" + startDate + "_"
                + endDate + "_" + percent + "_" + token);
        dataOutputStream.flush();
        Type sellerType = new TypeToken<Seller>() {
        }.getType();
        onlineAccount = new Gson().fromJson(dataInputStream.readUTF(), sellerType);
        main.onlineAccount = this.onlineAccount;
        popupWindow.close();
        fade(0.5, 10);
        sellerScrollPane.setContent(handelManageOff());

    }

    private FlowPane handelManageProduct() throws IOException {
        FlowPane flowPane = new FlowPane();
        flowPane.getStylesheets().add("file:src/main/java/view/css/adminPanel.css");
        flowPane.setPrefWidth(1150);
        flowPane.setPrefHeight(620);
        flowPane.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10");

        ImageView plus = new ImageView();
        plus.setFitWidth(200);
        plus.setFitHeight(200);
        plus.getStyleClass().add("addGoodPlus");
        plus.setOnMouseClicked(event -> {
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
        addGoodBox.getChildren().add(plus);
        addGoodBox.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(addGoodBox);
        for (Good good : ((Seller) onlineAccount).getGoods()) {

            dataOutputStream.writeUTF("receiveGoodFile_"+ good.getId());
            dataOutputStream.flush();

            VBox goodPack = new VBox();
            goodPack.setPrefWidth(225);
            goodPack.setPrefHeight(350);
            goodPack.getStyleClass().add("vBoxInMainMenu");
            ImageView productImage = new ImageView(new Image("file:" + receiveFile()));
            productImage.setFitHeight(170);
            productImage.setFitWidth(170);
            Label name = new Label(good.getName());
            Label price = new Label("$" + good.getPrice() + "");
            Label visit = new Label(good.getVisitNumber() + "");
            name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 14px;");
            price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 18px;" + "-fx-font-weight: bold;");
            goodPack.setOnMouseEntered(event -> fadeEffect(goodPack));
            productImage.setOnMouseClicked(event -> {
                GoodMenu goodMenu = null;
                try {
                    goodMenu = new GoodMenu(mainPane, socket, onlineAccount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goodMenu.setCurrentGood(good);
                mainPane.getChildren().remove(Login.currentPane);
                goodMenu.changePane();
            });
            goodPack.setAlignment(Pos.CENTER);

            Button auction = new Button();
            auction.setPrefSize(150, 31);
            if (auctionGoodsId.contains(good.getId())) {
                auctionSelected.getStyleClass().clear();
                auctionSelected.getStyleClass().add("auctionButtonNext");
                auction.setText("Went to auction");
            } else {
                auction.setText("Auction");
                auction.getStyleClass().add("auctionButton");
            }

            auction.setOnMouseClicked(event -> {
                try {
                    auctionGoodId = good.getId();
                    auctionSelected = auction;
                    popup("Auction");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            ImageView bin = new ImageView();
            bin.getStyleClass().add("imageViewRecy");
            bin.setFitWidth(31);
            bin.setFitHeight(31);
            HBox hBox = new HBox(auction, bin);
            hBox.setSpacing(5);
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPrefWidth(230);
            hBox.setPadding(new Insets(8, 20, 0, 0));
            goodPack.getChildren().addAll(productImage, name, price, visit, hBox);
            bin.setOnMouseClicked(e -> {
                try {
                    dataOutputStream.writeUTF("remove_product_" + good.getId() + "_" + token);
                    dataOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                flowPane.getChildren().remove(goodPack);
            });
            flowPane.getChildren().add(goodPack);
        }

        return flowPane;
    }

    private void handelAuctionPop() {
        error.setText("");
        loginPane.getChildren().clear();

        Label title = new Label("Duration Auction");
        title.setLayoutY(80);
        title.setLayoutX(40);
        title.getStyleClass().add("labelForLoginTitle");

        JFXButton submit = new JFXButton("Submit");
        submit.setLayoutY(445);
        submit.setLayoutX(40);
        submit.setPrefSize(400, 40);
        submit.getStyleClass().add("signUp");
        submit.setOnMouseClicked(event -> {
            processAddAuction();
        });

        startDate = new JFXDatePicker();
        startDate.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        startDate.setDefaultColor(Color.rgb(244, 218, 0));
        startDate.setLayoutY(150);
        startDate.setLayoutX(40);
        startDate.setPrefSize(240, 40);
        startDate.setOnAction(event -> {
            LocalDate date = startDate.getValue();
        });

        startTime = new JFXTimePicker();
        startTime.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        startTime.setDefaultColor(Color.rgb(244, 218, 0));
        startTime.setPrefSize(140, 40);
        startTime.setLayoutY(150);
        startTime.setLayoutX(300);
        startTime.setOnAction(event -> {
            LocalTime date = startTime.getValue();
        });

        endDate = new JFXDatePicker();
        endDate.setDefaultColor(Color.rgb(244, 218, 0));
        endDate.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        endDate.setPrefSize(240, 40);
        endDate.setLayoutY(220);
        endDate.setLayoutX(40);
        endDate.setOnAction(event -> {
            LocalDate date = endDate.getValue();
        });

        endTime = new JFXTimePicker();
        endTime.setStyle("-fx-font-family: 'Franklin Gothic Medium Cond';" + "-fx-text-fill: white;" + "-fx-font-size: 12pt");
        endTime.setDefaultColor(Color.rgb(244, 218, 0));
        endTime.setPrefSize(140, 40);
        endTime.setLayoutY(220);
        endTime.setLayoutX(300);
        endTime.setOnAction(event -> {
            LocalTime date = endTime.getValue();
        });

        loginPane.getChildren().addAll(exitButton(), title, endDate, endTime, submit, error);
    }

    private void processAddAuction() {
        auctionSelected.setText("Went to auction");
        auctionSelected.getStyleClass().clear();
        auctionSelected.getStyleClass().add("auctionButtonNext");
        auctionSelected.setAlignment(Pos.CENTER);
        auctionSelected.setOnMouseClicked(null);
        try {
            String endYear = "" + endDate.getValue().getYear();
            String endMonth = "" + endDate.getValue().getMonthValue();
            if (endMonth.length() == 1) {
                endMonth = "0" + endMonth;
            }
            String endDay = "" + endDate.getValue().getDayOfMonth();
            if (endDay.length() == 1) {
                endDay = "0" + endDay;
            }
            String timeH = this.endTime.getValue().toString().split(":")[0];
            String timeM = this.endTime.getValue().toString().split(":")[1];
            String endDate = (endMonth + "_" + endDay + "_" + endYear + "_" +
                    timeH + "_" + timeM);

            dataOutputStream.writeUTF("setAuction_" + auctionGoodId + "_" + endDate);
            dataOutputStream.flush();
            auctionGoodsId.add(auctionGoodId);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        popupWindow.close();
        fade(0.5, 10);

    }

    private void backToMainMenu() {
        main.updateFilters = true;
        mainPane.getChildren().remove(Login.currentPane);
        main.initialize(main.location, main.resources);
        mainPane.getChildren().add(mainMenu);
    }


    private void popupMoney(boolean increase) {
        moneyPane = new FlowPane(8, 8);

        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        layout.getStylesheets().add("file:src/main/java/view/css/loginMenu.css");
        moneyPane.setStyle("-fx-background-color: #eceff1;" + "-fx-background-radius: 30px;");
        moneyPane.setPrefWidth(370);
        moneyPane.setPrefHeight(250);
        moneyPane.setAlignment(Pos.CENTER);

        fade(10, 0.5);

        layout.setLayoutX(580);
        layout.setLayoutY(300);
        layout.getChildren().add(moneyPane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);
        layout.getChildren().add(exitPopupMoney());
        moneyPane.getChildren().addAll(moneyField(), confirmMoney(increase));
        popupWindow.showAndWait();
    }

    private NumberField moneyField() {
        moneyField = new NumberField();
        moneyField.setPromptText("$ Money");
        moneyField.setPrefHeight(30);
        moneyField.setPrefWidth(220);
        moneyField.getStyleClass().add("MoneyField");
        return moneyField;
    }

    private Button confirmMoney(boolean increase) {
        Button submit = new Button();
        submit.setText("Submit");
        submit.setPrefSize(220, 40);
        submit.getStyleClass().add("increase");
        submit.setOnMouseClicked(event -> {
            try {
                if (increase) {
                    processIncreaseCredit();
                } else {
                    processWithdraw();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return submit;
    }

    private void processIncreaseCredit() {
        long money = Long.parseLong(moneyField.getText());
        long credit = Long.parseLong(creditLabel.getText().substring(2));
        try {
            dataOutputStream.writeUTF("increase_credit_" + money);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        moneyPane.getChildren().clear();
        ImageView gif = new ImageView(new Image("file:src/main/java/view/image/checkgif.gif"));
        gif.setFitWidth(170);
        gif.setFitHeight(170);
        moneyPane.setVgap(5);
        Button ok = new Button();
        ok.setText("Ok");
        ok.setPrefSize(200, 25);
        ok.getStyleClass().add("ok");
        ok.setOnMouseClicked(event1 -> {
            creditLabel.setText(" $" + (credit + money));
            popupWindow.close();
            fade(0.5, 10);
        });
        moneyPane.getChildren().addAll(gif, ok);
    }


    private void processWithdraw() throws IOException {
        long money = Long.parseLong(moneyField.getText());
        long credit = Long.parseLong(creditLabel.getText().substring(2));
        dataOutputStream.writeUTF("getMinimumCredit");
        dataOutputStream.flush();
        int minimumCredit = Integer.parseInt(dataInputStream.readUTF());
        if (credit - money < minimumCredit) {
            //todo  باید ارور بدی بگی حداقل فلان قدر باید بمونه تو کیف پول
            System.out.println("hooooooooooooy!");
        } else {
            dataOutputStream.writeUTF("Withdraw_" + moneyField.getText());
            dataOutputStream.flush();
            moneyPane.getChildren().clear();
            ImageView gif = new ImageView(new Image("file:src/main/java/view/image/checkgif.gif"));
            gif.setFitWidth(170);
            gif.setFitHeight(170);
            moneyPane.setVgap(5);
            Button ok = new Button();
            ok.setText("Ok");
            ok.setPrefSize(200, 25);
            ok.getStyleClass().add("ok");
            ok.setOnMouseClicked(event1 -> {
                creditLabel.setText(" $" + (credit - money));
                popupWindow.close();
                fade(0.5, 10);
            });
            moneyPane.getChildren().addAll(gif, ok);
        }
    }

    private Button exitPopupMoney() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExit");
        exitButton.setLayoutY(25);
        exitButton.setLayoutX(335);
        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
    }

}
