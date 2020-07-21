package view.FXMLController;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket;
    private DataInputStream dataInputStream;
    private VBox innerChat;
    private ScrollPane scrollPaneChat;
    private MainMenu main;

    public Receiver(Socket socket, VBox innerChat, ScrollPane scrollPaneChat, MainMenu main) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.innerChat = innerChat;
        this.scrollPaneChat = scrollPaneChat;
        this.main = main;
    }

    @Override
    public void run() {
        String answer = "";
        try {
            while (!answer.equals("exit")) {
                answer = dataInputStream.readUTF();
                main.showMessage(innerChat, answer, "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;");
                scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
            }
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
