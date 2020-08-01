package Bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private static Bank bank = new Bank();
    private List<BankAccount> allBankAccounts = new ArrayList<>();
    private List<Receipt> allReceipts = new ArrayList<>();

    private Bank() {

    }

    public static Bank getBank() {
        return bank;
    }

    public List<BankAccount> getAllBankAccounts() {
        return allBankAccounts;
    }

    public List<Receipt> getAllReceipts() {
        return allReceipts;
    }
}
