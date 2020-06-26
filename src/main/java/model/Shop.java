package model;

import model.requests.Request;

import java.util.*;

public class Shop {
    private List<Account> allAccounts = new ArrayList<>();
    private List<Seller> allSellers = new ArrayList<>();
    private List<Buyer> allBuyers = new ArrayList<>();
    private List<Admin> allAdmins = new ArrayList<>();
    private List<Off> allOffs = new ArrayList<>();
    private List<Good> allGoods = new ArrayList<>();
    private List<Category> allCategories = new ArrayList<>();
    private List<Discount> allDiscounts = new ArrayList<>();
    private List<Request> allRequests = new ArrayList<>();
    private List<Comment> allComments = new ArrayList<>();

    private Shop() {
        allAccounts.add(new Admin("javads", "a", "A", "asd@dw.ds",
                "09123123123", "javad1379", "src/main/java/view/image/usersamad.jpg"));
    }

    public List<Comment> getAllComments() {
        return allComments;
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

    public List<Seller> getAllSellers() {
        return allSellers;
    }

    public List<Admin> getAllAdmins() {
        return allAdmins;
    }

    public List<Buyer> getAllBuyers() {
        return allBuyers;
    }

    public List<Off> getAllOffs() {
        return allOffs;
    }

    public List<Good> getAllGoods() {
        return allGoods;
    }

    public List<Request> getAllRequests() {
        return allRequests;
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

    public Request getRequestById(int id) {
        for (Request request : allRequests) {
            if (request.getId() == id) {
                return request;
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

    public Set<String> allCompanies() {
        Set<String> allCompanies = new HashSet<>();
        for (Good good : allGoods) {
            allCompanies.add(good.getCompany());
        }
        return allCompanies;
    }

}
