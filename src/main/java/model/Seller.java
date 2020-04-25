package model;

import java.util.List;

public class Seller extends Account {
    private String company;
    private List<Good> goods;
    private List<Off> offs;


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password , String company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.company = company;
    }
}
