import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.nio.file.Paths;
import static view.FXML.FXML.mainMenuURL;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
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
