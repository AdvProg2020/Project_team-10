package model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private static Shop shop = new Shop();

    public static Shop getShop() {
        return shop;
    }

    private Shop() {
        List<Account> allAccounts = new ArrayList<>();
        List<Request> allRequests = new ArrayList<>();
        List<Off> allOffs = new ArrayList<>();
        List<Good> allGoods = new ArrayList<>();
    }
}
