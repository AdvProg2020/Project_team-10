package Bank;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AccountManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileHandler {

    public static void write() {
        writeBankAccounts();
        writeReceipts();
        writeLastBankAccountId();
        writeLastReceiptId();
    }

    public static void updateDatabase() {
        try {
            readAllBankAccounts();
            readAllReceipts();
            readLastBankAccountId();
            readLastReceiptId();
        } catch (Exception ignored) {

        }
    }

    private static void writeBankAccounts() {
        try {
            FileWriter fileWriter = new FileWriter("database/allBankAccounts.txt");
            fileWriter.write(new Gson().toJson(Bank.getBank().getAllBankAccounts()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeReceipts() {
        try {
            FileWriter fileWriter = new FileWriter("database/allReceipts.txt");
            fileWriter.write(new Gson().toJson(Bank.getBank().getAllReceipts()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readAllReceipts() throws Exception {
        FileReader fileReader = new FileReader("database/allReceipts.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allSupportersJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Receipt> allReceipts;
        Type supporterListType = new TypeToken<ArrayList<Receipt>>() {
        }.getType();
        allReceipts = gson.fromJson(allSupportersJson, supporterListType);
        Bank.getBank().getAllReceipts().addAll(allReceipts);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllBankAccounts() throws Exception {
        FileReader fileReader = new FileReader("database/allBankAccounts.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allSupportersJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<BankAccount> allBankAccounts;
        Type supporterListType = new TypeToken<ArrayList<BankAccount>>() {
        }.getType();
        allBankAccounts = gson.fromJson(allSupportersJson, supporterListType);
        Bank.getBank().getAllBankAccounts().addAll(allBankAccounts);
        bufferedReader.close();
        fileReader.close();
    }

    private static void writeLastBankAccountId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastBankAccountId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastAccountNumber()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastReceiptId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastReceiptId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastReceiptId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readLastBankAccountId() throws IOException {
        FileReader fileReader = new FileReader("database/lastBankAccountId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastAccountNumber(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastReceiptId() throws IOException {
        FileReader fileReader = new FileReader("database/lastReceiptId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastReceiptId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }





}
