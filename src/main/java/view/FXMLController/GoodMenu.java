package view.FXMLController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import controller.AccountManager;
import controller.GoodsManager;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import view.NumberField;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;

import static javafx.scene.paint.Color.color;

public class GoodMenu {
    private AnchorPane mainPane;
    public ScrollPane goodPageScrollPane;
    private Stage popupWindow;
    private TextField title;
    private TextField content;
    static double initx;
    static double inity;
    static int height;
    static int width;
    //    public String path = currentGood.getImagePath();
//    static Scene initialScene,View;
    static double offSetX, offSetY, zoomlvl;
    private NumberField scoreField;
    private Label error;
    private boolean isPlaying;
    private Good currentGood;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private Account onlineAccount;


    public void setCurrentGood(Good currentGood) {
        this.currentGood = currentGood;
    }

    public GoodMenu(AnchorPane mainPane, Socket socket, Account onlineAccount) throws IOException {
        this.mainPane = mainPane;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.onlineAccount = onlineAccount;
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

    private VBox initView() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Image source = null;
        try {
            source = new Image(new FileInputStream(currentGood.getImagePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView image = new ImageView(source);
        double ratio = source.getWidth() / source.getHeight();

        if (500 / ratio < 500) {
            width = 500;
            height = (int) (500 / ratio);
        } else if (500 * ratio < 500) {
            height = 500;
            width = (int) (500 * ratio);
        } else {
            height = 500;
            width = 500;
        }
        image.setPreserveRatio(false);
        image.setFitWidth(width);
        image.setFitHeight(height);
        height = (int) source.getHeight();
        width = (int) source.getWidth();
        HBox zoom = new HBox(10);
        zoom.setAlignment(Pos.CENTER);

        Slider zoomLvl = new Slider();
        zoomLvl.setMax(4);
        zoomLvl.setMin(1);
        zoomLvl.setMaxWidth(200);
        zoomLvl.setMinWidth(200);
        Label hint = new Label("Zoom Level");
        Label value = new Label("1.0");

        offSetX = width / 2;
        offSetY = height / 2;


        zoom.getChildren().addAll(hint, zoomLvl, value);

        Slider Hscroll = new Slider();
        Hscroll.setMin(0);
        Hscroll.setMax(width);
        Hscroll.setMaxWidth(image.getFitWidth());
        Hscroll.setMinWidth(image.getFitWidth());
        Hscroll.setTranslateY(-20);
        Slider Vscroll = new Slider();
        Vscroll.setMin(0);
        Vscroll.setMax(height);
        Vscroll.setMaxHeight(image.getFitHeight());
        Vscroll.setMinHeight(image.getFitHeight());
        Vscroll.setOrientation(Orientation.VERTICAL);
        Vscroll.setTranslateX(20);


        BorderPane imageView = new BorderPane();
        BorderPane.setAlignment(Hscroll, Pos.CENTER);
        BorderPane.setAlignment(Vscroll, Pos.CENTER_LEFT);
        Hscroll.valueProperty().addListener(e -> {
            offSetX = Hscroll.getValue();
            zoomlvl = zoomLvl.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            value.setText(newValue + "");
            if (offSetX < (width / newValue) / 2) {
                offSetX = (width / newValue) / 2;
            }
            if (offSetX > width - ((width / newValue) / 2)) {
                offSetX = width - ((width / newValue) / 2);
            }

            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        Vscroll.valueProperty().addListener(e -> {
            offSetY = height - Vscroll.getValue();
            zoomlvl = zoomLvl.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            value.setText(newValue + "");
            if (offSetY < (height / newValue) / 2) {
                offSetY = (height / newValue) / 2;
            }
            if (offSetY > height - ((height / newValue) / 2)) {
                offSetY = height - ((height / newValue) / 2);
            }
            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        imageView.setCenter(image);
        imageView.setTop(Hscroll);
        imageView.setRight(Vscroll);
        zoomLvl.valueProperty().addListener(e -> {
            zoomlvl = zoomLvl.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            value.setText(newValue + "");
            if (offSetX < (width / newValue) / 2) {
                offSetX = (width / newValue) / 2;
            }
            if (offSetX > width - ((width / newValue) / 2)) {
                offSetX = width - ((width / newValue) / 2);
            }
            if (offSetY < (height / newValue) / 2) {
                offSetY = (height / newValue) / 2;
            }
            if (offSetY > height - ((height / newValue) / 2)) {
                offSetY = height - ((height / newValue) / 2);
            }
            Hscroll.setValue(offSetX);
            Vscroll.setValue(height - offSetY);
            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        imageView.setCursor(Cursor.OPEN_HAND);
        image.setOnMousePressed(e -> {
            initx = e.getSceneX();
            inity = e.getSceneY();
            imageView.setCursor(Cursor.CLOSED_HAND);
        });
        image.setOnMouseReleased(e -> {
            imageView.setCursor(Cursor.OPEN_HAND);
        });
        image.setOnMouseDragged(e -> {
            Hscroll.setValue(Hscroll.getValue() + (initx - e.getSceneX()));
            Vscroll.setValue(Vscroll.getValue() - (inity - e.getSceneY()));
            initx = e.getSceneX();
            inity = e.getSceneY();
        });
        root.getChildren().addAll(imageView, zoom);
        return root;
    }

    public void changePane() throws IOException {

        ImageView videoShow = new ImageView();
        videoShow.getStyleClass().add("video");
        videoShow.setLayoutY(200);
        videoShow.setLayoutX(635);
        videoShow.setFitHeight(30);
        videoShow.setFitWidth(30);
        videoShow.setOnMouseClicked(event -> popupVideo());

        dataOutputStream.writeUTF("visitIncrease_" + currentGood.getId());
        dataOutputStream.flush();

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

        ImageView zoom = new ImageView();
        zoom.getStyleClass().add("zoom");
        zoom.setLayoutY(200);
        zoom.setLayoutX(600);
        zoom.setFitHeight(30);
        zoom.setFitWidth(30);
        zoom.setOnMouseClicked(event -> popupZoom());

        Label productName = new Label();
        productName.getStyleClass().add("productNameLabel");
        productName.setLayoutX(600);
        productName.setLayoutY(10);

        HBox hBox = covertScoreToStar((int) currentGood.calculateAverageRate());
        hBox.setLayoutX(600);
        hBox.setLayoutY(160);

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
            try {
                dataOutputStream.writeUTF("addToCart_" + currentGood.getId());
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            ((Buyer) onlineAccount).getCart().add(currentGood);
//            currentGood.getGoodsInBuyerCart().put(onlineAccount.getUsername(), 1);
        });

        ImageView rate = new ImageView();
        rate.setLayoutY(62);
        rate.setLayoutX(705);
        rate.setFitHeight(25);
        rate.setFitWidth(25);
        rate.getStyleClass().add("rate");
        rate.setOnMouseClicked(event -> popupScore());

        if (!(onlineAccount instanceof Buyer) || ((Buyer) onlineAccount).getCart().contains(currentGood)) {
            addToCart.setDisable(true);
        }
        if (!canScore()) {
            rate.setDisable(true);
        }

        innerPane.getChildren().addAll(zoom, videoShow, goodImage, productName, productPrice, hBox, isAvailable, addToCart, rate, hLine, vLine);
        goodImage.setFitWidth(500);
        goodImage.setFitHeight(500);
        goodImage.setLayoutX(50);
        goodImage.setLayoutY(50);
        dataOutputStream.writeUTF("get_product_" + currentGood.getId());
        dataOutputStream.flush();
        Type productType = new TypeToken<Good>() {
        }.getType();
        Good good = new Gson().fromJson(dataInputStream.readUTF(), productType);
        if (good.getNumber() > 0) {
            isAvailable.setText("Available");
        } else {
            isAvailable.setText("Not available");
            addToCart.setDisable(true);
        }
        productName.setText(currentGood.getName());
        productPrice.setText("$" + currentGood.getPrice());
        mainPane.getStylesheets().add("file:src/main/java/view/css/goodPage.css");
        tabPane(innerPane);
//        similarProducts(innerPane);
        scrollPack.getChildren().add(goodPageScrollPane);
        mainPane.getChildren().add(scrollPack);
        innerPane.setPrefSize(1470, 640);
        Login.currentPane = scrollPack;

    }

    private void similarProducts(AnchorPane innerPane) {
        HBox goods = new HBox();
        try {
            dataOutputStream.writeUTF("get_category_" + currentGood.getCategory());
            dataOutputStream.flush();
            Type categoryType = new TypeToken<Category>() {
            }.getType();
            Category category = new Gson().fromJson(dataInputStream.readUTF(), categoryType);

            for (Good good : category.getGoods()) {
                VBox productBox = new VBox();
                productBox.setPrefWidth(180);
                productBox.setPrefHeight(200);
                productBox.getStyleClass().add("vBoxInMainMenu");

                ImageView logoImage = new ImageView(new Image("file:" + good.getImagePath()));
                logoImage.setFitHeight(120);
                logoImage.setFitWidth(120);
                Label name = new Label(good.getName());
                Label price = new Label("$" + good.getPrice() + "");
                Label number = new Label(good.getGoodsInBuyerCart().get(onlineAccount.getUsername()) + "x");
                name.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 16px;");
                price.setStyle("-fx-font-family: 'Bahnschrift SemiBold SemiConden';" + " -fx-font-size: 16px;" + "-fx-font-weight: bold;");
                number.setStyle("-fx-font-family: 'Myriad Pro';" + " -fx-font-size: 16px;");
                productBox.setAlignment(Pos.CENTER);
                productBox.getChildren().addAll(logoImage, name, price, number);
                goods.getChildren().add(productBox);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        goods.setLayoutX(50);
//        goods.setLayoutY(1000);
        AnchorPane anchorPane = new AnchorPane(goods);
        anchorPane.setPrefWidth(1360);
        anchorPane.setPrefHeight(200);
        ScrollPane similarProductsScrollPane = new ScrollPane(anchorPane);
        similarProductsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        similarProductsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        similarProductsScrollPane.setLayoutX(50);
        similarProductsScrollPane.setLayoutY(1000);
        innerPane.getChildren().add(similarProductsScrollPane);
    }

    private boolean canScore() {
        if (onlineAccount instanceof Buyer) {
            for (Good good : ((Buyer) onlineAccount).getGoods()) {
                if (good.getId() == currentGood.getId()) {
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
        scorePane.setPrefWidth(370);
        scorePane.setPrefHeight(250);

        fade(10, 0.5);

        layout.setLayoutX(580);
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

    private void popupVideo() {


        Media media = new Media(new File(currentGood.getVideoPath()).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(701);
        mediaView.setStyle("-fx-background-radius: 20");
        mediaView.setOnMouseClicked(event -> {
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
            } else {
                mediaPlayer.play();
                isPlaying = true;
            }
        });

        VBox pane = new VBox(5);
        pane.getStylesheets().add("file:src/main/java/view/css/loginMenu.css");
        popupWindow = new Stage();
        scoreField = new NumberField();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        pane.setStyle("-fx-background-color: none;" + "-fx-background-radius: 10px;");
        pane.setPrefWidth(410);
        pane.setPrefHeight(710);
        pane.setAlignment(Pos.CENTER);

        fade(10, 0.5);

        layout.setLayoutX(380);
        layout.setLayoutY(90);
        layout.getChildren().add(pane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);

        pane.getChildren().addAll(exitPopupVideo(mediaPlayer), mediaView);
        popupWindow.showAndWait();
    }

    private void popupZoom() {
        AnchorPane pane = new AnchorPane();
        pane.getStylesheets().add("file:src/main/java/view/css/loginMenu.css");
        popupWindow = new Stage();
        scoreField = new NumberField();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        AnchorPane layout = new AnchorPane();
        Scene scene = new Scene(layout);
        popupWindow.setMaximized(true);

        layout.setStyle("-fx-background-color: none;");
        pane.setStyle("-fx-background-color: none;" + "-fx-background-radius: 30px;");
        pane.setPrefWidth(600);
        pane.setPrefHeight(650);

        fade(10, 0.5);

        layout.setLayoutX(450);
        layout.setLayoutY(120);
        layout.getChildren().add(pane);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1500.0);
        dropShadow.setHeight(1500);
        dropShadow.setWidth(1500);
        dropShadow.setColor(color(0.4, 0.5, 0.5));
        layout.setEffect(dropShadow);

        popupWindow.setScene(scene);
        popupWindow.initStyle(StageStyle.TRANSPARENT);
        popupWindow.getScene().setFill(Color.TRANSPARENT);

        VBox zoom = initView();
        zoom.setLayoutX(38);
        zoom.setLayoutY(5);
        pane.getChildren().addAll(zoom, exitPopupExit());
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
        VBox comments = new VBox();
        for (Comment comment : currentGood.getComments()) {
            VBox commentVBox = new VBox(4);
            commentVBox.setPadding(new Insets(7, 0, 0, 0));
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
        exitButton.setLayoutY(25);
        exitButton.setLayoutX(335);
        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
    }

    private Button exitPopupVideo(MediaPlayer mediaPlayer) {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExitVideo");
        exitButton.setLayoutY(1);
        exitButton.setLayoutX(560);
        exitButton.setOnAction(event -> {
            mediaPlayer.pause();
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
    }

    private Button exitPopupExit() {
        Button exitButton = new Button();
        exitButton.getStyleClass().add("btnExitPop");
        exitButton.setLayoutY(1);
        exitButton.setLayoutX(560);
        exitButton.setOnAction(event -> {
            popupWindow.close();
            fade(0.5, 10);
        });
        return exitButton;
    }

    private Button addComment() {
        Button addComment = new Button();
        addComment.setText("ADD");
        addComment.setPrefSize(1450, 40);
        ImageView plus = new ImageView(new Image("file:src/main/java/view/image/plus.png"));
        plus.setFitWidth(30);
        plus.setFitHeight(30);
        addComment.getStyleClass().add("buttonComment");
        addComment.setGraphic(plus);
        addComment.setOnMouseClicked(event -> popupComment());
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
            currentGood.getComments().add(new Comment(onlineAccount.getUsername(),
                    currentGood.getId(), "" + content.getText(), "" + title.getText()));
        });
        return submit;
    }

    private Button confirmScore() {
        Button submit = new Button();
        submit.setText("OK");
        submit.setPrefSize(200, 40);
        submit.setLayoutX(85);
        submit.setLayoutY(135);
        submit.getStyleClass().add("login");
        submit.setOnMouseClicked(event -> {
            if (Integer.parseInt(scoreField.getText()) > 5 || Integer.parseInt(scoreField.getText()) < 0) {
                error.setText("only between 0 - 5");
                error.setStyle("-fx-text-fill: darkred");
            } else {
                popupWindow.close();
                fade(0.5, 10);
                currentGood.getAllScores().add(Integer.parseInt(scoreField.getText()));
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
        scoreField.setLayoutX(85);
        scoreField.setLayoutY(70);
        scoreField.setPrefHeight(50);
        scoreField.setPrefWidth(200);
        scoreField.getStyleClass().add("scoreField");
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
        error.setLayoutY(185);
        return error;
    }


}