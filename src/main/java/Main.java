import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.FXMLController.MainMenu;

import java.net.URL;
import java.nio.file.Paths;
import static view.FXML.FXML.mainMenuURL;

public class Main extends Application {

    public static double xDisplay,yDisplay;

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
        URL url = Paths.get(mainMenuURL).toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        xDisplay = scene.getWidth();
        yDisplay = scene.getHeight();
        stage.setScene(scene);
        stage.show();
    }
}
