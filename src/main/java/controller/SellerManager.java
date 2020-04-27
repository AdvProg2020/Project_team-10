package controller;

import model.*;
import model.requests.*;

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

    public static boolean showProductForSeller(int id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            System.out.println(good);
            return true;
        }
        return false;
    }

    public static boolean showBuyersOfThisProduct(int id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            System.out.println(good.getBuyers());
            return true;
        }
        return false;
    }

    public static boolean editProduct(int id, String name, String company, int number, long price, String category, List<String> categoryAttribute, String description) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            //new Request(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1);
            AccountManager.increaseLastRequestId();
            return true;
        }
        return false;
    }

    public static void addProduct(int id, String name, String company, int number, long price, String category, List<String> categoryAttribute, String description) {
        //new Request(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1);
        AccountManager.increaseLastRequestId();
    }

    public static boolean removeProduct(int id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            ((Seller) AccountManager.getOnlineAccount()).getGoods().remove(good);
            return true;
        }
        return false;
    }

    public static void showAllOffs() {
        for (Off off : ((Seller) AccountManager.getOnlineAccount()).getOffs()) {
            System.out.println(off);
        }
    }

    public static boolean showOff(int id) {
        Off off = ((Seller) AccountManager.getOnlineAccount()).getOffWithId(id);
        if (off != null) {
            System.out.println(off);
            return true;
        }
        return false;
    }

    public static boolean editOff(int id, List<Good> goods, Date startDate, Date endDate, int discount) {
        Off off = ((Seller) AccountManager.getOnlineAccount()).getOffWithId(id);
        if (off != null) {
            //new Request(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1);
            AccountManager.increaseLastRequestId();
            return true;
        }
        return false;
    }

    public static boolean addOff(int id, List<Good> goods, Date startDate, Date endDate, int discount) {
        Off off = ((Seller) AccountManager.getOnlineAccount()).getOffWithId(id);
        if (off != null) {
            //new Request(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1);
            AccountManager.increaseLastRequestId();
            return true;
        }
        return false;

    }

    public static void viewBalance() {
        System.out.println(AccountManager.getOnlineAccount().getCredit());
    }

}
