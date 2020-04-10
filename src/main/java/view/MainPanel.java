package view;

import java.util.Scanner;

public class MainPanel {
    public static Scanner scanner = new Scanner(System.in);
    public static void mainMenu(){
        while(true){
            System.out.println("1: user panel\n2: goods panel\n3: offs panel");
            int selectedMenu = scanner.nextInt();
            if (selectedMenu == 1){
                UserPanel.checkIsLogged();
            }else if (selectedMenu == 2){

            }else if (selectedMenu == 3){

            } else {
                System.out.println("you must select following options");
            }
        }
    }
}
