import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.*;
import view.CommandProcessor;
import view.FXMLController.AdminPanel;
import view.FXMLController.Login;
import view.Purchase;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.*;

public class Server {
    private SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static HashMap<String, String> onlineAccounts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        FileHandler.updateDatabase();
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            System.out.println("Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("a client connected");
            new ClientHandler(clientSocket, onlineAccounts).start();
        }
    }

    public String generateTokenForUser(String username) {
        while (true) {
            byte[] randomBytes = new byte[24];
            secureRandom.nextBytes(randomBytes);
            String token = base64Encoder.encodeToString(randomBytes);
            if (!onlineAccounts.containsKey(token)) {
                onlineAccounts.put(token, username);
//                onlineUsername.add(username);
                return token;
            }
        }
    }

}

class ClientHandler extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private HashMap<String, String> onlineAccounts;
    private Account account;


    public ClientHandler(Socket socket, HashMap<String, String> onlineAccounts) throws IOException {
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.socket = socket;
        this.onlineAccounts = onlineAccounts;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = dataInputStream.readUTF();
                String[] info = request.split("_");
                if (request.startsWith("getAllCompanies")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().allCompanies()));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllGoods")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllGoods()));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_off")) {
                    int id = Integer.parseInt(info[2]);
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getOffWithId(id)));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_product")) {
                    int id = Integer.parseInt(info[2]);
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getProductWithId(id)));
                    dataOutputStream.flush();
                } else if (request.startsWith("edit_profile")) {
                    AccountManager.editPersonalInfo(info[2], info[3], info[4], info[5], info[6], account.getUsername());
                } else if (request.startsWith("remove_off")) {
                    int id = Integer.parseInt(info[2]);
                    SellerManager.removeOff(account.getUsername(), id);
                } else if (request.startsWith("get_category")) {
                    String categoryName = info[2];
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getCategoryByName(categoryName)));
                    dataOutputStream.flush();
                } else if (request.startsWith("create_product")) {
                    Type categoryAttributeType = new TypeToken<HashMap<String, String>>() {
                    }.getType();
                    HashMap<String, String> categoryAttribute = new Gson().fromJson(info[7], categoryAttributeType);
                    SellerManager.addProduct(account.getUsername(), info[2], info[3], Integer.parseInt(info[4]),
                            Long.parseLong(info[5]), info[6], categoryAttribute, info[8], info[9], info[10]);
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAccountByUsername(account.getUsername())));
                    dataOutputStream.flush();
                } else if (request.startsWith("create_off")) {
                    Type goodsIdType = new TypeToken<List<Integer>>() {
                    }.getType();
                    List<Integer> goodsId = new Gson().fromJson(info[2], goodsIdType);
                    Date startDate = AdminPanel.getDateByString(info[3] + " " + info[4]);
                    Date endDate = AdminPanel.getDateByString(info[5] + " " + info[6]);
                    SellerManager.addOff(account.getUsername(), goodsId, startDate, endDate, Integer.parseInt(info[7]));
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAccountByUsername(account.getUsername())));
                } else if (request.startsWith("getAllCategories")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllCategories()));
                    dataOutputStream.flush();
                } else if (request.startsWith("remove_product")) {
                    int id = Integer.parseInt(info[2]);
                    SellerManager.removeProduct(id);
                } else if (request.startsWith("can_register")) {
                    String username = info[2];
                    if (Shop.getShop().getAccountByUsername(username) == null) {
                        dataOutputStream.writeUTF("true");
                    } else {
                        dataOutputStream.writeUTF("false");
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("register")) {
                    AccountManager.register(info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9]);
                } else if (request.startsWith("get_total_price")) {
                   dataOutputStream.writeUTF(new Gson().toJson(BuyerManager.getTotalPrice(account)));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_final_price")) {
                    dataOutputStream.writeUTF(new Gson().toJson(BuyerManager.getPriceAfterApplyOff(account)));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_discount")) {
                    String discount = request.split("_")[1];
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getDiscountWithCode(Integer.parseInt(discount))));
                    dataOutputStream.flush();
                } else if (request.startsWith("can_login")) {
                    Account account = AccountManager.canLogin(info[2], info[3]);
                    if (account == null) {
                        dataOutputStream.writeUTF("false");
                    } else {
                        String token = new Server().generateTokenForUser(account.getUsername());
                        if (account instanceof Buyer) {
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_buyer_" + token);
                        } else if (account instanceof Seller) {
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_seller_" + token);
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_seller_" + token);
                        } else if (account instanceof Supporter) {
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_supporter_" + token);
                        } else {
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_admin_" + token);
                        }
                        onlineAccounts.put(token, account.getUsername());
                        this.account = account;
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("create_category")) {
                    Type categoryAttribute = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> attributes = new Gson().fromJson(info[3], categoryAttribute);
                    AdminManager.addCategory(info[2], attributes);

                } else if (request.startsWith("remove_category")) {
                    Category category = Shop.getShop().getCategoryByName(info[2]);
                    AdminManager.removeCategory(category);
                } else if (request.startsWith("getAllSeller")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllSellers())));
                    dataOutputStream.flush();
                }  else if (request.startsWith("getAllAdmin")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllAdmins())));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllBuyer")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllBuyers())));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllSupporter")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllSupporter())));
                    dataOutputStream.flush();
                } else if (request.startsWith("create_discount")) {
                    Date startDate = AdminPanel.getDateByString(info[2] + " " + info[3]);
                    Date endDate = AdminPanel.getDateByString(info[4] + " " + info[5]);
                    int percent = Integer.parseInt(info[6]);
                    long maxAmount = Long.parseLong(info[7]);
                    int number = Integer.parseInt(info[8]);
                    Type selectedBuyersType = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> selectedBuyers = new Gson().fromJson(info[9], selectedBuyersType);
                    AdminManager.createDiscount(startDate, endDate, percent, maxAmount, number, selectedBuyers);

                } else if (request.startsWith("exit")) {
                    disconnectClient();
                    break;
                }

            }
        } catch (IOException e) {
            System.out.println("connection closed!");
        }
    }

    private void disconnectClient() throws IOException {
        FileHandler.write();
        socket.close();
        System.out.println("connection closed!");
        for (String token : onlineAccounts.keySet()) {
            if (account.getUsername().equals(onlineAccounts.get(token))) {
                onlineAccounts.remove(token);
            }
        }
    }


}
