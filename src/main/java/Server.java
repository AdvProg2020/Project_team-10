import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import controller.AccountManager;
import controller.FileHandler;
import controller.SellerManager;
import model.Shop;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
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
                    System.out.println(id);
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getOffWithId(id)));
                    dataOutputStream.flush();
                } else if (request.startsWith("get product")) {
                    int id = Integer.parseInt(request.substring(12));
                    System.out.println(id);
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getProductWithId(id)));
                    dataOutputStream.flush();
                } else if (request.startsWith("edit profile")) {
                    String[] info = request.split("\\s");
//                    AccountManager.editPersonalInfo(info[2], info[3], info[4], info[5], info[6], null);
                    System.out.println(info[2]);
                    System.out.println(info[3]);
                    System.out.println(info[4]);
                    System.out.println(info[5]);
                    System.out.println(info[6]);
                } else if (request.startsWith("remove off")) {
                    int id = Integer.parseInt(request.substring(11));
                    System.out.println("off id: " + id);
//                    SellerManager.removeOff(null, id);
                } else if (request.startsWith("get category")) {
                    String categoryName = request.substring(13);
                    System.out.println(categoryName);
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getCategoryByName(categoryName)));
                    dataOutputStream.flush();
                } else if (request.startsWith("create product")) {
                    String[] info = request.split("\\s");
                    Type categoryAttributeType = new TypeToken<HashMap<String, String>>() {
                    }.getType();
                    HashMap<String, String> categoryAttribute = new Gson().fromJson(info[7], categoryAttributeType);
//                    SellerManager.addProduct(null, info[2], info[3], Integer.parseInt(info[4]),
//                            Long.parseLong(info[5]), info[6], categoryAttribute, info[8], info[9], info[10]);
                    System.out.println(info[2]);
                    System.out.println(info[3]);
                    System.out.println(info[4]);
                    System.out.println(info[5]);
                    System.out.println(info[6]);
                    System.out.println(categoryAttribute);
                    System.out.println(info[8]);
                    System.out.println(info[9]);
                    System.out.println(info[10]);
                } else if (request.startsWith("create off")) {
                    String[] info = request.split("\\s");
                    Type goodsIdType = new TypeToken<List<Integer>>() {
                    }.getType();
                    List<Integer> goodsId = new Gson().fromJson(info[2], goodsIdType);
                    Type startDateType = new TypeToken<Date>() {
                    }.getType();
                    Date startDate = new Gson().fromJson(info[3], startDateType);
                    Type endDateType = new TypeToken<Date>() {
                    }.getType();
                    Date endDate = new Gson().fromJson(info[4], endDateType);

//                    SellerManager.addOff(null, goodsId, startDate, endDate, Integer.parseInt(info[5]));
                    System.out.println(goodsId);
                    System.out.println(startDate);
                    System.out.println(endDate);
                    System.out.println(info[5]);
                } else if (request.equals("getAllCategories")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllCategories()));
                    dataOutputStream.flush();
                } else if (request.startsWith("remove product")) {
                    int id = Integer.parseInt(request.substring(15));
//                    SellerManager.removeProduct(id);
                    System.out.println(id);
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
