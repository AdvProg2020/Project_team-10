import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AccountManager;
import controller.FileHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Good;
import model.Seller;
import model.Shop;
import model.Supporter;
import view.menus.MainMenu;
import view.menus.Menu;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.media.MediaPlayer.INDEFINITE;
import static view.FXML.FXML.mainMenuURL;

public class Client extends Application {

    public static void main(String[] args) {
        System.out.println("svs");
        launch(args);
//        Scanner scanner = new Scanner(System.in);
//        MainMenu mainMenu = new MainMenu();
//        Menu.setScanner(scanner);
//        mainMenu.show();
//        mainMenu.execute();
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL url = Paths.get(mainMenuURL).toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        AudioClip audio = new AudioClip("file:src/main/java/view/image/Free-ambient-background-music.mp3");
        audio.setVolume(0.2f);
//        audio.play();
    }
}
