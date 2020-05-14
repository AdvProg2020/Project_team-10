package controller;

import model.Account;
import model.Category;
import model.Discount;
import model.Shop;
import view.CommandProcessor;

import java.util.Date;
import java.util.List;

public class AdminManager {

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

    public static boolean removeProduct(int id) {
        return false;
    }

    public static void createDiscount(Date startDate, Date endDate, int percent,
                                         long maxAmountOfDiscount, int repeatDiscount, List<Account> users) {
        Shop.getShop().getAllDiscounts().add(new Discount(AccountManager.getLastDiscountCode() ,
                startDate ,
                endDate ,
                percent ,
                maxAmountOfDiscount ,
                repeatDiscount ,
                users));
        AccountManager.increaseLastDiscountId();
    }

    public static boolean showDiscount(String code) {
        return false;
    }

    public static void editDiscount(Date startDate, Date endDate, int percent,
                                       long maxAmountOfDiscount, int repeatDiscount, List<Account> users , Discount discount) {
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

    public static void editCategory(String name, String newName, List<String> newAttribute) {
        Category category = Shop.getShop().getCategoryByName(name);
        category.setName(newName);
        category.setAttributes(newAttribute);
    }

    public static void addCategory(String name, List<String> attributes) {
        new Category(name, attributes);
    }

    public static boolean removeCategory(String categoryName) {
        return false;
    }

}
