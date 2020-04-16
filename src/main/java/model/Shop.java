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

    public Account getRoleByUsername(String username) {
        return new Admin("f", "f");
    }

    public Good getProductWithId(String id) {
        return null;
    }

    public Discount getDiscountWithCode(String code) {
        return null;
    }

    public Account getAccountWithUsername(String username) {
        return null;
    }

    public Category getCategory(String name) {
        return null;
    }

    public boolean isThereEmail(String email) {
        return false;
    }

    public boolean isTherePhoneNumber(String phoneNumber) {
        return false;
    }

    public Off getOffWithId(String id) {
        return null;
    }
}
