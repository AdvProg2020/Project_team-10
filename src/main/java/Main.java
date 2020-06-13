import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.menus.MainMenu;
import view.menus.Menu;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

import static view.FXML.FXML.goodPageURL;
import static view.FXML.FXML.mainMenuURL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
//        Scanner scanner = new Scanner(System.in);
//        MainMenu mainMenu = new MainMenu();
//        Menu.setScanner(scanner);
//        mainMenu.show();
//        mainMenu.execute();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Paths.get(goodPageURL).toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
