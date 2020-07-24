package view.FXMLController;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.*;

public class BuyerReceiver extends Thread {
    private DataInputStream dataInputStream;
    private VBox innerChat;
    private ScrollPane scrollPaneChat;
    private MainMenu main;

    public BuyerReceiver(DataInputStream dataInputStream, VBox innerChat, ScrollPane scrollPaneChat, MainMenu main) {
        this.dataInputStream = dataInputStream;
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
                System.out.println("from supporter: " + answer);
                String finalAnswer = answer;
                Platform.runLater(() -> main.showMessage(innerChat, finalAnswer, "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;"));
                scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
                System.out.println("show done!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class SupporterReceiver extends Thread {
    private DataInputStream dataInputStream;
    private VBox innerChat;
    private ScrollPane scrollPaneChat;
    private SupporterPanel supporterPanel;

    public SupporterReceiver(DataInputStream dataInputStream, VBox innerChat, ScrollPane scrollPaneChat, SupporterPanel supporterPanel) {
        this.dataInputStream = dataInputStream;
        this.innerChat = innerChat;
        this.scrollPaneChat = scrollPaneChat;
        this.supporterPanel = supporterPanel;
    }

    @Override
    public void run() {
        String answer = "";
        try {
            while (!answer.equals("exit")) {
                answer = dataInputStream.readUTF();
                System.out.println("from buyer: " + answer);
                String finalAnswer = answer;
                Platform.runLater(() -> supporterPanel.showMessage(innerChat, finalAnswer, "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;"));
                scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
