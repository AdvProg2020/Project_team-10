package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.requests.*;

public class Shop {
    private List<Account> allAccounts = new ArrayList<>();
    private List<Request> allRequests = new ArrayList<>();
    private List<Off> allOffs = new ArrayList<>();
    private List<Good> allGoods = new ArrayList<>();
    private List<Category> allCategories = new ArrayList<>();
    private List<Discount> allDiscounts = new ArrayList<>();

    private Shop() {
        allCategories.add(new Category("khar", new ArrayList<>(Arrays.asList("RAM", "CPU"))));
    }

    public List<Discount> getAllDiscounts() {
        return allDiscounts;
    }

    public List<Category> getAllCategories() {
        return allCategories;
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

    public Account getAccountByUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public Good getProductWithId(int id) {
        for (Good good : allGoods) {
            if (good.getId() == id) {
                return good;
            }
        }
        return null;
    }

    public Discount getDiscountWithCode(int code) {
        for (Discount discount : allDiscounts) {
            if (discount.getCode() == code) {
                return discount;
            }
        }
        return null;
    }

    public Category getCategoryByName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public Off getOffWithId(int id) {
        for (Off off : allOffs) {
            if (off.getId() == id) {
                return off;
            }
        }
        return null;
    }

    public boolean isThereEmail(String email) {
        for (Account account : allAccounts) {
            if (account.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTherePhoneNumber(String phoneNumber) {
        for (Account account : allAccounts) {
            if (account.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

}
