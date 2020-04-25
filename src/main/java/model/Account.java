package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<String> discountCodes;
    private long credit;
    private List<Log> logs;

    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
