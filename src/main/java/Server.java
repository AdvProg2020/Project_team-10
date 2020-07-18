import com.google.gson.Gson;
import controller.FileHandler;
import model.Shop;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            System.out.println("Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("a client connected");
            new ClientHandler(clientSocket).start();
        }
    }

}

class ClientHandler extends Thread {
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    public ClientHandler(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.socket = socket;
    }

    @Override
    public void run() {
        FileHandler.updateDatabase();
        while (true) {
            try {
                String request = dataInputStream.readUTF();
                if (request.equals("getAllCompanies")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().allCompanies()));
                    dataOutputStream.flush();
                } else if (request.equals("getAllGoods")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllGoods()));
                    dataOutputStream.flush();
                } else if (request.startsWith("get off")) {
                    int id = Integer.parseInt(request.substring(8));
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getOffWithId(id)));
                    dataOutputStream.flush();
                } else if (request.startsWith("get product")) {
                    int id = Integer.parseInt(request.substring(12));
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getProductWithId(id)));
                    dataOutputStream.flush();
                } else if (request.equals("getAllCategories")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllCategories()));
                    dataOutputStream.flush();
                } else if (request.equals("exit")) {
                    FileHandler.write();
                    dataOutputStream.writeUTF("successfully logged out");
                    dataOutputStream.flush();
                    socket.close();
                    System.out.println("connection closed!");
                    break;
                }



            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
