package view;

import java.util.Scanner;

public class MainPanel {
    public static Scanner scanner = new Scanner(System.in);
    public static void mainMenu(){
        int selectedMenu;
        System.out.println("1: user panel\n2: goods panel\n3: offs panel");
        selectedMenu = scanner.nextInt();
        while(true){
            if (selectedMenu == 1){

            }else if (selectedMenu == 2){

            }else if (selectedMenu == 3){

            }
        }
    }
}
