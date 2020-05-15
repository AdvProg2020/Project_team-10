package controller;

import model.*;

public class BuyerManager {

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
