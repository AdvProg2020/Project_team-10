package controller;

import model.*;

import java.util.Date;
import java.util.List;

public class SellerManager {


    public static void showCompanyInfo() {
        System.out.println(((Seller) AccountManager.getOnlineAccount()).getCompany());
    }

    public static void showSalesHistory() {
        System.out.println(AccountManager.getOnlineAccount().getLogs());
    }

    public static void showHisProducts() {
        System.out.println(((Seller) AccountManager.getOnlineAccount()).getGoods());
    }

    public static boolean showProductForSeller(String id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            System.out.println(good);
            return true;
        }
        return false;
    }

    public static boolean showBuyersOfThisProduct(String id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            System.out.println(good.getBuyers());
            return true;
        }
        return false;
    }

    public static boolean editProduct(String id, String name, String company, int number, long price, String category, List<String> categoryAttribute, String description) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            new Request(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1);

            return true;
        }
        return false;
    }

    public static void addProduct(String id, String name, String company, int number, long price, String category, List<String> categoryAttribute, String description) {
        new Request(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1);
        AccountManager.increaseLastRequestId();
    }

    public static boolean removeProduct(String id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            ((Seller) AccountManager.getOnlineAccount()).getGoods().remove(good);
            return true;
        }
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
