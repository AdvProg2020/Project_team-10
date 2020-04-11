package view.Menus;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("manage users", parentMenu);
        subMenus.put(1 ,getView());
        subMenus.put(2 , getDeleteUser());
        subMenus.put(3 , getCreateManager());
    }

    private Menu getView(){
        return new Menu("view" , this) {
            @Override
            public void show() {
                //TODO
            }

            @Override
            public void execute() {
                //TODO
            }
        };
    }

    private Menu getDeleteUser(){
        return new Menu("delete user" , this) {
            @Override
            public void show() {
                //TODO
            }

            @Override
            public void execute() {
                //TODO
            }
        };
    }

    private Menu getCreateManager(){
        return new Menu("create manager" , this) {
            @Override
            public void show() {
                //TODO
            }

            @Override
            public void execute() {
                //TODO
            }
        };
    }

}
