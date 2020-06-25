package controller;

import model.*;

import java.util.List;

public class BuyerManager {

    public static boolean canIncrease(Good good) {
        int number = good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername());
        if (good.getNumber() > number) {
            good.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount().getUsername(), number + 1);
            return true;
        }
        return false;
    }

    public static boolean canDecrease(Good good) {
        int number = good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername());
        good.getGoodsInBuyerCart().put(AccountManager.getOnlineAccount().getUsername(), number - 1);
        if (number == 1) {
            good.getGoodsInBuyerCart().remove(AccountManager.getOnlineAccount().getUsername());
            ((Buyer) AccountManager.getOnlineAccount()).getCart().remove(good);
            return false;
        }
        return true;
    }

    public static long getTotalPrice() {
        int amount = 0;
        for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getCart()) {
            amount += good.getGoodsInBuyerCart().get(AccountManager.getOnlineAccount().getUsername()) * good.getPrice();
        }
        return amount;
    }

    public static long getPriceAfterApplyOff(List<Good> goods) {
        long totalPrice = 0;
        Buyer currentBuyer = ((Buyer) AccountManager.getOnlineAccount());
        for (Good good : goods) {
            int numberOfGoodInCart = good.getGoodsInBuyerCart().get(currentBuyer.getUsername());
            if (good.getOffId() != 0) {
                totalPrice += (good.getPrice() * ((100 - Shop.getShop().getOffWithId(good.getOffId()).getPercent()) / 100.0)
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
                good.getAllScores().add(rate);
                return true;
            }
        }
        return false;
    }
}
