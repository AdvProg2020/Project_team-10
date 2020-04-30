package model.requests;

import model.Account;


public class AddProductRequest extends Request {
    public AddProductRequest(Account account, int id) {
        super(account, id);
    }
}
