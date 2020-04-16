package view.Menus;

public abstract class LastMenu extends Menu {
    public LastMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    @Override
    public void show() {
        System.out.println("1: back");
    }

    @Override
    public void execute() {
        Menu nextMenu;
        int selectedMenu = scanner.nextInt();
        if (selectedMenu != 1) {
            System.out.println("you must choose one of following options");
            nextMenu = this;
        } else {
            nextMenu = this.parentMenu;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
