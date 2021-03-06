package controller;

import Bank.BankAccount;
import model.*;
import model.requests.RegisterOfSellerRequest;

public class AccountManager {
    private static Account onlineAccount = new Buyer("temp");
    private static int lastRequestId;
    private static int lastGoodId;
    private static int lastCommentId;
    private static int lastBuyerLogId;
    private static int lastSellerLogId;
    private static int lastDiscountCode;
    private static int lastOffId;
    private static int lastAccountNumber = 1;
    private static int lastReceiptId;
    private static int lastAuctionId;

    // getters & setters

    public static int getLastReceiptId() {
        return lastReceiptId;
    }

    public static void setLastReceiptId(int lastReceiptId) {
        AccountManager.lastReceiptId = lastReceiptId;
    }

    public static void setLastAccountNumber(int lastAccountNumber) {
        AccountManager.lastAccountNumber = lastAccountNumber;
    }

    public static int getLastAccountNumber() {
        return lastAccountNumber;
    }

    public static int getLastAuctionId() {
        return lastAuctionId;
    }

    public static void setLastAuctionId(int lastAuctionId) {
        AccountManager.lastAuctionId = lastAuctionId;
    }

    public static void setLastRequestId(int lastRequestId) {
        AccountManager.lastRequestId = lastRequestId;
    }

    public static void setLastGoodId(int lastGoodId) {
        AccountManager.lastGoodId = lastGoodId;
    }

    public static void setLastCommentId(int lastCommentId) {
        AccountManager.lastCommentId = lastCommentId;
    }

    public static void setLastBuyerLogId(int lastBuyerLogId) {
        AccountManager.lastBuyerLogId = lastBuyerLogId;
    }

    public static void setLastSellerLogId(int lastSellerLogId) {
        AccountManager.lastSellerLogId = lastSellerLogId;
    }

    public static void setLastDiscountCode(int lastDiscountCode) {
        AccountManager.lastDiscountCode = lastDiscountCode;
    }

    public static void setLastOffId(int lastOffId) {
        AccountManager.lastOffId = lastOffId;
    }

    public static int getLastRequestId() {
        return lastRequestId;
    }

    public static int getLastDiscountCode() {
        return lastDiscountCode;
    }

    public static int getLastOffId() {
        return lastOffId;
    }

    public static void increaseLastAuctionId() {
        lastAuctionId += 1;
    }

    public static void increaseLastAccountNumber() {
        lastAccountNumber += 1;
    }

    public static void increaseLastRequestId() {
        lastRequestId += 1;
    }

    public static void increaseLastGoodId() {
        lastGoodId += 1;
    }

    public static void increaseLastCommentId() {
        lastCommentId += 1;
    }

    public static void increaseLastDiscountId() {
        lastDiscountCode += 1;
    }

    public static void increaseLastOffId() {
        lastOffId += 1;
    }

    public static void increaseLastBuyerLogId() {
        lastBuyerLogId += 1;
    }

    public static void increaseLastSellerLogId() {
        lastSellerLogId += 1;
    }

    public static void increaseLastReceiptId() {
        lastReceiptId += 1;
    }

    public static int getLastGoodId() {
        return lastGoodId;
    }

    public static int getLastCommentId() {
        return lastCommentId;
    }

    public static int getLastBuyerLogId() {
        return lastBuyerLogId;
    }

    public static int getLastSellerLogId() {
        return lastSellerLogId;
    }

    public static Account getOnlineAccount() {
        return onlineAccount;
    }

    public static void setOnlineAccount(Account onlineAccount) {
        AccountManager.onlineAccount = onlineAccount;
    }

    // logical methods

    private static boolean canRegister(String username) {
        return Shop.getShop().getAccountByUsername(username) == null;
    }

    public static Account register(String username, String password, String type, String firstName,
                                String lastName, String email, String phoneNumber, String company, String imagePath) {
        switch (type) {
            case "buyer":
                Buyer buyer = new Buyer(username, firstName, lastName, email, phoneNumber, password, imagePath);
                Shop.getShop().getAllAccounts().add(buyer);
                Shop.getShop().getAllBuyers().add(buyer);
                return buyer;
            case "seller":
                //TODO
                Seller seller = new Seller(username, firstName, lastName, email, phoneNumber, password, company, imagePath);
                Shop.getShop().getAllAccounts().add(seller);
                Shop.getShop().getAllSellers().add(seller);
                Shop.getShop().getAllRequests().add(new RegisterOfSellerRequest(lastRequestId + 1, username, password,
                        firstName, lastName, email, phoneNumber, company, imagePath));
                return seller;
            case "supporter":
                //TODO
                Supporter supporter = new Supporter(username, firstName, lastName, email, phoneNumber, password, imagePath);
                Shop.getShop().getAllAccounts().add(supporter);
                Shop.getShop().getAllSupporters().add(supporter);
                return supporter;
            default:
                System.out.println("an admin added");
                Admin admin = new Admin(username, firstName, lastName, email, phoneNumber, password, imagePath);
                Shop.getShop().getAllAccounts().add(admin);
                Shop.getShop().getAllAdmins().add(admin);
                return admin;
        }
    }

    public static Account canLogin(String username, String password) {
        for (Account account : Shop.getShop().getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
//                    onlineAccount = Shop.getShop().getAccountByUsername(username);
//                    Menu.setIsLogged(true);
//                    UserMenu.setUsername(username);
                    return Shop.getShop().getAccountByUsername(username);
                }
            }
        }
        return null;
    }

    public static void editPersonalInfo(String password, String firstName, String lastName, String phoneNumber
            , String email, String username) {
        Account account = Shop.getShop().getAccountByUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhoneNumber(phoneNumber);
        account.setEmail(email);
    }


}
