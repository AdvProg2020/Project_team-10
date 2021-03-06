
import Bank.BankClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.*;
import view.FXMLController.AdminPanel;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Server {
    private SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static HashMap<String, String> onlineAccounts = new HashMap<>();
    private static HashMap<String, DataOutputStream> accountsToSockets = new HashMap<>();


    public static void main(String[] args) throws IOException {
        FileHandler.updateDatabase();
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            System.out.println("Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("a client connected");
            new ClientHandler(clientSocket, onlineAccounts, accountsToSockets).start();
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
    private HashMap<String, DataOutputStream> accountsToOutPuts;
    private Account account = new Buyer("temp");


    public ClientHandler(Socket socket, HashMap<String, String> onlineAccounts,
                         HashMap<String, DataOutputStream> accountsToSockets) throws IOException {
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.socket = socket;
        this.onlineAccounts = onlineAccounts;
        this.accountsToOutPuts = accountsToSockets;
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
                } else if (request.startsWith("remove_account")) {
                    AdminManager.deleteAccount(Shop.getShop().getAccountByUsername(info[2]));
                } else if (request.startsWith("sendFile_")) {
                    dataOutputStream.writeUTF(receiveFile(request));
                    dataOutputStream.flush();
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
                    Date startDate = AdminPanel.getDateByString(info[3]);
                    Date endDate = AdminPanel.getDateByString(info[4]);
                    SellerManager.addOff(account.getUsername(), goodsId, startDate, endDate, Integer.parseInt(info[5]));
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAccountByUsername(account.getUsername())));
                    dataOutputStream.flush();
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
                    Account account = AccountManager.register(info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9]);
                    System.out.println(account.getUsername());
                    if (account instanceof Buyer || account instanceof Seller) {
                        String accountId = handleBankClient("create_account " + info[4] + " " + info[5] + " " + info[1] + " " + info[2] + " " + info[2]);
                        account.setBankAccountId(accountId);
                        String token = handleBankClient("get_token " + account.getUsername() + " " + account.getPassword());
                        String receiptId = handleBankClient("create_receipt " + token + " deposit 10000000 -1 " + accountId + " description");
                        System.out.println("create_receipt " + token + " deposit 10000000 -1 " + accountId + " description");
                        System.out.println(handleBankClient("pay " + receiptId));
                    }
                } else if (request.startsWith("get_total_price")) {
                    System.out.println((BuyerManager.getTotalPrice(account)));
                    dataOutputStream.writeUTF(BuyerManager.getTotalPrice(account) + "");
                    dataOutputStream.flush();
                } else if (request.startsWith("get_final_price")) {
                    dataOutputStream.writeUTF(BuyerManager.getPriceAfterApplyOff(account) + "");
                    dataOutputStream.flush();
                } else if (request.startsWith("get_discount")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getDiscountWithCode(Integer.parseInt(info[2]))));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllDiscount")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAllDiscounts()));
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
                        } else if (account instanceof Supporter) {
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_supporter_" + token);
                        } else {
                            dataOutputStream.writeUTF("true_" + new Gson().toJson(account) + "_admin_" + token);
                        }
                        onlineAccounts.put(token, account.getUsername());
                        accountsToOutPuts.put(account.getUsername(), dataOutputStream);
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
                } else if (request.startsWith("getAllSellers")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllSellers())));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllAdmins")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllAdmins())));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllBuyers")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllBuyers())));
                    dataOutputStream.flush();
                } else if (request.startsWith("getAllSupporters")) {
                    dataOutputStream.writeUTF(new Gson().toJson((Shop.getShop().getAllSupporters())));
                    dataOutputStream.flush();
                } else if (request.startsWith("create_discount")) {
                    Date startDate = AdminPanel.getDateByString(info[2]);
                    Date endDate = AdminPanel.getDateByString(info[3]);
                    int percent = Integer.parseInt(info[4]);
                    long maxAmount = Long.parseLong(info[5]);
                    int number = Integer.parseInt(info[6]);
                    Type selectedBuyersType = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> selectedBuyers = new Gson().fromJson(info[7], selectedBuyersType);
                    AdminManager.createDiscount(startDate, endDate, percent, maxAmount, number, selectedBuyers);
                } else if (request.startsWith("logout")) {
                    offlineAccount();
                    account = new Buyer("temp");
                } else if (request.startsWith("setAuction_")) {
                    Good good = Shop.getShop().getProductWithId(Integer.parseInt(info[1]));
                    LocalDate date = LocalDate.of(Integer.parseInt(info[4]), Integer.parseInt(info[2]), Integer.parseInt(info[3]));

                    LocalTime time = LocalTime.of(Integer.parseInt(info[5]), Integer.parseInt(info[6]));
                    LocalDateTime localdatetime = LocalDateTime.of(date, time);
                    System.out.println(localdatetime);
                    SellerManager.createAuction(good, localdatetime);
                } else if (request.startsWith("getAllAuction")) {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getShop().getAuctionGoods()));
                    dataOutputStream.flush();
                } else if (request.startsWith("isOnlineAccount_")) {
                    String isOnline;
                    if (onlineAccounts.containsValue(info[1])) {
                        isOnline = "true";
                    } else {
                        isOnline = "false";
                    }
                    dataOutputStream.writeUTF(isOnline);
                    dataOutputStream.flush();
                } else if (request.startsWith("getOnlineSupporters")) {
                    ArrayList<Supporter> onlineSupporters = new ArrayList<>();
                    for (String username : onlineAccounts.values()) {
                        Account account = Shop.getShop().getAccountByUsername(username);
                        if (account instanceof Supporter) {
                            onlineSupporters.add(((Supporter) account));
                        }
                    }
                    dataOutputStream.writeUTF(new Gson().toJson(onlineSupporters));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_supporter")) {
                    Supporter supporter = ((Supporter) Shop.getShop().getAccountByUsername(info[2]));
                    dataOutputStream.writeUTF(new Gson().toJson(supporter));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_buyer")) {
                    Buyer buyer = ((Buyer) Shop.getShop().getAccountByUsername(info[2]));
                    dataOutputStream.writeUTF(new Gson().toJson(buyer));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_admin")) {
                    Admin admin = ((Admin) Shop.getShop().getAccountByUsername(info[2]));
                    dataOutputStream.writeUTF(new Gson().toJson(admin));
                    dataOutputStream.flush();
                } else if (request.startsWith("get_seller")) {
                    Seller seller = ((Seller) Shop.getShop().getAccountByUsername(info[2]));
                    dataOutputStream.writeUTF(new Gson().toJson(seller));
                    dataOutputStream.flush();
                } else if (request.startsWith("add_to_buyers")) {
                    Supporter supporter = ((Supporter) Shop.getShop().getAccountByUsername(info[3]));
                    supporter.getBuyersToMessages().put(info[4], new ArrayList<>());
                } else if (request.startsWith("from_buyer")) {
                    Supporter supporter = ((Supporter) Shop.getShop().getAccountByUsername(info[4]));
                    if (supporter.getBuyersOnPage().contains(info[2])) {
                        DataOutputStream dataOutputStream = accountsToOutPuts.get(info[4]);
                        dataOutputStream.writeUTF(info[2] + "_" + info[5]);
                        dataOutputStream.flush();
                        System.out.println("message: " + info[5] + " from " + account.getUsername() + " to " + info[4] + " sent.");
                    } else {
                        supporter.getBuyersToMessages().get(info[2]).add(info[5]);
                    }
                } else if (request.startsWith("from_supporter")) {
                    System.out.println("from supporter to " + info[3] + " : " + info[4]);
                    DataOutputStream dataOutputStream = accountsToOutPuts.get(info[3]);
                    dataOutputStream.writeUTF(info[4]);
                    dataOutputStream.flush();
                } else if (request.startsWith("getAuctionPrice_")) {
                    dataOutputStream.writeUTF("" + Shop.getShop().getAuctionWithId(Integer.parseInt(info[1])).getPrice() +
                            "_" + account.getCredit());
                    dataOutputStream.flush();
                } else if (request.startsWith("setAuctionPrice_")) {
                    Auction auction = Shop.getShop().getAuctionWithId(Integer.parseInt(info[2]));
                    auction.setPrice(Long.parseLong(info[1]));
                    auction.setBuyerUsername(account.getUsername());
                } else if (request.startsWith("disconnect_buyer")) {
                    dataOutputStream.writeUTF("disconnect_buyer");
                    dataOutputStream.flush();
                    Supporter supporter = ((Supporter) Shop.getShop().getAccountByUsername(info[2]));
                    supporter.getBuyersToMessages().remove(account.getUsername());
                    System.out.println("buyer: " + account.getUsername() + " disconnected to supporter: " + supporter.getUsername());
                } else if (request.startsWith("disconnect_supporter")) {
                    dataOutputStream.writeUTF("disconnect_supporter");
                    dataOutputStream.flush();
                } else if (request.startsWith("clear_messages_of")) {
                    ((Supporter) account).getBuyersToMessages().get(info[3]).clear();
                } else if (request.startsWith("page_")) {
                    ((Supporter) account).getBuyersOnPage().add(info[1]);
                } else if (request.startsWith("addToCart_")) {
                    Good good = Shop.getShop().getProductWithId(Integer.parseInt(info[1]));
                    ((Buyer) account).getCart().add(good);
                    good.getGoodsInBuyerCart().put(account.getUsername(), 1);
                } else if (request.startsWith("getGoodInCart")) {
                    dataOutputStream.writeUTF(new Gson().toJson(((Buyer) account).getCart()));
                    dataOutputStream.flush();
                } else if (request.startsWith("removeInMapGoodsInBuyerCart_")) {
                    Shop.getShop().getProductWithId(Integer.parseInt(info[1])).getGoodsInBuyerCart().remove(account.getUsername());
                } else if (request.startsWith("removeInBuyerCart_")) {
                    ((Buyer) account).getCart().remove(Shop.getShop().getProductWithId(Integer.parseInt(info[1])));
                } else if (request.startsWith("putInMapGoodsInBuyerCart_")) {
                    Shop.getShop().getProductWithId(Integer.parseInt(info[2])).getGoodsInBuyerCart().put(account.getUsername(), Integer.valueOf(info[1]));
                } else if (request.startsWith("can_pay")) {
                    if (BuyerManager.canPay(Double.parseDouble(info[2]), account)) {
                        dataOutputStream.writeUTF("true");
                    } else {
                        dataOutputStream.writeUTF("false");
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("payWithCredit")) {
                    BuyerManager.pay(Double.parseDouble(info[1]), Integer.parseInt(info[2]), ((Buyer) account), true);
                } else if (request.startsWith("payWithBankAccount")) {
                    BuyerManager.pay(Double.parseDouble(info[1]), Integer.parseInt(info[2]), ((Buyer) account), false);
                    String token = handleBankClient("get_token " + account.getUsername() + " " + account.getPassword());
                    System.out.println("create_receipt " + token + " move " + info[1] + " " + account.getBankAccountId() + " 0 description");
                    String receiptId = handleBankClient("create_receipt " + token + " move " + info[1] + " " + account.getBankAccountId() + " 0 description");
                    System.out.println("receiptId = " + receiptId);
                    System.out.println("pay: " + handleBankClient("pay " + receiptId));
                } else if (request.startsWith("increase_credit")) {
                    String token = handleBankClient("get_token " + account.getUsername() + " " + account.getPassword());
                    System.out.println("create_receipt " + token + " move " + info[2] + " " + account.getBankAccountId() + " 0 description");
                    String receiptId = handleBankClient("create_receipt " + token + " move " + info[2] + " " + account.getBankAccountId() + " 0 description");
                    System.out.println(receiptId);
                    System.out.println("pay: " + handleBankClient("pay " + receiptId));
                    account.increaseCredit(Long.parseLong(info[2]));
                } else if (request.startsWith("Withdraw")) {
                    String token = handleBankClient("get_token shop javad1379");
                    System.out.println("create_receipt " + token + " move " + info[1] + " 0 " + account.getBankAccountId() + " description");
                    String receiptId = handleBankClient("create_receipt " + token + " move " + info[1] + " 0 " + account.getBankAccountId() + " description");
                    System.out.println("receiptId = " + receiptId);
                    System.out.println("pay: " + handleBankClient("pay " + receiptId));
                    account.subtractCredit(Long.parseLong(info[1]));
                } else if (request.startsWith("get_credit")) {
                    dataOutputStream.writeUTF("" + account.getCredit());
                    dataOutputStream.flush();
                } else if (request.startsWith("add_to_auction")) {
                    Auction auction = Shop.getShop().getAuctionWithId(Integer.parseInt(info[3]));
                    auction.getBuyersInAuction().add(account.getUsername());
                } else if (request.startsWith("send_")) {
                    Auction auction = Shop.getShop().getAuctionWithId(Integer.parseInt(info[4]));
                    for (String buyerUsername : auction.getBuyersInAuction()) {
                        if (!buyerUsername.equals(account.getUsername())) {
                            DataOutputStream dataOutputStream = accountsToOutPuts.get(buyerUsername);
                            dataOutputStream.writeUTF(account.getUsername() + "_" + info[1]);
                            dataOutputStream.flush();
                        }
                    }
                } else if (request.startsWith("disconnect_from_auction_")) {
                    Auction auction = Shop.getShop().getAuctionWithId(Integer.parseInt(info[3]));
                    auction.getBuyersInAuction().remove(account.getUsername());
                    dataOutputStream.writeUTF("disconnect_auction");
                    dataOutputStream.flush();
//                    dataOutputStream.writeUTF("" + account.getCredit());
//                    dataOutputStream.flush();
                } else if (request.startsWith("finishAuction_")) {
                    Auction auction = Shop.getShop().getAuctionWithId(Integer.parseInt(info[1]));
                    if (auction.getBuyersInAuction() != null) {
                        Buyer winner = ((Buyer) Shop.getShop().getAccountByUsername(auction.getBuyerUsername()));
                        BuyerManager.pay(auction.getPrice(), -1, winner, true);
                    }
                } else if (request.startsWith("getWage")) {
                    dataOutputStream.writeUTF("" + Shop.getShop().getWage());
                    dataOutputStream.flush();
                } else if (request.startsWith("setWage")) {
                    Shop.getShop().setWage(Integer.parseInt(info[1]));
                } else if (request.startsWith("getMinimumCredit")) {
                    dataOutputStream.writeUTF("" + Shop.getShop().getMinimumCredit());
                    dataOutputStream.flush();
                } else if (request.startsWith("setMinimumCredit")) {
                    Shop.getShop().setMinimumCredit(Integer.parseInt(info[1]));
                } else if (request.startsWith("getFinalTotalPrice_")) {
                    Discount discount = Shop.getShop().getDiscountWithCode(Integer.parseInt(info[1]));
                    dataOutputStream.writeUTF("" + BuyerManager.getFinalTotalPrice(discount, ((Buyer) account)));
                    dataOutputStream.flush();
                } else if (request.startsWith("receiveFile_")) {
                    sendFile(account.getImagePath());
                } else if (request.startsWith("receiveGoodFile_")) {
                    sendFile(Shop.getShop().getProductWithId(Integer.parseInt(info[1])).getImagePath());
                } else if (request.startsWith("receiveVideoFile_")) {
                    sendFile(Shop.getShop().getProductWithId(Integer.parseInt(info[1])).getVideoPath());
                } else if (request.startsWith("getTypeOfFile")) {
                    String path = Shop.getShop().getProductWithId(Integer.parseInt(info[1])).getVideoPath();
                    System.out.println(path.split("\\.")[1]);
                    dataOutputStream.writeUTF(path.split("\\.")[1]);
                    dataOutputStream.flush();
                } else if (request.startsWith("visit_")) {
                    dataOutputStream.writeUTF("" + Shop.getShop().getProductWithId(Integer.parseInt(info[1])).getVisitNumber());
                    dataOutputStream.flush();
                } else if (request.startsWith("visitIncrease_")) {
                    Good good = Shop.getShop().getProductWithId(Integer.parseInt(info[1]));
                    int number = good.getVisitNumber() + 1;
                    good.setVisitNumber(number);
                } else if (request.startsWith("getShopCredit")) {
                    String token = handleBankClient("get_token shop javad1379");
                    dataOutputStream.writeUTF(handleBankClient("get_balance " + token));
                    dataOutputStream.flush();
                } else if (request.startsWith("exit")) {
                    handleBankClient("exit");
                    disconnectClient();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("connection closed!");
            offlineAccount();
        }
    }

    public String receiveFile(String request) {
        try {
            int bytesRead;
            System.out.println(request);
            String fileName = request.split("_")[1];
            OutputStream output = new FileOutputStream("src/main/java/view/fileSender/" + fileName);
            long size = dataInputStream.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

//            output.close();
//            dataInputStream.close();

            System.out.println("File " + fileName + " received from client.");
            return "src/main/java/view/fileSender/" + fileName;

        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
            return null;
        }

    }


    public void sendFile(String fileName) {
        try {

            File myFile = new File(fileName);  //handle file reading
            byte[] myByteArray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(myByteArray, 0, myByteArray.length);

            dataOutputStream.writeUTF(myFile.getName());
            dataOutputStream.writeLong(myByteArray.length);
            dataOutputStream.write(myByteArray, 0, myByteArray.length);
            dataOutputStream.flush();
            System.out.println("File " + fileName + " sent to client.");
        } catch (Exception e) {
            System.err.println("File " + fileName + "does not exist!");
        }
    }


    private void disconnectClient() throws IOException {
        FileHandler.write();
        socket.close();
        System.out.println("connection closed!");
        offlineAccount();
    }

    private void offlineAccount() {
        for (String token : onlineAccounts.keySet()) {
            if (account.getUsername().equals(onlineAccounts.get(token))) {
                onlineAccounts.remove(token);
                break;
            }
        }
    }

    private String handleBankClient(String message) throws IOException {
        Socket bankSocket = new Socket("localhost", 8888);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(bankSocket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(bankSocket.getInputStream()));
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        bankSocket.close();
        return response;
    }

}
