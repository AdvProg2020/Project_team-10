package controller;

import model.*;

import java.util.List;

public class BuyerManager {

    public static boolean canIncrease(Good good, Account account) {
        int number = good.getGoodsInBuyerCart().get(account.getUsername());
        if (good.getNumber() > number) {
            good.getGoodsInBuyerCart().put(account.getUsername(), number + 1);
            return true;
        }
        return false;
    }

    public static boolean canDecrease(Good good, Account account) {
        int number = good.getGoodsInBuyerCart().get(account.getUsername());
        good.getGoodsInBuyerCart().put(account.getUsername(), number - 1);
        if (number == 1) {
            good.getGoodsInBuyerCart().remove(account.getUsername());
            ((Buyer) account).getCart().remove(good);
            return false;
        }
        return true;
    }

    public static long getTotalPrice(Account account) {
        int amount = 0;
        for (Good good : ((Buyer) account).getCart()) {
            amount += good.getGoodsInBuyerCart().get(account.getUsername()) * good.getPrice();
        }
        return amount;
    }

    public static long getPriceAfterApplyOff(Account account) {
        long totalPrice = 0;
        Buyer currentBuyer = ((Buyer) account);
        for (Good good : currentBuyer.getCart()) {
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

    public static boolean rateProduct(int id, int rate, Account account) {
        Buyer buyer = (Buyer) account;
        for (Good good : buyer.getGoods()) {
            if (good.getId() == id) {
                good.getAllScores().add(rate);
                return true;
            }
        }
        return false;
    }
}
