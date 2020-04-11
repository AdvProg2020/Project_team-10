package model;

import java.util.HashMap;
import java.util.List;

public class Buyer extends Account {

    private HashMap<Discount , Integer> discountAndNumberOfAvailableDiscount;
    private List<Good> goods;
    private List<Good> cart;


    public Buyer(String username, String password) {
        super(username, password);
    }
}
