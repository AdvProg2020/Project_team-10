package model.requests;

import model.Account;

public abstract class Request {
    protected Account account;
    private int id;
    private boolean isAccepted;

    public Request(Account account, int id) {
        this.account = account;
        this.id = id;
    }

    public void accept() {

    }
}
