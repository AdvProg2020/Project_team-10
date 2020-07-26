package view.FXMLController;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.Map;

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
        String answer;
        try {
            while (!(answer = dataInputStream.readUTF()).equals("disconnect_buyer")) {
                System.out.println("from supporter: " + answer);
                String finalAnswer = answer;
                Platform.runLater(() -> main.showMessage(innerChat, finalAnswer, "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;"));
                scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
                System.out.println("show done!");
            }
            System.out.println("the buyer disconnected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class SupporterReceiver extends Thread {
    private DataInputStream dataInputStream;
    private SupporterPanel supporterPanel;
    private Map<String, FlowPane> buyerToChatPane;

    public SupporterReceiver(DataInputStream dataInputStream, SupporterPanel supporterPanel, Map<String, FlowPane> buyerToChatPane) {
        this.dataInputStream = dataInputStream;
        this.supporterPanel = supporterPanel;
        this.buyerToChatPane = buyerToChatPane;
    }

    @Override
    public void run() {
        String answer;
        try {
            while (!(answer = dataInputStream.readUTF()).equals("disconnect_supporter")) {
                System.out.println("Waiting for message from buyer...");
                System.out.println("answer: " + answer);
                ScrollPane scrollPaneChat = ((ScrollPane) buyerToChatPane.get(answer.split("_")[0]).getChildren().get(2));
                System.out.println("from buyer: " + answer);
                String finalAnswer = answer.split("_")[1];
                Platform.runLater(() -> supporterPanel.showMessage(((VBox) scrollPaneChat.getContent()), finalAnswer,
                        "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;"));
                scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
            }
            System.out.println("the supporter disconnected.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setBuyerToChatPane(Map<String, FlowPane> buyerToChatPane) {
        this.buyerToChatPane = buyerToChatPane;
    }
}

class GroupChatReceiver extends Thread {
    private DataInputStream dataInputStream;
    private VBox innerChat;
    private ScrollPane scrollPaneChat;
    private MainMenu main;

    public GroupChatReceiver(DataInputStream dataInputStream, VBox innerChat, ScrollPane scrollPaneChat, MainMenu main) {
        this.dataInputStream = dataInputStream;
        this.innerChat = innerChat;
        this.scrollPaneChat = scrollPaneChat;
        this.main = main;
    }

    @Override
    public void run() {
        String answer;
        try {
            while (!(answer = dataInputStream.readUTF()).equals("disconnect_auction")) {
                String message = answer.split("_")[0] + " : " + answer.split("_")[1];
                System.out.println("97-  " + message);
                Platform.runLater(() -> main.showMessage(innerChat, message, "-fx-background-color: #b9ecff;-fx-text-fill: black;-fx-background-radius: 5;"));
                scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
                System.out.println("show done!");
            }
            System.out.println("the buyer disconnected from the auction");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
