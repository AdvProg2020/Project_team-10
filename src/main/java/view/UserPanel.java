package view;

import model.Account;
import model.Buyer;
import model.Seller;

import java.util.Scanner;

public class UserPanel {
    private static Scanner scanner = MainPanel.scanner;
    private static boolean isLogged;
    private static String username;

    public static void checkIsLogged() {
        if (isLogged) {
            userMenu();
        } else {
            registerAndLoginPanel();
        }
    }

    public static void userMenu() {
        Account account = Controller.Manager.getRoleByUsername(username);
        if (account instanceof Buyer) {

        } else if (account instanceof Seller) {

        } else {

        }
    }

    public static void registerAndLoginPanel() {
        int selected;
        System.out.println("1: register\n2: login\n3: back");
        while ((selected = scanner.nextInt()) != 3){
           if (selected == 1) {
                register();
           } else {
               login();
           }
        }
    }

    private static void register(){

    }

    private static void login(){

    }

}
