package controller;

import model.Good;
import model.Seller;

import java.util.Date;
import java.util.List;

public class SellerManager {

    public static void showCompanyInfo() {

    }

    public static void showSalesHistory() {

    }

    public static void showHisProducts() {

    }

    public static boolean showProductForSeller(String id) {
        return false;
    }

    public static boolean showBuyersOfThisProduct(String id) {
        return false;
    }

    public static boolean editProduct(String id, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description) {
        return false;
    }

    public static boolean addProduct(String id, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description) {
        return false;
    }

    public static void showAllOffs() {

    }

    public static boolean showOff(String id) {
        return false;
    }

    public static boolean editOff(String id, List<Good> goods, Date startDate, Date endDate, int discount) {
        return false;
    }

    public static boolean addOff(String id, List<Good> goods, Date startDate, Date endDate, int discount) {
        return false;
    }

    public static void viewBalance() {

    }

}
