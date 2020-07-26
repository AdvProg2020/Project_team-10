package Bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private static Bank bank = new Bank();
    private List<BankAccount> allBankAccounts = new ArrayList<>();
    private List<Receipt> allReceipts = new ArrayList<>();

    private Bank() {
        allBankAccounts.add(new BankAccount("admins", "admins", "shop", "javad1379", 0));
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
