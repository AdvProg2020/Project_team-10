package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Buyer extends Account {

    private HashMap<Discount, Integer> discountAndNumberOfAvailableDiscount;
    private List<Good> goods;
    private List<Good> cart;
    private List<Discount> discounts;
    private List<BuyerLog> buyerLogs;


    public Buyer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        goods = new ArrayList<>();
        cart = new ArrayList<>();
        discounts = new ArrayList<>();
        this.discountAndNumberOfAvailableDiscount = new HashMap<>();
        this.buyerLogs = new ArrayList<>();
    }



    public HashMap<Discount, Integer> getDiscountAndNumberOfAvailableDiscount() {
        return discountAndNumberOfAvailableDiscount;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public List<Good> getCart() {
        return cart;
    }

    public Good getGoodInCartById(int id) {
        for (Good good : this.getCart()) {
            if (good.getId() == id) {
                return good;
            }
        }
        return null;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public List<BuyerLog> getBuyerLogs() {
        return buyerLogs;
    }

    public Discount getDiscountByCode(int code){
        for (Discount discount : this.discounts) {
            if (discount.getCode() == code){
                return discount;
            }
        }
        return null;
    }

    public BuyerLog getBuyerLogWithId(int id) {
        for (BuyerLog buyerLog : this.getBuyerLogs()) {
            if (buyerLog.getId() == id) {
                return buyerLog;
            }
        }
        return null;
    }

}
