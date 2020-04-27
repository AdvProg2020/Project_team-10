package model;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {
    private String company;
    private List<Good> goods;
    private List<Off> offs;


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, String company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.company = company;
        goods = new ArrayList<>();
        offs = new ArrayList<>();
    }

    public Good getProductWithId(int id) {
        for (Good good : this.getGoods()) {
            if (good.getId() == id) {
                return good;
            }
        }
        return null;
    }

    public Off getOffWithId(int id) {
        for (Off off : this.getOffs()) {
            if (off.getId() == id) {
                return off;
            }
        }
        return null;
    }

    public String getCompany() {
        return company;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public List<Off> getOffs() {
        return offs;
    }


}
