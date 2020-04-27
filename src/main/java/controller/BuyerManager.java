package controller;

import model.*;

public class BuyerManager {

    //Buyer
    public static void showProductsInCart() {
        for (Good good : Shop.getShop().getGoodsInCart()) {
            System.out.println("name: " + good.getName() + " id: " + good.getId());
        }
    }

    public static boolean showProductInCart(int id) {
        for (Good good : Shop.getShop().getGoodsInCart()) {
            if (good.getId() == id){
                System.out.println("name: " + good.getName() + " id: " + good.getId());
                return true;
            }
        }
        return false;
    }

    public static boolean increase(int id) {
        for (Good good : Shop.getShop().getGoodsInCart()) {
            if (good.getId() == id) {
                if (good.getNumberOfChoose() >= good.getNumber()) {
                    return false;
                } else {
                    good.setNumberOfChoose(good.getNumberOfChoose() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean decrease(int id) {
        for (Good good : Shop.getShop().getGoodsInCart()) {
            if (good.getId() == id){
                good.setNumberOfChoose(good.getNumberOfChoose() - 1);
                if (good.getNumberOfChoose() == 0){
                    Shop.getShop().getGoodsInCart().remove(good);
                }
                return true;
            }
        }
        return false;
    }

    public static void showTotalPrice() {
        int amount = 0;
        for (Good good : Shop.getShop().getGoodsInCart()) {
            amount += good.getNumberOfChoose() * good.getPrice();
        }
        System.out.println("total price: " + amount);
    }

    public static void purchase() {
    }

    public static void showAllOrders() {
        for (Log log : AccountManager.getOnlineAccount().getLogs()) {
            System.out.println(log.toString());
        }
    }

    public static boolean showOrder(int id) {
        for (Log log : AccountManager.getOnlineAccount().getLogs()) {
            if (log.getId() == id){
                System.out.println(log.toString());
                return true;
            }
        }
        return false;
    }

    public static boolean rateProduct(int id, int rate) {
        Buyer buyer = (Buyer) AccountManager.getOnlineAccount();
        for (Good good : buyer.getGoods()) {
            if (good.getId() == id) {
                good.getAllRates().add(rate);
                return true;
            }
        }
        return false;
    }

    public static void showAllDiscountsCode() {
        for (Discount discount : AccountManager.getOnlineAccount().getDiscounts()) {
            System.out.println(discount.toString());
        }
    }
}
