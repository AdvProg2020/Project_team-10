package controller;

import model.*;

public class BuyerManager {

    public static boolean canIncrease(Good good) {
        int number = good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount());
        if (good.getNumber() > number) {
            good.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount(), number + 1);
            return true;
        }
        return false;
    }

    public static boolean canDecrease(Good good) {
        int number = good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount());
        good.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount(), number - 1);
        if (number == 1) {
            good.getGoodsInBuyerCart().remove(AccountManager.getOnlineAccount());
            ((Buyer) AccountManager.getOnlineAccount()).getCart().remove(good);
            return false;
        }
        return true;
    }

    public static long getTotalPrice() {
        int amount = 0;
        for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getCart()) {
            amount += good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount()) * good.getPrice();
        }
        return amount;
    }

    public static void purchase() {
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
}
