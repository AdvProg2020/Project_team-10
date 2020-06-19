package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.requests.RegisterOfSellerRequest;
import view.menus.Menu;
import view.menus.UserMenu;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AccountManager {
    private static Account onlineAccount;
    private static int lastRequestId;
    private static int lastGoodId;
    private static int lastCommentId;
    private static int lastBuyerLogId;
    private static int lastSellerLogId;
    private static int lastDiscountCode;
    private static int lastOffId;

    // getters & setters


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

    public static boolean canRegister(String username) {
        return Shop.getShop().getAccountByUsername(username) == null;
    }

    public static void register(String username, String password, String type, String firstName,
                                String lastName, String email, String phoneNumber, String company, String imagePath) {
        if (type.equals("buyer")) {
            Buyer buyer = new Buyer(username, firstName, lastName, email, phoneNumber, password, imagePath);
            Shop.getShop().getAllAccounts().add(buyer);
            Shop.getShop().getAllBuyers().add(buyer);
        } else if (type.equals("seller")) {
            //TODO
            Seller seller = new Seller(username, firstName, lastName, email, phoneNumber, password, company, imagePath);
            Shop.getShop().getAllAccounts().add(seller);
            Shop.getShop().getAllSellers().add(seller);
            Shop.getShop().getAllRequests().add(new RegisterOfSellerRequest(lastRequestId + 1, username, password,
                    firstName, lastName, email, phoneNumber, company, imagePath));
        } else {
            Admin admin = new Admin(username, firstName, lastName, email, phoneNumber, password, imagePath);
            Shop.getShop().getAllAccounts().add(admin);
            Shop.getShop().getAllAdmins().add(admin);
        }
    }

    public static boolean login(String username, String password) {
        for (Account account : Shop.getShop().getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    onlineAccount = Shop.getShop().getAccountByUsername(username);
                    Menu.setIsLogged(true);
                    UserMenu.setUsername(username);
                    return true;
                }
            }
        }
        return false;
    }

    public static void editPersonalInfo(String password, String firstName, String lastName, String phoneNumber, String email) {
        AccountManager.getOnlineAccount().setPassword(password);
        AccountManager.getOnlineAccount().setFirstName(firstName);
        AccountManager.getOnlineAccount().setLastName(lastName);
        AccountManager.getOnlineAccount().setPhoneNumber(phoneNumber);
        AccountManager.getOnlineAccount().setEmail(email);
    }


}
