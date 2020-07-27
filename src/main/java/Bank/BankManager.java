package Bank;

import java.util.*;

public class BankManager {
    public static HashMap<String, Integer> onlineToken = new HashMap();
    public static HashMap<String, Date> tokenToDate = new HashMap<>();

    public static String canRegister(String username, String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            return "password do not match";
        }
        for (BankAccount bankAccount : Bank.getBank().getAllBankAccounts()) {
            if (bankAccount.getUsername().equals(username)) {
                return "username is not available";
            }
        }
        return "true";
    }

    public static BankAccount canGetToken(String username, String password) {
        for (BankAccount bankAccount : Bank.getBank().getAllBankAccounts()) {
            if (bankAccount.getUsername().equals(username)) {
                if (bankAccount.getPassword().equals(password)) {
                    return bankAccount;
                }
            }
        }
        return null;
    }

    public static boolean isExistBankAccount(int accountId) {
        for (BankAccount bankAccount : Bank.getBank().getAllBankAccounts()) {
            if (bankAccount.getAccountNumber() == accountId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistReceipt(int receiptId) {
        for (Receipt receipt : Bank.getBank().getAllReceipts()) {
            if (receipt.getId() == receiptId) {
                return true;
            }
        }
        return false;
    }

    public static boolean canDeposit(String token, int accountId) {
        for (String token1 : onlineToken.keySet()) {
            if (token.equals(token1)) {
                if (onlineToken.get(token1) == accountId) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getAccountIdByUsername(String username) {
        for (BankAccount bankAccount : Bank.getBank().getAllBankAccounts()) {
            if (bankAccount.getUsername().equals(username)) {
                return bankAccount.getAccountNumber();
            }
        }
        return -1;
    }

    public static Receipt getReceiptById(int id) {
        for (Receipt receipt : Bank.getBank().getAllReceipts()) {
            if (receipt.getId() == id) {
                return receipt;
            }
        }
        return null;
    }

    public static BankAccount getBankAccountById(int id){
        for (BankAccount bankAccount : Bank.getBank().getAllBankAccounts()) {
            if (bankAccount.getAccountNumber() == id) {
                return bankAccount;
            }
        }
        return null;
    }

    public static int getAccountIdByToken(String token) {
        for (String s : onlineToken.keySet()) {
            if (s.equals(token)) {
                return onlineToken.get(s);
            }
        }
        return -1;
    }

    public static String getDestinationById(int id) {
        String output = "";
        for (Receipt receipt : Bank.getBank().getAllReceipts()) {
            if (receipt.getDestId() == id) {
                output += receipt.toString();
            }
        }
        return output;
    }

    public static String getSourceById(int id) {
        String output = "";
        for (Receipt receipt : Bank.getBank().getAllReceipts()) {
            if (receipt.getSourceId() == id) {
                    output += receipt.toString();
            }
        }
        return output;
    }

    public static String getAllTransactionsById(int id) {
        String output = "";
        for (Receipt receipt : Bank.getBank().getAllReceipts()) {
            if (receipt.getDestId() == id || receipt.getSourceId() == id) {
                    output += receipt.toString();
            }
        }
        return output;
    }

    public static boolean checkReceiptAndBankAccount(int receiptId, int bankAccountId) {
        return getReceiptById(receiptId).getSourceId() == bankAccountId || getReceiptById(receiptId).getDestId() == bankAccountId;
    }

    public static boolean checkExpiration(String currentToken) {
        Date date = new Date();
        for (String token : tokenToDate.keySet()) {
            if (token.equals(currentToken)) {
                if (date.getHours() - tokenToDate.get(token).getHours() > 1) {
                    return true;
                }
            }
        }
        return false;
    }

}
