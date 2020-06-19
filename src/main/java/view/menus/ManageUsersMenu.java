package view.menus;

import static view.CommandProcessor.*;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("manage users", parentMenu);
        subMenus.put(1, getViewAllUsers());
        subMenus.put(2, getDeleteUser());
        subMenus.put(3, getCreateManager());
    }

    private Menu getViewAllUsers() {
        return new LastMenu("view all users", this) {
            @Override
            public void show() {
                viewAllUsers();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getDeleteUser() {
        return new LastMenu("delete user", this) {
            @Override
            public void show() {
                processDeleteAccountByAdmin();
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Menu getCreateManager() {
        return new LastMenu("create manager", this) {
            @Override
            public void show() {
//                processRegister(false);
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

}
