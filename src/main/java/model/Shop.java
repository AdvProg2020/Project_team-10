package model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private static Shop shop = new Shop();

    public static Shop getShop() {
        return shop;
    }

    private List<Account> allAccounts;

    private Shop() {
        allAccounts = new ArrayList<Account>();
    }
}
