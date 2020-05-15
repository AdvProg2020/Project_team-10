package model.requests;

import controller.AccountManager;
import model.Account;

public abstract class Request {
    protected Account account;
    protected int id;
    protected String requestName;
    protected String acceptMessage;
    protected String declineMessage;

    public Request(Account account, int id) {
        this.account = account;
        this.id = id;
        AccountManager.increaseLastRequestId();
    }

    public abstract void accept();

    public int getId() {
        return id;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public String getDeclineMessage() {
        return declineMessage;
    }

    @Override
    public String toString() {
        return "username : " + account.getUsername() + "\n" +
                "id : " + id + "\n" +
                "name : " + requestName;
    }
}
