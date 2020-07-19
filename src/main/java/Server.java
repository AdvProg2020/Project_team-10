import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import controller.AccountManager;
import controller.BuyerManager;
import controller.FileHandler;
import controller.SellerManager;
import model.*;
import controller.*;
import model.*;
import view.CommandProcessor;
import view.FXMLController.AdminPanel;
import view.Purchase;

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
                    Date startDate = AdminPanel.getDateByString(info[3] + " " + info[4]);
                    Date endDate = AdminPanel.getDateByString(info[5] + " " + info[6]);

//                    SellerManager.addOff(null, goodsId, startDate, endDate, Integer.parseInt(info[5]));
//                    System.out.println(goodsId);
                    System.out.println("startDate: " + startDate);
                    System.out.println("endDate: " + endDate);
                    System.out.println("percent:" + info[7]);
                } else if (request.equals("getAllCategories")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllCategories()));
                    dataOutputStream.flush();
                } else if (request.startsWith("remove product")) {
                    int id = Integer.parseInt(request.substring(15));
//                    SellerManager.removeProduct(id);
                    System.out.println(id);
                } else if (request.startsWith("can register")) {
                    String username = request.substring(13);
                    if (Shop.getShop().getAccountByUsername(username) == null) {
                        dataOutputStream.writeUTF("true");
                    } else {
                        dataOutputStream.writeUTF("false");
                    }
                    dataOutputStream.flush();
                    System.out.println("username: " + username);
                } else if (request.startsWith("register")) {
                    String[] info = request.split("\\s");
                    AccountManager.register(info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9]);
                } else if (request.startsWith("get total price ")) {
//                   dataOutputStream.writeUTF(new Gson().toJson(BuyerManager.getTotalPrice())); //TODO Add Account
                    dataOutputStream.flush();
                }else if (request.startsWith("get final price ")) {
//                    dataOutputStream.writeUTF(new Gson().toJson(BuyerManager.getPriceAfterApplyOff())); //TODO Add Account
                    dataOutputStream.flush();
                }else if (request.startsWith("get discount ")) {
                    String discount = request.split("\\s")[1];
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getDiscountWithCode(Integer.parseInt(discount))));
                    dataOutputStream.flush();
                }else if (request.startsWith("can login")) {
                    String[] info = request.split("\\s");
                    Account account = AccountManager.canLogin(info[2], info[3]);
                    if (account == null) {
                        dataOutputStream.writeUTF("false");
                    } else {
                        if (account instanceof Buyer) {
                            dataOutputStream.writeUTF("true " + new Gson().toJson(account) + " buyer");
                        } else if (account instanceof Seller) {
                            dataOutputStream.writeUTF("true " + new Gson().toJson(account) + " seller");
                        }  else if (account instanceof Supporter) {
                            dataOutputStream.writeUTF("true " + new Gson().toJson(account) + " supporter");
                        }else {
                            dataOutputStream.writeUTF("true " + new Gson().toJson(account) + " admin");
                        }
                        //TODO add to hashMap
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("create category ")) {
                    String[] info = request.split("\\s");
                    Type categoryAttribute = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> attributes = new Gson().fromJson(info[3], categoryAttribute);
                    AdminManager.addCategory(info[2], attributes);

                } else if (request.startsWith("remove category ")) {
                    String[] info = request.split("\\s");
                    Category category = Shop.getShop().getCategoryByName(info[2]);
                    AdminManager.removeCategory(category);
                } else if (request.startsWith("create discount ")) {
                    String[] info = request.split("\\s");
                    Date startDate = AdminPanel.getDateByString(info[2] + " " + info[3]);
                    Date endDate = AdminPanel.getDateByString(info[4] + " " + info[5]);
                    int percent = Integer.parseInt(info[6]);
                    long maxAmount = Long.parseLong(info[7]);
                    int number = Integer.parseInt(info[8]);
                    Type selectedBuyersType = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> selectedBuyers = new Gson().fromJson(info[9], selectedBuyersType);
                    AdminManager.createDiscount(startDate, endDate, percent, maxAmount, number, selectedBuyers);

                }else if (request.equals("exit")) {
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
