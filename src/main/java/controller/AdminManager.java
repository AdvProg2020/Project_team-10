package controller;

import model.Account;
import model.Buyer;
import model.Discount;
import model.Shop;
import view.CommandProcessor;

import java.util.Date;
import java.util.List;

public class AdminManager {

    private static int code= 0;
    //Admin
    public static void showPersonalInfo() {
        CommandProcessor.printShowPersonalInfo();
    }

    public static void editPersonalInfo(String password, String firstName, String lastName, String phoneNumber , String email) {
        AccountManager.getOnlineAccount().setPassword(password);
        AccountManager.getOnlineAccount().setFirstName(firstName);
        AccountManager.getOnlineAccount().setLastName(lastName);
        AccountManager.getOnlineAccount().setPhoneNumber(phoneNumber);
        AccountManager.getOnlineAccount().setEmail(email);
    }

    public static void showAllAccount() {

    }

    public static boolean showAccount(String username) {
        return false;
    }

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

    public static boolean removeProduct(String id) {
        return false;
    }

    public static void createDiscount(Date startDate, Date endDate, int percent,
                                         long maxAmountOfDiscount, int repeatDiscount, List<Account> users) {
        new Discount(code , startDate ,endDate , percent , maxAmountOfDiscount , repeatDiscount , users);
        AccountManager.increaseLastDiscountId();
    }

    public static void showAllDiscount() {
        CommandProcessor.printShowAllDiscount();
    }

    public static boolean showDiscount(String code) {
        return false;
    }

    public static boolean editDiscount(String code) {
        return false;
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

    public static void showAllCategories() {

    }

    public static boolean editCategory(String name, String newName, List<String> newAttribute) {
        return false;
    }

    public static boolean addCategory(String name, List<String> attributes) {
        return false;
    }

    public static boolean removeCategory(String categoryName) {
        return false;
    }

}
