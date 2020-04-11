package model;

import java.util.List;

public class Seller extends Account {
    private String company;
    private List<Good> goods;
    private List<Off> offs;

    public Seller(String username, String password) {
        super(username, password);
    }
}
