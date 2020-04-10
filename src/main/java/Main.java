import view.Menus.MainMenu;
import view.Menus.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu mainMenu = new MainMenu();
        Menu.setScanner(scanner);
        mainMenu.show();
        mainMenu.execute();
    }
}
