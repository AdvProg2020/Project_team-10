package controller;

import model.*;
import view.Menus.Menu;

import java.util.Date;
import java.util.List;

public class AccountManager {
    private Account onlineAccount;

    public static Account getRoleByUsername(String username) {
        return null;
    }

    public static boolean canRegister(String username) {
        return true;
    }

    public static void register(String username, String password, String type, String firstName, String lastName, String email, String phoneNumber) {
    }

    public static boolean login(String username, String password) {
        return false;
    }

    public static void logout() {
        Menu.setIsLogged(false);
    }

    //Admin
    public static void showPersonalInfo() {

    }

    public static void editPersonalInfo(String password, String firstName, String lastName, String email, String phoneNumber) {

    }

    public static void showAllAccount() {

    }

    public static boolean showAccount(String username) {
        return false;
    }

    public static boolean deleteAccount(String username) {
        return false;
    }

    public static boolean addAdmin(String username) {
        return false;
    }

    public static boolean removeProduct(String id) {
        return false;
    }

    public static boolean createDiscount(String code, Date startDate, Date endDate, int percent, long maxAmountOfDiscount, int repeatDiscount, List<Buyer> users) {
        return false;
    }

    public static void showAllDiscount() {

    }

    public static boolean showDiscount(String code) {
        return false;
    }

    public static boolean editDiscount(String code) {
        return false;
    }

    public static boolean removeDiscount(String code) {
        return false;
    }

    public static void showAllRequests() {

    }

    public static boolean showRequestDetail(String id) {
        return false;
    }

    public static boolean acceptRequest(String id) {
        return false;
    }

    public static boolean declineRequest(String id) {
        return false;
    }

    public static void showAllCategories() {

    }

    public static boolean editCategory(String name, String newName, List<String> newAttribute) {
        return false;
    }

    public static boolean addCategory(String name, List<String> attributes) {
        return false;
    }

    public static boolean removeCategory(String categoryName) {
        return false;
    }

    //Seller
    public static void showCompanyInfo() {

    }

    public static void showSalesHistory() {

    }

    public static void showHisProducts() {

    }

    public static boolean showProductForSeller(String id) {
        return false;
    }

    public static boolean showBuyersOfThisProduct(String id) {
        return false;
    }

    public static boolean editProduct(String id, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description) {
        return false;
    }

    public static boolean addProduct(String id, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description) {
        return false;
    }

    public static void showAllOffs() {

    }

    public static boolean showOff(String id) {
        return false;
    }

    public static boolean editOff(String id, List<Good> goods, Date startDate, Date endDate, int discount) {
        return false;
    }

    public static boolean addOff(String id, List<Good> goods, Date startDate, Date endDate, int discount) {
        return false;
    }

    public static void viewBalance() {

    }

    //Buyer
    public static void showProductsInCart() {

    }

    public static boolean showProductInCart(String id) {
        return false;
    }

    public static boolean increase(String id) {
        return false;
    }

    public static boolean decrease(String id) {
        return false;
    }

    public static void showTotalPrice() {

    }

    public static void purchase() {
    }

    public static void showAllOrders() {

    }

    public static boolean showOrder(String id) {
        return false;
    }

    public static boolean rateProduct(String id, int rate) {
        return false;
    }

    public static void showAllDiscountsCode() {

    }

    //Checker
    private static Good getProductWithId(String id) {
        return null;
    }

    private static Discount getDiscountWithCode(String code) {
        return null;
    }

    private static Account getAccountWithUsername(String username) {
        return null;
    }

    private static Category getCategory(String name) {
        return null;
    }

    private static boolean isThereEmail(String email) {
        return false;
    }

    private static boolean isTherePhoneNumber(String phoneNumber) {
        return false;
    }

    private static Off getOffWithId(String id) {
        return null;
    }


}
