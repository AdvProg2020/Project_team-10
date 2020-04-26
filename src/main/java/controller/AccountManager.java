package controller;

import model.*;
import view.menus.Menu;

public class AccountManager {
    private static Account onlineAccount;
    private static int lastRequestId;
    private static int lastGoodId;
    private static int lastCommentId;
    private static int lastLogId;

    public static int getLastRequestId() {
        return lastRequestId;
    }

    public static void increaseLastRequestId() {
        lastRequestId += 1;
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
        onlineAccount = Shop.getShop().getRoleByUsername(username);
        return false;
    }

    public static void logout() {
        Menu.setIsLogged(false);
    }


}
