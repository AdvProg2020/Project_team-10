package controller;

import model.Buyer;

import java.util.Date;
import java.util.List;

public class AdminManager {

    //Admin
    public static void showPersonalInfo() {
        System.out.println(AccountManager.getOnlineAccount());
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
        return false;
    }

    public static boolean addAdmin(String username) {
        return false;
    }

    public static boolean removeProduct(String id) {
        return false;
    }

    public static boolean createDiscount(String code, Date startDate, Date endDate, int percent, long maxAmountOfDiscount, int repeatDiscount, List<Buyer> users) {
        return false;
    }

    public static void showAllDiscount() {

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
