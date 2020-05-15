package controller;

import model.*;
import view.CommandProcessor;

import java.util.Date;
import java.util.List;

public class AdminManager {

    //Admin


    public static boolean deleteAccount(String username) {
        for (Account allAccount : Shop.getShop().getAllAccounts()) {
            if (allAccount.equals(Shop.getShop().getAccountByUsername(username))) {
                Shop.getShop().getAllAccounts().remove(allAccount);
                return true;
            }
        }
        return false;
    }

//    public static boolean addAdmin(String username) {
//        return false;
//    }

    public static boolean removeProduct(int id) {
        return false;
    }

    public static void createDiscount(Date startDate, Date endDate, int percent,
                                      long maxAmountOfDiscount, int repeatDiscount, List<Account> users) {
        Discount discount = new Discount(AccountManager.getLastDiscountCode(), startDate, endDate, percent,
                maxAmountOfDiscount, repeatDiscount, users);
        Shop.getShop().getAllDiscounts().add(discount);
        for (Account user : users) {
            ((Buyer) user).getDiscounts().add(discount);
        }
    }

    public static void editDiscount(Date startDate, Date endDate, int percent,
                                    long maxAmountOfDiscount, int repeatDiscount, List<Account> users, Discount discount) {
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setPercent(percent);
        discount.setMaxAmountOfDiscount(maxAmountOfDiscount);
        discount.setRepeatDiscount(repeatDiscount);
        discount.setUsers(users);
    }

    public static boolean removeDiscount(String code) {
        return false;
    }

    public static void showAllRequests() {

    }

    public static boolean showRequestDetail(String id) {
        return false;
    }

    public static boolean acceptRequest(String id) {
        return false;
    }

    public static boolean declineRequest(String id) {
        return false;
    }

    public static void editCategory(String oldName, String newName, List<String> newAttribute) {
        Category category = Shop.getShop().getCategoryByName(oldName);
        category.setName(newName);
        category.setAttributes(newAttribute);
    }

    public static void addCategory(String name, List<String> attributes) {
        Shop.getShop().getAllCategories().add(new Category(name, attributes));
    }

    public static boolean removeCategory(String categoryName) {
        return false;
    }

}
