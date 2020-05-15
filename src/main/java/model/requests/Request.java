package model.requests;

import controller.AccountManager;
import model.Account;

public abstract class Request {
    protected Account account;
    protected int id;
    protected String message;
    protected boolean isAccepted;

    public Request(Account account, int id) {
        this.account = account;
        this.id = id;
        AccountManager.increaseLastRequestId();
    }

    public abstract void accept();
}
