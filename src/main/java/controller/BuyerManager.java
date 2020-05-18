package controller;

import model.*;

import java.util.List;

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

    public static long getPriceAfterApplyOff(List<Good> goods) {
        long totalPrice = 0;
        Buyer currentBuyer = ((Buyer) AccountManager.getOnlineAccount());
        for (Good good : goods) {
            int numberOfGoodInCart = good.getGoodsInBuyerCart().get(currentBuyer);
            if (good.getOff() != null) {
                totalPrice += (good.getPrice() * ((100 - good.getOff().getPercent()) / 100.0)
                        * numberOfGoodInCart);
            } else {
                totalPrice += (good.getPrice() * numberOfGoodInCart);
            }
        }
        return totalPrice;
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
