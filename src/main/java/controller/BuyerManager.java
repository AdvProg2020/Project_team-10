package controller;

import model.*;

public class BuyerManager {

    //Buyer
//    public static void showProductsInCart() {
//        for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getCart()) {
//            System.out.println("name: " + good.getName() + " id: " + good.getId());
//        }
//    }

//    public static boolean showProductInCart(int id) {
//        Good good = ((Buyer) AccountManager.getOnlineAccount()).getGoodInCartById(id);
//        if (good != null) {
//            System.out.println("name: " + good.getName() + " id: " + good.getId());
//            return true;
//        }
//        return false;
//    }

    //check nulling(good) in command processor
    public static boolean increase(int id) {
        Good good = ((Buyer) AccountManager.getOnlineAccount()).getGoodInCartById(id);
        if (good.getNumber() > good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount())) {
            int number = good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount());
            good.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount(), number + 1);
            return true;
        }
        return false;
    }

    public static boolean decrease(int id) {
        Good good = ((Buyer) AccountManager.getOnlineAccount()).getGoodInCartById(id);
        if (good.getNumber() > good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount())) {
            int number = good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount());
            good.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount(), number - 1);
            return true;
        }
        return false;
    }

    public static long showTotalPrice() {
        int amount = 0;
        for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getCart()) {
            amount += good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount()) * good.getPrice();
        }
        return amount;
    }

    public static void purchase() {
    }

    // move to command processor
//    public static void showAllOrders() {
//        for (Log log : AccountManager.getOnlineAccount().getLogs()) {
//            System.out.println(log.toString());
//        }
//    }

    // move to command processor
//    public static boolean showOrder(int id) {
//        for (Log log : AccountManager.getOnlineAccount().getLogs()) {
//            if (log.getId() == id) {
//                System.out.println(log.toString());
//                return true;
//            }
//        }
//        return false;
//    }

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

    // move to command processor
//    public static void showAllDiscountsCode() {
//        for (Discount discount : AccountManager.getOnlineAccount().getDiscounts()) {
//            System.out.println(discount.toString());
//        }
//    }
}
