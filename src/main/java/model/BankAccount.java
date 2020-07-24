package model;

import controller.AccountManager;

public class BankAccount {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int accountNumber;
    private String token;
    private long money;

    public BankAccount(String firstName, String lastName, String username, String password, int accountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        AccountManager.increaseLastAccountNumber();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
