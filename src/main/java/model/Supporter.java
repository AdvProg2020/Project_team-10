package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Supporter extends Account {
    private HashMap<String, ArrayList<String>> buyersToMessages;
    private List<String> buyersOnPage;

    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber
            , String password, String imagePath) {
        super(username, firstName, lastName, email, phoneNumber, password, imagePath);
        this.buyersToMessages = new HashMap<>();
        this.buyersOnPage = new ArrayList<>();
    }


    public HashMap<String, ArrayList<String>> getBuyersToMessages() {
        return buyersToMessages;
    }

    public List<String> getBuyersOnPage() {
        return buyersOnPage;
    }
}
