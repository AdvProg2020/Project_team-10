package model.requests;

import model.Account;

public class EditProfileRequest extends Request {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    public EditProfileRequest(Account account, int id, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(account, id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.requestName = "edit profile request";
        this.acceptMessage = "profile of user " + account.getUsername() + " edited";
        this.declineMessage = "request of edit of user " + account.getUsername() + " was declined";
    }

    @Override
    public void accept() {
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setPassword(password);
    }
}
