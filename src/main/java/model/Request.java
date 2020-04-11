package model;

public class Request {
    private Account account;
    private int id;
    private boolean isAccepted;

    public Request(Account account, int id) {
        this.account = account;
        this.id = id;
    }
}
