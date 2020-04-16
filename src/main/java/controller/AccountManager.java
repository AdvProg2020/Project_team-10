package controller;

import model.*;
import view.Menus.Menu;

public class AccountManager {
    private static Account onlineAccount;

    public static Account getOnlineAccount() {
        return onlineAccount;
    }

    public static boolean canRegister(String username) {
        return true;
    }

    public static void register(String username, String password, String type, String firstName, String lastName, String email, String phoneNumber) {
    }

    public static boolean login(String username, String password) {
        onlineAccount = Shop.getShop().getRoleByUsername(username);
        return false;
    }

    public static void logout() {
        Menu.setIsLogged(false);
    }


}
