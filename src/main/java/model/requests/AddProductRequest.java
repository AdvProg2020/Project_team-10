package model.requests;

import model.Account;
import model.Request;

public class AddProductRequest extends Request {
    public AddProductRequest(Account account, int id) {
        super(account, id);
    }
}
