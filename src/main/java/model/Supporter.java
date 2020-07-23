package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Supporter extends Account {
    private HashMap<String, ArrayList<String>> buyersToMessages;

    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber
            , String password, String imagePath) {
        super(username, firstName, lastName, email, phoneNumber, password, imagePath);
        this.buyersToMessages = new HashMap<>();
    }


    public HashMap<String, ArrayList<String>> getBuyersToMessages() {
        return buyersToMessages;
    }
}
