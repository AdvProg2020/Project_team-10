package model.requests;

import model.Account;
import model.Seller;
import model.Shop;

public class RegisterOfSellerRequest extends Request {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String company;

    public RegisterOfSellerRequest(int id, String username, String password, String firstName,
                                   String lastName, String email, String phoneNumber, String company) {
        super(null, id);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.requestName = "registration of a seller request";
        this.acceptMessage = "user " + username + " was registered as a seller";
        this.declineMessage = "request of registration of user " + username + " as a seller was declined";
    }

    @Override
    public void accept() {
        Shop.getShop().getAllAccounts().add(new Seller(username, firstName, lastName, email, phoneNumber, password, company));
    }

    @Override
    public String toString() {
        return "username : " + username + "\n" +
                "id : " + id + "\n" +
                "name : " + requestName;
    }

}
