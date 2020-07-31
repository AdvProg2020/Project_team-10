package controller;

import model.*;

import java.util.*;

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

    public static long getFinalTotalPrice(Discount discount, Buyer buyer) {
        if (BuyerManager.getPriceAfterApplyOff(buyer) * (discount.getPercent() / 100.0) > discount.getMaxAmountOfDiscount()) {
            return (BuyerManager.getPriceAfterApplyOff(buyer) - discount.getMaxAmountOfDiscount());
        } else {
            return (((long) (BuyerManager.getPriceAfterApplyOff(buyer) * ((100.0 - discount.getPercent()) / 100.0))));
        }
    }


    public static void purchase() {
    }

//    public static boolean rateProduct(int id, int rate, Account account) {
//        Buyer buyer = (Buyer) account;
//        for (Good good : buyer.getGoods()) {
//            if (good.getId() == id) {
//                good.getAllScores().add(rate);
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean canPay(double finalPrice, Account account) {
        return finalPrice <= account.getCredit();
    }

    public static void pay(double finalPrice, int code, Buyer currentBuyer, boolean payWithCredit) {
        Discount currentDiscount = Shop.getShop().getDiscountWithCode(code);
        if (payWithCredit) {
            currentBuyer.subtractCredit(finalPrice);
        }
        Set<Seller> sellers = new HashSet<>();
        for (Good good : currentBuyer.getCart()) {
            good.getBuyersUsername().add(currentBuyer.getUsername());
            sellers.add(((Seller) Shop.getShop().getAccountByUsername(good.getSellerUsername())));
        }
        for (Integer discountCode : currentBuyer.getDiscountAndNumberOfAvailableDiscount().keySet()) {
            if (currentDiscount != null && discountCode == currentDiscount.getCode()) {
                int number = currentBuyer.getDiscountAndNumberOfAvailableDiscount().get(discountCode);
                currentBuyer.getDiscountAndNumberOfAvailableDiscount().put(discountCode, number - 1);
                if (number == 1) {
                    currentBuyer.getDiscountAndNumberOfAvailableDiscount().remove(discountCode);
                }
            }
        }
        makeLogs(sellers, finalPrice, currentBuyer);
        currentBuyer.getGoods().addAll(currentBuyer.getCart());
        currentBuyer.getCart().clear();
    }

    private static void makeLogs(Set<Seller> sellers, double finalPrice, Buyer currentBuyer) {
        Map<String, List<Good>> sellersToHisGoods = new HashMap<>();
        for (Seller seller : sellers) {
            ArrayList<Good> goodsOfOneSeller = new ArrayList<>();
            for (Good good : currentBuyer.getCart()) {
                if (good.getSellerUsername().equals(seller.getUsername())) {
                    goodsOfOneSeller.add(good);
                }
            }
            sellersToHisGoods.put(seller.getUsername(), goodsOfOneSeller);
            int wage = Shop.getShop().getWage();
            seller.increaseCredit(((long) (BuyerManager.getPriceAfterApplyOff(currentBuyer) * ((100 - wage) / 100.0))));
            seller.getSellerLogs().add(new SellerLog(AccountManager.getLastSellerLogId() + 1, new Date(), ((long) finalPrice),
                    BuyerManager.getTotalPrice(currentBuyer) - BuyerManager.getPriceAfterApplyOff(currentBuyer)
                    , sellersToHisGoods.get(seller.getUsername()), currentBuyer.getUsername(), "received"));
        }
        currentBuyer.getBuyerLogs().add(new BuyerLog(AccountManager.getLastBuyerLogId() + 1, new Date(), ((long) finalPrice),
                ((long) (BuyerManager.getPriceAfterApplyOff(currentBuyer) - finalPrice)), sellersToHisGoods, "paid"));
    }

}
