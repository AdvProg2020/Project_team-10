package model.requests;

import model.Account;

public class RegisterOfSellerRequest extends Request {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public RegisterOfSellerRequest(int id, String username, String password, String firstName,
                                   String lastName, String email, String phoneNumber) {
        super(null, id);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void accept() {

    }
}
