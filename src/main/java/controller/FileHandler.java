package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileHandler {

    public static void write() {
        writeProducts();
        writeOffs();
        writeDiscounts();
        writeReceipts();
        writeBankAccounts();
        writeSeller();
        writeBuyer();
        writeAdmin();
        writeSupporter();
        writeCategories();
        writeComments();
        writeLastProductId();
        writeLastCommentId();
        writeLastBuyerLogId();
        writeLastSellerLogId();
        writeLastOffId();
        writeLastDiscountCodeId();
        writeLastRequestId();
    }

    public static void updateDatabase() {
        try {
            readAllCategories();
            readAllProducts();
            readAllAccounts();
            readAllBankAccounts();
            readAllReceipts();
            readAllDiscounts();
            readAllOffs();
            readAllComments();
            readLastProductId();
            readLastBuyerLogId();
            readLastSellerLogId();
            readLastCommentId();
            readLastDiscountCodeId();
            readLastOffId();
            readLastRequestId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //write
    private static void writeSeller() {
        try {
            FileWriter fileWriter = new FileWriter("database/allSellers.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllSellers()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeBuyer() {
        try {
            FileWriter fileWriter = new FileWriter("database/allBuyers.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllBuyers()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeAdmin() {
        try {
            FileWriter fileWriter = new FileWriter("database/allAdmins.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllAdmins()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeSupporter() {
        try {
            FileWriter fileWriter = new FileWriter("database/allSupporters.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllSupporters()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeProducts() {
        try {
            FileWriter fileWriter = new FileWriter("database/allProducts.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllGoods()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeBankAccounts() {
        try {
            FileWriter fileWriter = new FileWriter("database/allBankAccounts.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllBankAccounts()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeReceipts() {
        try {
            FileWriter fileWriter = new FileWriter("database/allReceipts.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllReceipts()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeOffs() {
        try {
            FileWriter fileWriter = new FileWriter("database/allOffs.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllOffs()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeDiscounts() {
        try {
            FileWriter fileWriter = new FileWriter("database/allDiscounts.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllDiscounts()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeCategories() {
        try {
            FileWriter fileWriter = new FileWriter("database/allCategories.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllCategories()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeComments() {
        try {
            FileWriter fileWriter = new FileWriter("database/allComments.txt");
            fileWriter.write(new Gson().toJson(Shop.getShop().getAllComments()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastProductId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastProductId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastGoodId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastOffId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastOffId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastOffId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastDiscountCodeId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastDiscountCodeId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastDiscountCode()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastRequestId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastRequestId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastRequestId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastBuyerLogId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastBuyerLogId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastBuyerLogId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastSellerLogId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastSellerLogId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastSellerLogId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLastCommentId() {
        try {
            FileWriter fileWriter = new FileWriter("database/lastCommentId.txt");
            fileWriter.write(new Gson().toJson(AccountManager.getLastCommentId()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read
    private static void readAllAccounts() throws Exception {
        readAllAdmins();
        readAllSupporters();
        readAllBuyers();
        readAllSellers();
    }

    private static void readAllSellers() throws Exception {
        FileReader fileReader = new FileReader("database/allSellers.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allSellersJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Seller> allSellers;
        Type sellerListType = new TypeToken<ArrayList<Seller>>() {
        }.getType();
        allSellers = gson.fromJson(allSellersJson, sellerListType);
        Shop.getShop().getAllSellers().addAll(allSellers);
        Shop.getShop().getAllAccounts().addAll(allSellers);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllBuyers() throws Exception {
        FileReader fileReader = new FileReader("database/allBuyers.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allBuyerJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Buyer> allBuyers;
        Type buyerListType = new TypeToken<ArrayList<Buyer>>() {
        }.getType();
        allBuyers = gson.fromJson(allBuyerJson, buyerListType);
        Shop.getShop().getAllBuyers().addAll(allBuyers);
        Shop.getShop().getAllAccounts().addAll(allBuyers);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllAdmins() throws Exception {
        FileReader fileReader = new FileReader("database/allAdmins.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allAdminsJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Admin> allAdmins;
        Type adminListType = new TypeToken<ArrayList<Admin>>() {
        }.getType();
        allAdmins = gson.fromJson(allAdminsJson, adminListType);
        Shop.getShop().getAllAdmins().addAll(allAdmins);
        Shop.getShop().getAllAccounts().addAll(allAdmins);
        bufferedReader.close();
        fileReader.close();
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
        Shop.getShop().getAllReceipts().addAll(allReceipts);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllSupporters() throws Exception {
        FileReader fileReader = new FileReader("database/allSupporters.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allSupportersJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Supporter> allSupporters;
        Type supporterListType = new TypeToken<ArrayList<Supporter>>() {
        }.getType();
        allSupporters = gson.fromJson(allSupportersJson, supporterListType);
        Shop.getShop().getAllSupporters().addAll(allSupporters);
        Shop.getShop().getAllAccounts().addAll(allSupporters);
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
        Shop.getShop().getAllBankAccounts().addAll(allBankAccounts);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllProducts() throws IOException {
        FileReader fileReader = new FileReader("database/allProducts.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allProductsJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Good> allProducts;
        Type productsListType = new TypeToken<ArrayList<Good>>() {
        }.getType();
        allProducts = gson.fromJson(allProductsJson, productsListType);
        Shop.getShop().getAllGoods().addAll(allProducts);
        bufferedReader.close();
        fileReader.close();

//        GoodsManager.getFilteredGoods().addAll(Shop.getShop().getAllGoods());

    }

    private static void readAllCategories() throws IOException {
        FileReader fileReader = new FileReader("database/allCategories.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allCategoriesJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Category> allCategories;
        Type categoriesListType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        allCategories = gson.fromJson(allCategoriesJson, categoriesListType);
        Shop.getShop().getAllCategories().addAll(allCategories);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllOffs() throws IOException {
        FileReader fileReader = new FileReader("database/allOffs.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allOffsJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Off> allOffs;
        Type offsListType = new TypeToken<ArrayList<Off>>() {
        }.getType();
        allOffs = gson.fromJson(allOffsJson, offsListType);
        Shop.getShop().getAllOffs().addAll(allOffs);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllDiscounts() throws IOException {
        FileReader fileReader = new FileReader("database/allDiscounts.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allDiscountsJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Discount> allDiscounts;
        Type discountListType = new TypeToken<ArrayList<Discount>>() {
        }.getType();
        allDiscounts = gson.fromJson(allDiscountsJson, discountListType);
        Shop.getShop().getAllDiscounts().addAll(allDiscounts);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readAllComments() throws IOException {
        FileReader fileReader = new FileReader("database/allComments.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String allCommentsJson = bufferedReader.readLine().trim();
        Gson gson = new Gson();
        ArrayList<Comment> allComments;
        Type commentListType = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        allComments = gson.fromJson(allCommentsJson, commentListType);
        Shop.getShop().getAllComments().addAll(allComments);
        bufferedReader.close();
        fileReader.close();
    }

    private static void readLastRequestId() throws IOException {
        FileReader fileReader = new FileReader("database/lastRequestId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastRequestId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastOffId() throws IOException {
        FileReader fileReader = new FileReader("database/lastOffId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastOffId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastDiscountCodeId() throws IOException {
        FileReader fileReader = new FileReader("database/lastDiscountCodeId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastDiscountCode(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastBuyerLogId() throws IOException {
        FileReader fileReader = new FileReader("database/lastBuyerLogId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastBuyerLogId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastSellerLogId() throws IOException {
        FileReader fileReader = new FileReader("database/lastSellerLogId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastSellerLogId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastCommentId() throws IOException {
        FileReader fileReader = new FileReader("database/lastCommentId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastCommentId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

    private static void readLastProductId() throws IOException {
        FileReader fileReader = new FileReader("database/lastProductId.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        AccountManager.setLastGoodId(Integer.parseInt(bufferedReader.readLine()));
        fileReader.close();
    }

}
