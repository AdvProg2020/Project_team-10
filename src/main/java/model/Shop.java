package model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Account> allAccounts = new ArrayList<>();
    private List<Request> allRequests = new ArrayList<>();
    private List<Off> allOffs = new ArrayList<>();
    private List<Good> allGoods = new ArrayList<>();
    private List<Discount> allDiscount = new ArrayList<>();

    public List<Discount> getAllDiscount() {
        return allDiscount;
    }

    public List<Account> getAllAccounts() {
        return allAccounts;
    }

    public List<Request> getAllRequests() {
        return allRequests;
    }

    public List<Off> getAllOffs() {
        return allOffs;
    }

    public List<Good> getAllGoods() {
        return allGoods;
    }

    private static Shop shop = new Shop();

    public static Shop getShop() {
        return shop;
    }

    private Shop() {
    }

    public Account getAccountByUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)){
                return account;
            }
        }
        return null;
    }

    public Good getProductWithId(String id) {
        return null;
    }

    public Discount getDiscountWithCode(int code) {

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
