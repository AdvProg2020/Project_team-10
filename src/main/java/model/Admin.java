package model;

import java.util.List;

public class Admin extends Account {
    // darkhast
    private List<Discount> discounts;


    public Admin(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
    }
}
