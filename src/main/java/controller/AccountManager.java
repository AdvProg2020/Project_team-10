package controller;

import model.*;
import sun.plugin2.message.ShowDocumentMessage;
import view.menus.Menu;

public class AccountManager {
    private static Account onlineAccount;

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
