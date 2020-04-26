package controller;

import model.*;
import view.menus.Menu;
import view.menus.UserMenu;

public class AccountManager {
    private static Account onlineAccount;
    private static int lastRequestId;
    private static int lastGoodId;
    private static int lastCommentId;
    private static int lastLogId;
    private static int lastDiscountId;
    private static int lastOffId;

    public static int getLastRequestId() {
        return lastRequestId;
    }

    public static int getLastDiscountId() {
        return lastDiscountId;
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

    public static void increaseLastLogId() {
        lastLogId += 1;
    }

    public static void increaseLastDiscountId() {
        lastDiscountId += 1;
    }

    public static void increaseLastOffId() {
        lastOffId += 1;
    }

    public static int getLastGoodId() {
        return lastGoodId;
    }

    public static void setLastGoodId(int lastGoodId) {
        AccountManager.lastGoodId = lastGoodId;
    }

    public static int getLastCommentId() {
        return lastCommentId;
    }

    public static void setLastCommentId(int lastCommentId) {
        AccountManager.lastCommentId = lastCommentId;
    }

    public static int getLastLogId() {
        return lastLogId;
    }

    public static void setLastLogId(int lastLogId) {
        AccountManager.lastLogId = lastLogId;
    }

    public static Account getOnlineAccount() {
        return onlineAccount;
    }

    public static boolean canRegister(String username) {
        for (Account account : Shop.getShop().getAllAccounts()) {
            if (account.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    public static void register(String username, String password, String type, String firstName,
                                String lastName, String email, String phoneNumber, String company) {
        if (type.equals("buyer")) {
            Shop.getShop().getAllAccounts().add(new Buyer(username, firstName, lastName, email, phoneNumber, password));
        } else if (type.equals("seller")) {
            Shop.getShop().getAllAccounts().add(new Seller(username, firstName, lastName, email, phoneNumber, password, company));
        } else {
            Shop.getShop().getAllAccounts().add(new Admin(username, firstName, lastName, email, phoneNumber, password));
        }

    }

    public static boolean login(String username, String password) {
        for (Account account : Shop.getShop().getAllAccounts()) {
            if (account.getUsername().equals(username)){
                if (account.getPassword().equals(password)){
                    onlineAccount = Shop.getShop().getRoleByUsername(username);
                    Menu.setIsLogged(true);
                    UserMenu.setUsername(username);
                    return true;
                }
            }
        }
        return false;
    }

    public static void setOnlineAccount(Account onlineAccount) {
        AccountManager.onlineAccount = onlineAccount;
    }

    public static void showPersonalInfo() {

    }

    public static void editPersonalInfo(String password, String firstName, String lastName, String email, String phoneNumber) {

    }

}
