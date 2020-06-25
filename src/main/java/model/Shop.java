package model;

import model.requests.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

//        allAccounts.add(new Admin("javads" , "jjjj" , "sakbn" ,
//                "sakcnakcsakd@gamil.com" , "091221273738" , "javad1379" ,
//                "src/main/java/view/image/usersamad.jpg"));
//
//        ArrayList<String> arrayList = new ArrayList<>();
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("asds" , "200 km/h");
//        hashMap.put("spd" , "200 km/h");
//        hashMap.put("speed" , "300 km/h");
//        arrayList.add("speed");
//        arrayList.add("asds");
//        arrayList.add("speaffed");
//        allCategories.add(new Category("car" ,arrayList ));
//        allGoods.add(new Good(4 , "dddd" , "bmw"
//                , 2 , 4 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(5));
//        allGoods.add(new Good(4 , "hhhh" , "bmw"
//                , 2 , 8 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(10));
//        allGoods.add(new Good(2 , "bbbb" , "bmw"
//                , 2 , 2 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(2));
//        allGoods.add(new Good(3 , "cccc" , "bmw"
//                , 2 , 3 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(8));
//        allGoods.add(new Good(1 , "iiii" , "bmw"
//                , 2 , 9 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(1));
//        allGoods.add(new Good(1 , "eeee" , "bmw"
//                , 2 , 5 , null , "car" , hashMap , "dhsvjshv"));
//        allGoods.add(new Good(2 , "ffff" , "bmw"
//                , 2 , 6 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(12));
//        allGoods.add(new Good(3 , "kkkk" , "bmw"
//                , 2 , 11 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(15));
//        allGoods.add(new Good(3 , "gggg" , "bmw"
//                , 2 , 7 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(3));
//        allGoods.add(new Good(1 , "aaaa" , "bmw"
//                , 2 , 1 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(6));
//        allGoods.add(new Good(2 , "jjjj" , "bmw"
//                , 2 , 10 , null , "car" , hashMap , "dhsvjshv"));
//        allGoods.add(new Good(4 , "llll" , "bmw"
//                , 2 , 12 , null , "car" , hashMap , "dhsvjshv"));
//        allGoods.add(new Good(1 , "mmmm" , "bmw"
//                , 2 , 13 , null , "car" , hashMap , "dhsvjshv").setVisitNumber(10));
//        allGoods.add(new Good(2 , "nnnn" , "bmw"
//                , 2 , 1800 , "seller" , "car" , hashMap , "dhsvjshv", "C:/Users/hossein/Desktop/optima.jpg"));
//        allGoods.add(new Good(3 , "oooo" , "bmw"
//                , 2 , 1500 , "seller" , "car" , hashMap , "dhsvjshv", "C:/Users/hossein/Desktop/optima.jpg"));
//        allGoods.add(new Good(7 , "optima" , "kia"
//                , 5 , 2000 , "seller" , "car" , hashMap , "dhsvjshvgg dfg d fdfgdfgdfg dfgdfg",
//                "C:/Users/hossein/Desktop/optima.jpg").setVisitNumber(19));
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

}
