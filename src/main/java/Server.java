import com.google.gson.Gson;
import controller.FileHandler;
import model.Shop;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    public static void main(String[] args) throws IOException {
        UUID token = UUID.randomUUID();
        System.out.println(token);
        FileHandler.updateDatabase();
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket clientSocket = serverSocket.accept();
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        String allProductsJson = new Gson().toJson(Shop.getShop().getAllGoods());
        dataOutputStream.writeUTF(allProductsJson);
        dataOutputStream.flush();

        dataOutputStream.writeUTF(Shop.getShop().allCompanies().toString());
        System.out.println(Shop.getShop().allCompanies().toString());
        while (true) {
        }
    }


}
