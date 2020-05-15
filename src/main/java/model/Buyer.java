package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Buyer extends Account {

    private HashMap<Discount, Integer> discountAndNumberOfAvailableDiscount;
    private List<Good> goods;
    private List<Good> cart;
    private List<Discount> discounts;


    public Buyer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        goods = new ArrayList<>();
        cart = new ArrayList<>();
        discounts = new ArrayList<>();
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
}
