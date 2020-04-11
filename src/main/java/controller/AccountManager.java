package controller;

import model.Account;
import model.Buyer;
import model.Good;
import model.Seller;

import java.util.Date;
import java.util.List;

public class AccountManager {
    private Account onlineAccount;

    public static Account getRoleByUsername(String username) {
        return null;
    }

    public static boolean canRegister(String username, String type){
            return false;
    }

    public static void register(String username, String password, String type, String firstName, String lastName, String email, String phoneNumber){

    }

    public static boolean login(String username, String password){
        return false;
    }
    //Admin
    public static void showPersonalInfo(){

    }

    public static void editPersonalInfo(String password, String firstName, String lastName, String email, String phoneNumber){

    }

    public static void showAllAccount(){

    }

    public static void showAccount(){

    }

    public static void deleteAccount(){

    }

    public static void addAdmin(String username){

    }

    public static void removeProduct(String id){

    }

    public static void createDiscount(String code, Date startDate, Date endDate, int percent, long maxAmountOfDiscount, int repeatDiscount, List<Buyer> users){

    }

    public static void showAllDiscount(){

    }

    public static void showDiscount(String code){

    }

    public static void editDiscount(String code){

    }

    public static void removeDiscount(String code){

    }

    public static void showAllRequests(){

    }

    public static void showRequestDetail(String id){

    }

    public static void acceptRequest(String id){

    }

    public static void declineRequest(String id){

    }

    public static void showAllcategories(){

    }

    public static boolean editCategory(String name, String newName, List<String> newAttribute){

        return false;
    }

    public static void addCategory(String name, List<String> attributes){

    }

    public static void removeCategory(String categoryName){

    }
    //Seller
    public static void showCompanyInfo(){

    }

    public static void showSalesHistory(){

    }

    public static void showHisProducts(){

    }

    public static void showProductForSeller(String id){

    }

    public static void showBuyersOfThisProduct(String id){

    }

    public static void editProduct(String id, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description){

    }

    public static void addProdcut(String id, String status, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description){

    }

    public static void showAllOffs(){

    }

    public static void showOff(String id){

    }

    public static void editOff(String id, List<Good> goods, Date startDate, Date endDate, int discount){

    }

    public static void addOff(String id, List<Good> goods, Date startDate, Date endDate, int discount){

    }

    public static void viewBalance(){

    }
    //Buyer
    public static void showProductsInCart(){

    }

    public static void showProductInCart(String id){

    }

    public static void increase(String id){

    }

    public static void decrease(String id){

    }

    public static void showTotalPrice(){

    }

    public static void purchase(){

    }

    public static void showAllOrders(){

    }

    public static void showOrder(String id){

    }

    public static void rateProduct(String id, int rate){

    }

    public static void showAllDiscountsCode(){

    }

}
