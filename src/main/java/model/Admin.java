package model;

import java.util.List;

public class Admin extends Account {
    // darkhast
    private List<Discount> discounts;

    public Admin(String username, String password) {
        super(username, password);
    }
}
